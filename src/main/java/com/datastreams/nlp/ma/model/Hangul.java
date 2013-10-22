package com.datastreams.nlp.ma.model;

import com.datastreams.nlp.common.annotation.Immutable;
import com.datastreams.nlp.ma.util.HangulUtil;
import kr.co.datastreams.dsma.analyzer.rule.Constraints;

/**
 *
 * User: shkim
 * Date: 13. 10. 18
 * Time: 오후 1:28
 *
 */
@Immutable
public class Hangul {

    public static final Hangul EMPTY = new Hangul('\0', '\0', '\0');

    public final char cho;
    public final char jung;
    public final char jong;

    public Hangul(char cho, char jung, char jong) {
        this.cho = cho;
        this.jung = jung;
        this.jong = jong;
    }

    public Hangul(char cho, char jung) {
        this(cho, jung, (char)0);
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
        return HangulUtil.findIndexAndcombine(cho, jung, jong);
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
     * 중성이 phonemesArray의 자소 중 한개라도 포함하고 있으면 true 를 반환한다.
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
     * 조사가 붙을 수 있는지 확인한다.
     *
     * @param josa
     * @return true if this josa can be appended
     */
    public boolean isJosaAppendable(String josa) {
        return hasJong() ? Constraints.isAppendableJosaToJongSung(josa) : Constraints.isAppendableJosaWithoutJongsung(josa);
    }
}
