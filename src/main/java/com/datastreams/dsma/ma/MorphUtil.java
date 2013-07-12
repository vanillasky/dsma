package com.datastreams.dsma.ma;

import javafx.util.converter.CharacterStringConverter;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 6. 24
 * Time: 오후 1:20
 * To change this template use File | Settings | File Templates.
 */
public class MorphUtil {

    public static final char HANGUL_START_CODE = 0xAC00;
    public static final char HANGUL_END_CODE   = 0xD7A3;

    public static final char[] CHOSUNG = {
       'ㄱ','ㄲ','ㄴ','ㄷ','ㄸ','ㄹ','ㅁ','ㅂ','ㅃ','ㅅ',
       'ㅆ','ㅇ','ㅈ','ㅉ','ㅊ','ㅋ','ㅌ','ㅍ','ㅎ'
    };

    public static final char[] JUNGSUNG = {
        'ㅏ','ㅐ','ㅑ','ㅒ','ㅓ','ㅔ','ㅕ','ㅖ','ㅗ','ㅘ',
        'ㅙ','ㅚ','ㅛ','ㅜ','ㅝ','ㅞ','ㅟ','ㅠ','ㅡ','ㅢ',
        'ㅣ'
    };

    private static final char[] JONGSUNG = {
        '\0','ㄱ','ㄲ','ㄳ','ㄴ','ㄵ','ㄶ','ㄷ','ㄹ','ㄺ',
        'ㄻ','ㄼ','ㄽ','ㄾ','ㄿ','ㅀ','ㅁ','ㅂ','ㅄ','ㅅ',
        'ㅆ','ㅇ','ㅈ','ㅊ','ㅋ','ㅌ','ㅍ','ㅎ'
    };

    private static final int JUNG_JONG = 588; // 21(중성) * 28(종성)

    public static char[] disassemble(char ch) {
        char[] result = null;
        if (ch < HANGUL_START_CODE || ch > HANGUL_END_CODE) {
            return null;
        }

        ch -= 0xAC00;

        char chosung = CHOSUNG[ ch / JUNG_JONG];
        ch = (char)( ch % JUNG_JONG);

        char jungsung = JUNGSUNG[ ch / JONGSUNG.length];
        char jongsung = JONGSUNG[ ch % JONGSUNG.length];

        if (jongsung != 0) {
            result = new char[] {chosung, jungsung, jongsung};
        } else {
            result = new char[] {chosung, jungsung};
        }

        System.out.println("disassemble result:"+ String.valueOf(result));
        return result;

    }


    public static char makeChar(char ch, int last) {
        ch -= 0xAC00;
        int first = ch/JUNG_JONG;
        ch = (char)(ch % JUNG_JONG);
        int middle = ch/JONGSUNG.length;

        return compound(first,middle,last);
    }

    public static char compound(int first, int middle, int last) {
        return (char)(0xAC00 + first* JUNG_JONG + middle * JONGSUNG.length + last);
    }
}
