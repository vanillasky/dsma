package kr.co.datastreams.dsma.ma;

import kr.co.datastreams.dsma.dic.TokenPattern;

import java.util.*;
import java.util.regex.Matcher;
import static java.lang.Character.UnicodeBlock.*;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 7. 22
 * Time: 오후 2:18
 * To change this template use File | Settings | File Templates.
 */
public class Tokenizer {

    private static final char PREDEFINED_TOKEN_REPLACEMENT = ' ';

    private final Map<Integer, Token> filteredTokens = new HashMap<Integer, Token>(); // 전처리 과정에서 PREDEFINED_TOKEN_PATTERN 에 의해 걸러진 토큰들
    private final List<Token> tokens = new ArrayList<Token>();

    public Tokenizer() {
    }

    public List<Token> tokenize(String text) {
        StringBuilder buf = new StringBuilder(text);
        filterPredefinedPatterns(buf);

        char ch;
//        char prevCh = '\0';
        String temp = "";
        CharType currCharType = CharType.ETC;
        CharType prevCharType;
        int tokenIndex = 0;

        for (int i=0, len=text.length(); i < len; i++) {
            ch = buf.charAt(i);
            prevCharType = currCharType;
            currCharType = determineCharType(ch, i);

            if(i != 0) {
                if (prevCharType != currCharType) {
                    //System.out.println("["+i+"]prevCharType != currCharType =>"+ temp + "," + ch +"," + prevCharType + "," + currCharType);
                    if(prevCharType != CharType.EMOTICON) {
//                        System.out.println("  create token:"+ temp + "," + prevCharType);
                        tokens.add(new Token(temp, prevCharType, tokenIndex));
                    }
                    tokenIndex = i;
                    temp = "";
                }
            }


            temp = (new StringBuilder(String.valueOf(temp))).append(ch).toString();
            //prevCh = ch;

        }

        if (temp.trim().length() > 0) {
            Token t = new Token(temp, currCharType, tokenIndex);
            tokens.add(t);
        }

        Collections.sort(tokens);
        return tokens;
    }

    private CharType determineCharType(char ch, int location) {
        CharType charType;
        Character.UnicodeBlock unicodeBlock = Character.UnicodeBlock.of(ch); // 한글이면 HANGUL_SYLLABLES

        if (filteredTokens.containsKey(location)) {
            charType = CharType.EMOTICON;
        }
        else if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z')) {
            charType = CharType.ALPHABET;
        }
        else if (ch >= '0' && ch <= '9') {
            charType = CharType.NUMBER;
        }
        else if (ch == ' ' || ch == '\t' || ch == '\r' || ch == '\n') {
            charType = CharType.SPACE;
        }
        else if (unicodeBlock == HANGUL_SYLLABLES || unicodeBlock == HANGUL_COMPATIBILITY_JAMO) {
            charType = CharType.HANGUL;
        }
        else if (unicodeBlock == CJK_COMPATIBILITY_IDEOGRAPHS || unicodeBlock == CJK_UNIFIED_IDEOGRAPHS) {  // 한중일 호환 한자 || 한중일 공통한자
            charType = CharType.HANJA;
        }
        else if (unicodeBlock == LETTERLIKE_SYMBOLS ||
                 unicodeBlock == CJK_COMPATIBILITY ||
                 unicodeBlock == CJK_SYMBOLS_AND_PUNCTUATION ||
                 unicodeBlock == HALFWIDTH_AND_FULLWIDTH_FORMS ||
                 unicodeBlock == BASIC_LATIN) {
            charType = CharType.SYMBOL;

        }
        else {
            charType = CharType.ETC;
        }
        return charType;
    }


    // 미리 정의된 패턴과 일치하는 부분을 걸러낸다 (ㅜㅜ, 숫자 등)
    private void filterPredefinedPatterns(StringBuilder buf) {
        TokenPattern[] predefinedPatterns = TokenPattern.getPredefinedPatterns();

        for (TokenPattern each : predefinedPatterns) {
            tokens.addAll(match(buf, each));
        }
    }

    // 패턴에 매칭되는 토큰 리스트를 만든다.
    private List<Token> match(StringBuilder text, TokenPattern tokenPattern) {
        List<Token> tokenList = new ArrayList<Token>();

        for (Matcher matcher = tokenPattern.getPattern().matcher(text); matcher.find(); ) {
            Token token = new Token(text.substring(matcher.start(), matcher.end()),
                                    tokenPattern.getCharType(),
                                    matcher.start() );
            tokenList.add(token);
            markFiltered(text, matcher.start(), matcher.end(), token);
        }

        return tokenList;
    }

    private void markFiltered(StringBuilder text, int start, int end, Token filtered) {
        for (int i=start; i < end; i++) {
            filteredTokens.put(i, filtered);
            text.setCharAt(i, PREDEFINED_TOKEN_REPLACEMENT);
        }
    }

}
