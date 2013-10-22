package com.datastreams.nlp.ma.dic;

/**
 * 음절정보(SyllableDic)의 음절특성 Index
 *
 * User: shkim
 * Date: 13. 10. 2
 * Time: 오전 10:18
 *
 */
public interface SyllableFeatureIndex {
    public static final int IDX_JOSA1 = 0; // 조사의 첫음절로 사용되는 음절 48개
    public static final int IDX_JOSA2 = 1; // 조사의 두 번째 이상의 음절로 사용되는 음절 58개
    public static final int IDX_EOMI1 = 2; // 어미의 첫음절로 사용되는 음절 72개
    public static final int IDX_EOMI2 = 3; // 어미의 두 번째 이상의 음절로 사용되는 음절 105개
    public static final int IDX_YONG1 = 4; // 1음절 용언에 사용되는 음절 362개
    public static final int IDX_YONG2 = 5; // 2음절 용언의 마지막 음절로 사용되는 음절 316개
    public static final int IDX_YONG3 = 6; // 3음절 이상 용언의 마지막 음절로 사용되는 음절 195개
    public static final int IDX_CHEON1 = 7; // 1음절 체언에 사용되는 음절 680개
    public static final int IDX_CHEON2 = 8; // 2음절 체언의 마지막 음절로 사용되는 음절 916개
    public static final int IDX_CHEON3 = 9; // 3음절 체언의 마지막 음절로 사용되는 음절 800개
    public static final int IDX_CHEON4 = 10; // 4음절 체언의 마지막 음절로 사용되는 음절 610개
    public static final int IDX_CHEON5 = 11; // 5음절 이상 체언의 마지막 음절로 사용되는 음절 330개
    public static final int IDX_BUSA1 = 12; // 1음절 부사의 마지막 음절로 사용되는 음절 191개
    public static final int IDX_BUSA2 = 13; // 2음절 부사의 마지막 음절로 사용되는 음절 519개
    public static final int IDX_BUSA3 = 14; // 3음절 부사의 마지막 음절로 사용되는 음절 139개
    public static final int IDX_BUSA4 = 15; // 4음절 부사의 마지막 음절로 사용되는 음절 366개
    public static final int IDX_BUSA5 = 16; // 5음절 부사의 마지막 음절로 사용되는 음절 79개
    public static final int IDX_PRONOUN = 17; // 대명사의 마지막 음절로 사용되는 음절 77개
    public static final int IDX_EXCLAM = 18; // 관형사와 감탄사의 마지막 음절로 사용되는 음절 241개

    public static final int IDX_YNPNA = 19; // (용언+'-ㄴ')에 의하여 생성되는 음절 129개
    public static final int IDX_YNPLA = 20; // (용언+'-ㄹ')에 의해 생성되는 음절 129개
    public static final int IDX_YNPMA = 21; // (용언+'-ㅁ')에 의해 생성되는 음절 129개
    public static final int IDX_YNPBA = 22; // (용언+'-ㅂ')에 의해 생성되는 음절 129개
    public static final int IDX_YNPAH = 23; // 모음으로 끝나는 음절 129개중 'ㅏ/ㅓ/ㅐ/ㅔ/ㅕ'로 끝나는 것이 선어말 어미 '-었-'과 결합할 때 생성되는 음절
    public static final int IDX_YNPOU = 24; // 모음 'ㅗ/ㅜ'로 끝나는 음절이 '아/어'로 시작되는 어미나 선어말 어미 '-었-'과 결합할 때 생성되는 음절
    public static final int IDX_YNPEI = 25; // 모음 'ㅣ'로 끝나는 용언이 '아/어'로 시작되는 어미나 선어말 어미 '-었-'과 결합할 때 생성되는 음절
    public static final int IDX_YNPOI = 26; // 모음 'ㅚ'로 끝나는 용언이 '아/어'로 시작되는 어미나 선어말 어미 '-었-'과 결합할 때 생성되는 음절
    public static final int IDX_YNPLN = 27; // 받침 'ㄹ'로 끝나는 용언이 어미 '-ㄴ'과 결합할 때 생성되는 음절
    public static final int IDX_IRRLO = 28; // '러' 불규칙(8개)에 의하여 생성되는 음절 : 러, 렀
    public static final int IDX_IRRPLE = 29; // '르' 불규칙(193개)에 의하여 생성되는 음절
    public static final int IDX_IRROO = 30; // '우' 불규칙에 의하여 생성되는 음절 : 퍼, 펐
    public static final int IDX_IRROU = 31; // '어' 불규칙에 의하여 생성되는 음절 : 해, 했
    public static final int IDX_IRRDA = 32; // 'ㄷ' 불규칙(37개)에 의하여 생성되는 음절
    public static final int IDX_IRRBA = 33; // 'ㅂ' 불규칙(446개)에 의하여 생성되는 음절
    public static final int IDX_IRRSA = 34; // 'ㅅ' 불규칙(39개)에 의하여 생성되는 음절
    public static final int IDX_IRRHA = 35; // 'ㅎ' 불규칙(96개)에 의하여 생성되는 음절
    public static final int IDX_PEND = 36; // 선어말 어미 : 시 셨 았 었 였 겠

    public static final int IDX_YNPEOMI = 37; // 용언이 어미와 결합할 때 생성되는 음절의 수 734개

    /**	용언의 표층 형태로만 사용되는 음절 */
    public static final int IDX_WDSURF = 38;
    public static final int IDX_EOGAN = 39; // 어미 또는 어미의 변형으로 존재할 수 있는 음 (즉 IDX_EOMI 이거나 IDX_YNPNA 이후에 1이 있는 음절)
}
