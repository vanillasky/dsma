package com.datastreams.nlp.ma.constants;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * User: shkim
 * Date: 13. 10. 16
 * Time: 오전 10:33
 *
 */
public enum WordPattern {
    N(11, "체언")        /* 체언 : N/PN/NM/XN/CN/UN/AS/HJ/ET */
    , NJ(12, "체언+조사")
    , NSM(13, "체언+용언화접미사+어미")
    , NSMJ(14, "체언+용언화접미사+'음/기'+ 조사")
    , NSMXM(15, "체언+용언화접미사+'아/어'+보조용언+어미 ")
    , NJCM(16, "체언+'에서/부터/에서부터'+'이'+어미")

    , VM(21, "용언+어미")
    , VMJ(22, "용언+'음/기'+ 조사")
    , VMCM(23, "용언+'음/기'+'이'+어미 ")
    , VMXM(24, "용언+'아/어'+보조용언+어미")
    , VMXMJ(25, "용언+'아/어'+보조용언+'음/기'+조사")
    , AID(26, "단일어:부사,관형사,감탄사")
    , ADVJ(27, "부사+조사")  /*  : '빨리도' */

    , NVM(31, "체언+동사+어미")
    , ZZZ(90, "문장부호")   /* 문장부호, KS 완성형 기호열, 단독조사/어미 */
    ;

    private int value;
    private String name;
    private WordPattern(int i, String patternName) {
        value = i;
        name = patternName;
    }

    public static WordPattern[] josaAttachedPatterns() {
        WordPattern[] patterns = new WordPattern[6];
        patterns[0] = NJ;
        patterns[1] = NSMJ;
        patterns[2] = VMJ;
        patterns[4] = VMXMJ;
        patterns[5] = ADVJ;

        return patterns;
    }
}
