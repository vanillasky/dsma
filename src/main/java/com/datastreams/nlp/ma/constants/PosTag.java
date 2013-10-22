package com.datastreams.nlp.ma.constants;

import java.util.*;

/**
 *
 * User: shkim
 * Date: 13. 8. 8
 * Time: 오후 12:21
 *
 */
public class PosTag {

    private static final String[] TAGS = {
         "NNG", "NNP", "NNN", "NNB", "NR", "NP"
        ,"VV", "VA", "VX", "VCP", "VCN"
        ,"MM", "MAG", "MAJ", "IC"
        ,"JKS", "JKC", "JKG", "JKO", "JKB", "JKV", "JKQ", "JX", "JC"
        ,"EP", "EF", "EC", "ETN", "ETM"
        ,"XPN", "XSN", "XSV", "XSA", "XR"
        ,"SF", "SP", "SS", "SE", "SO", "SW"
        ,"NF", "NV", "SL", "SH", "SN"
        ,"DOVI", "DOVT"
    };




    public static enum IrregularType {
        IRRB    //ㅂ 불규칙
        ,IRRS   //ㅅ 불규칙
        ,IRRD   //ㄷ 불규칙
        ,IRRL    //ㄹ 불규칙
        ,IRRH    //ㅎ 불규칙
        ,IRRLU   //르 불규칙
        ,IRRLE   //러 불규칙
    };

    private static final  Map<String, Long> TAG_NAME_HASH = Collections.synchronizedMap(new HashMap<String, Long>());
    private static final Map<Long, String> TAG_NUM_HASH = Collections.synchronizedMap(new HashMap<Long, String>());
    private static final List<String> IRR_LIST = Collections.synchronizedList(new ArrayList<String>());

    // 체언(N)
    public static final long NNG; //일반명사
    public static final long NNP; //고유명사
    public static final long NNN; //이름
    public static final long NNB; //의존명사
    public static final long NR;  //수사
    public static final long NP;  //대명사

    // 용언(V)
    public static final long VV;  //동사
    public static final long VA;  //형용사
    public static final long VX;  //보조용언
    public static final long VCP; //긍정 지정사(서술격 조사 "이다")
    public static final long VCN; //부정 지정사(형용사 "아니다")

    //관형사(M)
    public static final long MM;  //관형사

    //부사(AD)
    public static final long MAG; //일반부사
    public static final long MAJ; //접속부사

    //감탄사(I)
    public static final long IC;  //감탄사

    //조사(J)
    public static final long JKS; //주격조사
    public static final long JKC;//보격조사
    public static final long JKG; //관형격조사
    public static final long JKO; //목적격조사
    public static final long JKB; //부사격조사
    public static final long JKV; //호격조사
    public static final long JKQ; //인용격조사
    public static final long JX;  //보조사
    public static final long JC;  //접속조사

    //선어말어미(EP)
    public static final long EP;  //선어말어미

    //어말어미(E)
    public static final long EF;  //종결어미
    public static final long EC;  //연결어미
    public static final long ETN; //명사형 전성 어미
    public static final long ETM; //관형형 전성 어미

    //접두사(XP)
    public static final long XPN; //체언 접두사

    //접미사(XS)
    public static final long XSN; //명사 파생 접미사
    public static final long XSV; //동사 파생 접미사
    public static final long XSA; //형용사 파생 접미사

    //어근(XR)
    public static final long XR; //어근

    //부호(SY)
    public static final long SF;  //마침표, 물음표, 느낌표
    public static final long SP;  //쉼표, 가운뎃점,콜론,빗금
    public static final long SS;  //따옴표,괄호표,줄표
    public static final long SE;  //줄표
    public static final long SO;  //붙임표(물결,숨김,빠짐)
    public static final long SW;  //기타기호

    //분석불능(U)
    public static final long NF;  //명사추정범주
    public static final long NV;  //용언추정범주

    //한글이외(O)
    public static final long SL;  //외국어
    public static final long SH;  //한자
    public static final long SN;  //숫자

    public static final long DOVI;    //명사뒤에 -하다 결합할 때 자동사로 쓰임
    public static final long DOVT;    //명사뒤에 -하다 결합하여 타동사로 쓰임

    public static final long N;    //NNG,NNP,NNB,NR,NP
    public static final long V;    //VV,VA,VX,VCP,VCN
    public static final long AD;   //MAG,MAJ
    public static final long J;     //JKS,JKC,JKG,JKO,JKB,JKV,JKQ,JX,JC
    public static final long E;     //EF,EC,ETN,ETM
    public static final long XS;    //XSN,XSV,XSA
    public static final long SY;    //SF,SP,SS,SE,SO,SW
    public static final long O;     //SL,SH,SN
    public static final long DO;

    static {
        long tagNum = 0L;
        for (int i=0; i < TAGS.length; i++) {
            tagNum = 1L << i;  // 1,2,4,8,16,...
            TAG_NAME_HASH.put(TAGS[i], new Long(tagNum));
            TAG_NUM_HASH.put(new Long(tagNum), TAGS[i]);
        }

        NNG = getTagNum("NNG");
        NNP = getTagNum("NNP");
        NNN = getTagNum("NNN");
        NNB = getTagNum("NNB");
        NR = getTagNum("NR");
        NP = getTagNum("NP");
        VV = getTagNum("VV");
        VA = getTagNum("VA");
        VX = getTagNum("VX");
        VCP = getTagNum("VCP");
        VCN = getTagNum("VCN");
        MM = getTagNum("MM");
        MAG = getTagNum("MAG");
        MAJ = getTagNum("MAJ");
        IC = getTagNum("IC");
        JKS = getTagNum("JKS");
        JKC = getTagNum("JKC");
        JKG = getTagNum("JKG");
        JKO = getTagNum("JKO");
        JKB = getTagNum("JKB");
        JKV = getTagNum("JKV");
        JKQ = getTagNum("JKQ");
        JX = getTagNum("JX");
        JC = getTagNum("JC");
        EP = getTagNum("EP");
        EF = getTagNum("EF");
        EC = getTagNum("EC");
        ETN = getTagNum("ETN");
        ETM = getTagNum("ETM");
        XPN = getTagNum("XPN");
        XSN = getTagNum("XSN");
        XSV = getTagNum("XSV");
        XSA = getTagNum("XSA");
        XR = getTagNum("XR");
        SF = getTagNum("SF");
        SP = getTagNum("SP");
        SS = getTagNum("SS");
        SE = getTagNum("SE");
        SO = getTagNum("SO");
        SW = getTagNum("SW");
        NF = getTagNum("NF");
        NV = getTagNum("NV");
        SL = getTagNum("SL");
        SH = getTagNum("SH");
        SN = getTagNum("SN");
        DOVI = getTagNum("DOVI");
        DOVT = getTagNum("DOVT");


        N = NNG | NNP | NNN | NNB | NR |NP;
        V = VV | VA | VX | VCP | VCN;
        AD = MAG | MAJ;
        J = JKS | JKC | JKG | JKO | JKB | JKV | JKQ | JX | JC;
        E = EF | EC | ETN | ETM;
        XS = XSN | XSV | XSA;
        SY = SF | SP | SS | SE | SO | SW;
        O  =  SL | SH | SN;
        DO = DOVI | DOVT;


        TAG_NAME_HASH.put("N", Long.valueOf(N));
        TAG_NUM_HASH.put(Long.valueOf(N), "N");

        TAG_NAME_HASH.put("V", Long.valueOf(V));
        TAG_NUM_HASH.put(Long.valueOf(V), "V");

        TAG_NAME_HASH.put("AD", Long.valueOf(AD));
        TAG_NUM_HASH.put(Long.valueOf(AD), "AD");

        TAG_NAME_HASH.put("J", Long.valueOf(J));
        TAG_NUM_HASH.put(Long.valueOf(J), "J");

        TAG_NAME_HASH.put("E", Long.valueOf(E));
        TAG_NUM_HASH.put(Long.valueOf(E), "E");

        TAG_NAME_HASH.put("XS", Long.valueOf(XS));
        TAG_NUM_HASH.put(Long.valueOf(XS), "XS");

        TAG_NAME_HASH.put("SY", Long.valueOf(SY));
        TAG_NUM_HASH.put(Long.valueOf(SY), "SY");

        TAG_NAME_HASH.put("O", Long.valueOf(O));
        TAG_NUM_HASH.put(Long.valueOf(O), "O");

        TAG_NAME_HASH.put("DO", Long.valueOf(DO));
        TAG_NUM_HASH.put(Long.valueOf(DO), "DO");

    }

    private PosTag() {

    }

    public static long getTagNum(String tag) {
        if (tag == null) return 0L;
        Long value = TAG_NAME_HASH.get(tag);
        if (value == null)
            return 0L;

        return value.longValue();
    }

    /**
     * 태그 이름을 반환한다.
     * 2개 이상의 태그로 이루어진 경우에는 첫 번째 태그명을 반환한다.
     *
     * @param tagNum
     * @return
     */
    public static String getTag(long tagNum) {
        String tagName = TAG_NUM_HASH.get(tagNum);
        if (tagName == null && Long.bitCount(tagNum) > 1) {
            tagName = TAG_NUM_HASH.get(tagNum & Long.highestOneBit(tagNum));
        }
        return tagName;
    }

    public static boolean isTagOf(long tagNum, long compare) {
        return (compare & Long.MAX_VALUE & tagNum) > 0L;
    }

    public static String decodeVerb(long tag) {
        return getTag(tag & V);
    }

    public static String decodeNoun(long tag) {
        return getTag(tag & N);
    }

    public static long extractNounTag(long tag) {
        return tag & N;
    }

    public static String decodeAdverb(long tag) {
        return getTag(tag & AD);
    }

    public static long extractAdverbTag(long tag) {
        return tag & AD;
    }

    public static String decodeJosa(long tag) {
        return getTag(tag & J);
    }

    public static long extracteJosaTag(long tag) {
        return tag & J;
    }


}
