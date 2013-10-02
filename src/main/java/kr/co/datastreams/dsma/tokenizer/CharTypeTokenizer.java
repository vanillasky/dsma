package kr.co.datastreams.dsma.tokenizer;

import kr.co.datastreams.dsma.annotation.ThreadSafe;
import kr.co.datastreams.dsma.dic.FilterTokenPattern;
import kr.co.datastreams.dsma.model.Token;
import kr.co.datastreams.dsma.model.*;

import java.util.*;
import java.util.regex.Matcher;
import static java.lang.Character.UnicodeBlock.*;

/**
 * CharTypeTokenizer break up text into individual Tokens.
 * Filter predefined tokens and replace with character 'PREDEFINED_TOKEN_REPLACEMENT'.
 * To identify character type, scan each characters in the specified text and compare current character with previous character.
 * If the character types are different, creates a Token and add it to List.
 *
 * User: shkim
 * Date: 13. 7. 22
 * Time: 오후 2:18
 *
 */
@ThreadSafe
public class CharTypeTokenizer implements Tokenizer {

    private static final char PREDEFINED_TOKEN_REPLACEMENT = ' ';

    public CharTypeTokenizer() {
    }


    @Override
    public List<Token> tokenize(String text) {
        if (text == null || text.trim().length() == 0) return null;

        List<Token> tokens = new ArrayList<Token>();
        Map<Integer, Token> filteredTokenMap = new HashMap<Integer, Token>(); // 전처리 과정에서 PREDEFINED_TOKEN_PATTERN 에 의해 걸러진 토큰들
        StringBuilder buf = new StringBuilder(text);

        List<Token> filteredTokens = filterPredefinedPatterns(buf, filteredTokenMap);
        tokens.addAll(filteredTokens);

        char ch;
        String temp = "";
        CharType currCharType = CharType.ETC;
        CharType prevCharType;
        int tokenIndex = 0;

        for (int i=0, len=text.length(); i < len; i++) {
            ch = buf.charAt(i);

            prevCharType = currCharType;
            if (filteredTokenMap.containsKey(i)) {
                currCharType = CharType.FILTERED;
            } else {
                currCharType = determineCharType(ch);
            }

            if(i != 0) {
                if (prevCharType != currCharType) {
//                    System.out.println("["+i+"]prevCharType != currCharType =>"+ temp + "," + ch +"," + prevCharType + "," + currCharType);
                    if(prevCharType != CharType.FILTERED) {
//                        System.out.println("  created token:"+ temp + "," + prevCharType);
                        tokens.add(new Token(temp, prevCharType, tokenIndex));
                    }
                    tokenIndex = i;
                    temp = "";
                }
            }


            temp = (new StringBuilder(String.valueOf(temp))).append(ch).toString();
        }

        if (temp.trim().length() > 0) {
            Token t = new Token(temp, currCharType, tokenIndex);
            tokens.add(t);
        }

        Collections.sort(tokens);
        return tokens;
    }

    private CharType determineCharType(char ch) {
        CharType charType;
        Character.UnicodeBlock unicodeBlock = Character.UnicodeBlock.of(ch); // 한글이면 HANGUL_SYLLABLES

        if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z')) {
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
    private List<Token> filterPredefinedPatterns(StringBuilder buf, Map<Integer, Token> filteredTokenMap) {
        List<Token> result = new ArrayList<Token>();

        FilterTokenPattern[] predefinedPatterns = FilterTokenPattern.getPredefinedPatterns();

        List<Token> filteredTokens;
        for (FilterTokenPattern each : predefinedPatterns) {
            filteredTokens = match(buf, each, filteredTokenMap);
            if (filteredTokens.size() > 0) {
                result.addAll(filteredTokens);
            }
        }
        return result;
    }

    // 패턴에 매칭되는 토큰 리스트를 만든다.
    private List<Token> match(StringBuilder text, FilterTokenPattern tokenPattern, Map<Integer, Token> filteredTokenMap) {
        List<Token> tokenList = new ArrayList<Token>();

        for (Matcher matcher = tokenPattern.getPattern().matcher(text); matcher.find(); ) {
            Token token = new Token(text.substring(matcher.start(), matcher.end()),
                                    tokenPattern.getCharType(),
                                    matcher.start() );
            tokenList.add(token);
            markFiltered(text, matcher.start(), matcher.end(), token, filteredTokenMap);
        }

        return tokenList;
    }

    private void markFiltered(StringBuilder text, int start, int end, Token filtered, Map<Integer, Token> filteredTokenMap) {
        for (int i=start; i < end; i++) {
            filteredTokenMap.put(i, filtered);
            text.setCharAt(i, PREDEFINED_TOKEN_REPLACEMENT);
        }
    }


}
