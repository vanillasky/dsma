package com.datastreams.nlp.ma.util;

import com.datastreams.nlp.ma.model.Hangul;

/**
 *
 * 한글처리 유틸
 *
 * User: shkim
 * Date: 13. 10. 18
 * Time: 오후 1:23
 *
 */
public class HangulUtil {

    public static final char HANGUL_START = 0xAC00; // 유니코드 한글 '가'
    public static final char HANGUL_END= 0xD7A3; // 유니코드 한글 '힣'

    public static final int JONG_JUNG = 21 * 28; // 중성 개수 * 종성 개수 = 588


    // 초성 19자
    public static final char[] CHOSEONG = {
        'ㄱ','ㄲ','ㄴ','ㄷ','ㄸ','ㄹ','ㅁ','ㅂ','ㅃ','ㅅ',
        'ㅆ','ㅇ','ㅈ','ㅉ','ㅊ','ㅋ','ㅌ','ㅍ','ㅎ'
    };

    // 중성 21자
    public static final char[] JUNGSEONG = {
        'ㅏ','ㅐ','ㅑ','ㅒ','ㅓ','ㅔ','ㅕ','ㅖ','ㅗ','ㅘ',
        'ㅙ','ㅚ','ㅛ','ㅜ','ㅝ','ㅞ','ㅟ','ㅠ','ㅡ','ㅢ',
        'ㅣ'
    };

    // 종성 28
    public static final char[] JONGSEONG = {
        '\0','ㄱ','ㄲ','ㄳ','ㄴ','ㄵ','ㄶ','ㄷ','ㄹ','ㄺ',
        'ㄻ','ㄼ','ㄽ','ㄾ','ㄿ','ㅀ','ㅁ','ㅂ','ㅄ','ㅅ',
        'ㅆ','ㅇ','ㅈ','ㅊ','ㅋ','ㅌ','ㅍ','ㅎ'
    };

    public static final char[] SYLLABLES_TO_CHECK_BEFORE_E = {
        '누', '멍', '성', '기', '보', '니', '리'
    };

    private static final int getChoIndex(char ch)
    {
        int ret = -1;
        switch (ch) {
            case 'ㄱ':	ret = 0;	break;
            case 'ㄲ':	ret = 1;	break;
            case 'ㄴ':	ret = 2;	break;
            case 'ㄷ':	ret = 3;	break;
            case 'ㄸ':	ret = 4;	break;
            case 'ㄹ':	ret = 5;	break;
            case 'ㅁ':	ret = 6;	break;
            case 'ㅂ':	ret = 7;	break;
            case 'ㅃ':	ret = 8;	break;
            case 'ㅅ':	ret = 9;	break;
            case 'ㅆ':	ret = 10;	break;
            case 'ㅇ':	ret = 11;	break;
            case 'ㅈ':	ret = 12;	break;
            case 'ㅉ':	ret = 13;	break;
            case 'ㅊ':	ret = 14;	break;
            case 'ㅋ':	ret = 15;	break;
            case 'ㅌ':	ret = 16;	break;
            case 'ㅍ':	ret = 17;	break;
            case 'ㅎ':	ret = 18;	break;
        }
        return ret;
    }


    private static final int getJungIndex(char ch)
    {
        int ret = -1;
        switch(ch)
        {
            case 'ㅏ':	ret = 0;	break;
            case 'ㅐ':	ret = 1;	break;
            case 'ㅑ':	ret = 2;	break;
            case 'ㅒ':	ret = 3;	break;
            case 'ㅓ':	ret = 4;	break;
            case 'ㅔ':	ret = 5;	break;
            case 'ㅕ':	ret = 6;	break;
            case 'ㅖ':	ret = 7;	break;
            case 'ㅗ':	ret = 8;	break;
            case 'ㅘ':	ret = 9;	break;
            case 'ㅙ':	ret = 10;	break;
            case 'ㅚ':	ret = 11;	break;
            case 'ㅛ':	ret = 12;	break;
            case 'ㅜ':	ret = 13;	break;
            case 'ㅝ':	ret = 14;	break;
            case 'ㅞ':	ret = 15;	break;
            case 'ㅟ':	ret = 16;	break;
            case 'ㅠ':	ret = 17;	break;
            case 'ㅡ':	ret = 18;	break;
            case 'ㅢ':	ret = 19;	break;
            case 'ㅣ':	ret = 20;	break;
        }

        return ret;
    }

    private static final int getJongIndex(char ch)
    {
        int ret = -1;
        switch(ch)
        {
            case 0:		ret = 0;	break;
            case ' ':	ret = 0;	break;
            case 'ㄱ':	ret = 1;	break;
            case 'ㄲ':	ret = 2;	break;
            case 'ㄳ':	ret = 3;	break;
            case 'ㄴ':	ret = 4;	break;
            case 'ㄵ':	ret = 5;	break;
            case 'ㄶ':	ret = 6;	break;
            case 'ㄷ':	ret = 7;	break;
            case 'ㄹ':	ret = 8;	break;
            case 'ㄺ':	ret = 9;	break;
            case 'ㄻ':	ret = 10;	break;
            case 'ㄼ':	ret = 11;	break;
            case 'ㄽ':	ret = 12;	break;
            case 'ㄾ':	ret = 13;	break;
            case 'ㄿ':	ret = 14;	break;
            case 'ㅀ':	ret = 15;	break;
            case 'ㅁ':	ret = 16;	break;
            case 'ㅂ':	ret = 17;	break;
            case 'ㅄ':	ret = 18;	break;
            case 'ㅅ':	ret = 19;	break;
            case 'ㅆ':	ret = 20;	break;
            case 'ㅇ':	ret = 21;	break;
            case 'ㅈ':	ret = 22;	break;
            case 'ㅊ':	ret = 23;	break;
            case 'ㅋ':	ret = 24;	break;
            case 'ㅌ':	ret = 25;	break;
            case 'ㅍ':	ret = 26;	break;
            case 'ㅎ':	ret = 27;	break;
        }
        return ret;
    }

    /**
     * 한글 여부를 판단한다.
     *
     * @param ch
     * @return
     */
    public static boolean isHangul(char ch) {
        Character.UnicodeBlock unicodeBlock = Character.UnicodeBlock.of(ch); // 한글이면 HANGUL_SYLLABLES
        return unicodeBlock == Character.UnicodeBlock.HANGUL_SYLLABLES;
    }

    /**
     * 초성/중성/종성을 합쳐서 한 개의 문자로 만들어 반환한다.
     *
     * @return
     */
    public static char combine(int first, int medial, int last) {
        return (char)(HANGUL_START + first * JONG_JUNG + medial * 28 + last);
    }

    /**
     * 초, 중, 종성에 대한 index를 찾아서 한글자로 합친다.
     * @param cho
     * @param jung
     * @param jong
     * @return
     */
    public static char findIndexAndcombine(char cho, char jung, char jong) {
        return combine(getChoIndex(cho), getJungIndex(jung), getJongIndex(jong));
    }

    /**
     * 종성을 제거한 문자를 만들어서 반환한다.
     * @param ch - 문자
     * @return 종성이 제거된 문자(e.g. 를 -> 르, 는 -> 느)
     *
     */
    public static char removeFinal(char ch) {
        ch -=  HANGUL_START;

        int first = ch / 588;
        ch = (char)(ch % 588);
        int medial = ch / 28;

        return combine(first, medial, 0);
    }

    /**
     * 문자의 중성을 replace 로 변경해서 새로운 문자를 반환한다.
     *
     * @param ch - 원본 문자
     * @param replacePhoneme - 바뀔 중성
     * @return
     */
    public static char replaceMedial(char ch, char replacePhoneme) {
        ch -= HANGUL_START;
        int first = ch / 588;
        int medial = getJungIndex(replacePhoneme);
        return combine(first, medial, 0);
    }

    /**
     * 종성을 replace 로 바꾸어 반환한다.
     * @param ch - 원본 문자
     * @param replace - 바꿀 종성
     * @return
     */
    public static char replaceFinal(char ch, char replace) {
        char last = (char)(ch - HANGUL_START);
        int lastIndex = last % 28;

        replace -= HANGUL_START;
        int first = replace / 588;
        replace = (char)(replace % 588);
        int medial = replace / 28;

        return combine(first, medial, lastIndex);
    }

    /**
     * 한글 한글자를 초성/중성/종성의 배열로 만들어 반환한다.<br/>
     *
     * 유니코드2.0에서 11,172자의 한글은 19(초성) * 21(중성) * 28(종성) = 11,172로 계산된다.<br/>
     * 각 글자의 유니코드는 0xAC00(가)에 순서대로 0 ~ 11171을 더한 값이다.<br/>
     * 따라서 임의의 유니코드값을 unicode 라 할 때<br/>
     * 종성은 (unicode - 0xAC00) % 28 --> 나머지가 0이면 종성없음, 1이면 ㄱ, ... 27이면 ㅎ.<br/>
     * 초성은 (unicode - 0xAC00) / 588 -> 몫이 0이면 'ㄱ', 1이면 'ㄲ' .. 18이면 'ㅎ'<br/>
     * 중성은 ((unicode - 0xAC00) % 588) / 28 --> 0이면 'ㅏ' 1이면 'ㅐ' ... 20이면 'ㅣ'
     *
     * @param ch - character to split
     * @return An array of 초성/중성/종성
     */
    public static Hangul split(char ch) {
        char[] result = null;

        // ch < 가 && ch > 힣
        if(ch < 0xAC00 || ch > 0xD7A3) {
            return Hangul.EMPTY;
        }

        char hcode = (char)(ch - 0xAC00);

        char cho = CHOSEONG[hcode / 588];
        hcode = (char)(hcode % 588);
        char jung = JUNGSEONG[hcode / 28];
        char jong = JONGSEONG[hcode % 28];

        if(jong != 0) {
            return new Hangul(cho, jung, jong);
        }else {
            return new Hangul(cho, jung);
        }
    }



    /**
     * 선어말 어미 후보를 포함하고 있는지 확인한다.
     * @param ch - 문자
     * @return true if ch == '시' or ch == 'ㅆ' or medial/final consonant is 'ㅆ'
     */
    public static boolean containsPrefinalEnding(char ch) {
        Hangul phonemes = split(ch);
        return ch == '시' || ch == 'ㅆ' || phonemes.endsWith('ㅆ');
    }

    /**
     * 조사가 붙을 수 있는지 확인한다.
     *
     * @param josa
     * @return true if this josa can be appended
     */
    public static boolean isJosaAppendable(char ch, String josa) {
        Hangul hg = split(ch);
        return hg.isJosaAppendable(josa);
    }


    /**
     * 명사형 전성어미 음/기로 끝나는 단어인지 확인한다.
     *
     * @param word
     * @return
     */
    public static boolean endsWithNounChangingEomi(String word) {
        int len = word.length();
        Hangul lastSyllable = split(word.charAt(len-1));
        return word.endsWith("기") || (lastSyllable.hasJong() && lastSyllable.endsWith('ㅁ'));
    }

    /**
     * 누에/멍에 등과 같이 '에'로 끝날 수 있는 음절인지 확인한다.
     *
     * @param ch
     * @return
     */
    public static boolean nounCanEndsWith_E(char ch) {
        for (char each : SYLLABLES_TO_CHECK_BEFORE_E) {
            if (each == ch) {
                return true;
            }
        }
        return false;
    }

}
