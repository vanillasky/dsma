package kr.co.datastreams.dsma.util;

import kr.co.datastreams.dsma.Constraints;
import kr.co.datastreams.dsma.dic.SyllableDic;

import javax.print.attribute.HashDocAttributeSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * User: shkim
 * Date: 13. 7. 23
 * Time: 오후 2:05
 *
 */
public class Hangul {

    private static final char HANGUL_START = 0xAC00;

    private static final char[] CHOSEONG = {
            'ㄱ','ㄲ','ㄴ','ㄷ','ㄸ','ㄹ','ㅁ','ㅂ','ㅃ','ㅅ',
            'ㅆ','ㅇ','ㅈ','ㅉ','ㅊ','ㅋ','ㅌ','ㅍ','ㅎ'
    };

    private static final char[] JUNGSEONG = {
            'ㅏ','ㅐ','ㅑ','ㅒ','ㅓ','ㅔ','ㅕ','ㅖ','ㅗ','ㅘ',
            'ㅙ','ㅚ','ㅛ','ㅜ','ㅝ','ㅞ','ㅟ','ㅠ','ㅡ','ㅢ',
            'ㅣ'
    };

    private static final char[] JONGSEONG = {
            '\0','ㄱ','ㄲ','ㄳ','ㄴ','ㄵ','ㄶ','ㄷ','ㄹ','ㄺ',
            'ㄻ','ㄼ','ㄽ','ㄾ','ㄿ','ㅀ','ㅁ','ㅂ','ㅄ','ㅅ',
            'ㅆ','ㅇ','ㅈ','ㅊ','ㅋ','ㅌ','ㅍ','ㅎ'
    };

    //private static final int JUNG_JONG = JUNGSEONG.length * JONGSEONG.length;

    public final char cho;
    public final char jung;
    public final char jong;


    private Hangul(char cho, char jung, char jong) {
        this.cho = cho;
        this.jung = jung;
        this.jong = jong;
    }

    private Hangul(char cho, char jung) {
        this(cho, jung, (char)0);
    }

    public Hangul() {
        this('\0','\0','\0');
    }

    public static Hangul create(char cho, char jung, char jong) {
        return new Hangul(cho, jung, jong);
    }

    public static Hangul create(char cho, char jung) {
        return new Hangul(cho, jung, '\0');
    }

    public static boolean isHangul(char ch) {
        Character.UnicodeBlock unicodeBlock = Character.UnicodeBlock.of(ch); // 한글이면 HANGUL_SYLLABLES
        return unicodeBlock == Character.UnicodeBlock.HANGUL_SYLLABLES;
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
        int medial = PhonemeIndexer.getJungIndex(replacePhoneme);
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
     * 초성/중성/종성을 합쳐서 한 개의 문자로 만들어 반환한다.
     * @return
     */
    public static char combine(int first, int medial, int last) {
        return (char)(HANGUL_START + first * 588 + medial * 28 + last);
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
            return new Hangul();
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


    public String toString() {
        return "(" + this.cho + "," + this.jung + "," + this.jong + ")";
    }

    public boolean hasJong() {
        return jong != 0;
    }


    public boolean hasJung() {
        return jung != 0;
    }

    public boolean hasCho() {
        return cho != 0;
    }

    /**
     * 초성/중성/종성을 합쳐서 한 개의 문자로 만들어 반환한다.
     * @return
     */
    public char combine() {
        int indexCho = PhonemeIndexer.getChoIndex(cho);
        int indexJung = PhonemeIndexer.getJungIndex(jung);
        int indexJong = PhonemeIndexer.getJongIndex(jong);
        return (char)(indexCho * 588 + indexJung * 28 + indexJong + 0xAC00);
    }


    /**
     * 종성이 ㄴ/ㄹ/ㅁ/ㅂ 이면 true를 아니면 false를 반환
     *
     * @return true if the ending is ㄴ/ㄹ/ㅁ/ㅂ
     */
    public boolean endsWithNLMB() {
        return (jong == 'ㄴ' || jong == 'ㄹ' || jong == 'ㅁ' || jong == 'ㅂ');
    }


    public boolean endsWith(char phoneme) {
        return hasJong() ? jong == phoneme :
               hasJung() ? jung == phoneme : false;
    }


    /**
     * 중성이 phonemesArray 의 자소로 이루어져 있으면 true 를 반환한다.
     * @param phonemesArray - 검사할 중성 자소
     *
     * @return 중성이 자소중 하나와 일치하면 true.
     */
    public boolean containsJungsung(char[] phonemesArray) {
        for (char each : phonemesArray) {
            if (jung == each) return true;
        }
        return false;
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
    public boolean isAppendableJosa(String josa) {

        return hasJong() ? Constraints.isAppendableJosaToJongSung(josa) : Constraints.isAppendableJosaWithoutJongsung(josa);
    }
}