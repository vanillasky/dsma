package kr.co.datastreams.dsma.ma;

import javax.management.ObjectName;
import java.util.Hashtable;

/**
 *
 * User: shkim
 * Date: 13. 8. 8
 * Time: 오후 12:21
 *
 */
public class PosTag {
    private static final String[] TAGS = new String[] {"NN", "NX", "NU", "NP",
                                                       "VV", "VJ", "VX", "CP",
                                                       "AD", "DT", "EX", "JO",
                                                       "EM", "EP", "PF", "SN",
                                                       "SV", "SJ", "SA", "SF",
                                                       "XR", "SY", "NR", "OL",
                                                       "OH", "ON"};
    private static final Hashtable<String, Long> TAG_HASH = new Hashtable<String, Long>();
    private static final Hashtable<Long, String> TAG_NUM_HASH = new Hashtable<Long, String>();
    static final String[] ZIP_TAG_ARR;

    public static final long NN;  // 일반명사
    public static final long NX;  // 의존명사
    public static final long NU;  // 수사
    public static final long NP;  // 대명사

    public static final long VV;  // 동사
    public static final long VJ;  // 형용사
    public static final long VX;  // 보조용언
    public static final long CP;  // 서술격조사 '이다'

    public static final long AD;  // 부사
    public static final long DT;  // 관형사
    public static final long EX;  // 감탄사
    public static final long JO;  // 조사
    public static final long EM;  // 어미
    public static final long EP;  // 선어말 어미
    public static final long PF;  // 접두사
    public static final long SN;  // 명사화접미사     //s
    public static final long SV;  // 동사화접미사     //t
    public static final long SJ;  // 형용사화접미사   //t
    public static final long SA;  // 부사화화접미사
    public static final long SF;  // 기타접미사
    public static final long XR;  // 어근
    public static final long SY;  // 문장부호
    public static final long NR;  // 미등록어

    public static final long OL;  // 외국어
    public static final long OH;  // 한자
    public static final long ON;  // 숫자

    public static final long N;
    public static final long V;

    static {
        long tagNum = 0L;
        for (int i=0; i < TAGS.length; i++) {
            tagNum = 1L << i;  // 1,2,4,8, ...
            TAG_HASH.put(TAGS[i], new Long(tagNum));
            TAG_NUM_HASH.put(new Long(tagNum), TAGS[i]);
        }

        NN = getTagNum("NN");
        NX = getTagNum("NX");
        NU = getTagNum("NU");
        NP = getTagNum("NP");
        VV = getTagNum("VV");
        VJ = getTagNum("VJ");
        VX = getTagNum("VX");
        CP = getTagNum("CP");
        AD = getTagNum("AD");
        DT = getTagNum("DT");
        EX = getTagNum("EX");
        JO = getTagNum("JO");
        EM = getTagNum("EM");
        EP = getTagNum("EP");
        PF = getTagNum("PF");
        SN = getTagNum("SN");
        SV = getTagNum("SV");
        SJ = getTagNum("SJ");
        SA = getTagNum("SA");
        SF = getTagNum("SF");
        XR = getTagNum("XR");
        SY = getTagNum("SY");
        NR = getTagNum("NR");
        OL = getTagNum("OL");
        OH = getTagNum("OH");
        ON = getTagNum("ON");

        N = NN | NP;
        V = VV | VJ;

        TAG_HASH.put("N", Long.valueOf(N));
        TAG_NUM_HASH.put(Long.valueOf(N), "N");

        TAG_HASH.put("V", Long.valueOf(V));
        TAG_NUM_HASH.put(Long.valueOf(V), "V");

        ZIP_TAG_ARR = new String[]{"N", "V"};
    }

    public static long getTagNum(String tag) {
        if (tag == null) return 0L;
        System.out.println("tag:"+ tag);
        return TAG_HASH.get(tag).longValue();
    }

//    public static char NOUN  =   'N';       // 명사(noun)
//    public static char PNOUN  =  'P';       // 대명사(pronoun)
//    public static char XNOUN  =  'U';       // 의존명사(dependent noun)
//    public static char NUMERAL = 'M';       // 수사(numeral)
//    public static char PROPER  = 'O';       // proper noun: NOT USED
//
//    public static char VJXV  =   'V';       // 용언: 동사/형용사/보조용언
//    public static char AID   =   'Z';       // 기타: 부사/관형사/감탄사
//
//    public static char PUNC  =   'q';       // 문장부호
//    public static char SYMB  =   'Q';       //* special symbols       */
//
//    public static char CNOUN  =  'C';       //* compound noun guessed */
//    public static char NOUNK  =  'u';       //* guessed as noun       */
//
//    public static char ASCall =  '@';       //* all alphanumeric chars*/
//    public static char ASCend =  '$';       //* end with alphanumeric */
//    public static char ASCmid =  '*';       //* ..+alphanumeric+Hangul*/
//
//    //* defined for numeral to digit conversion */
//    public static char digits =  '1';       //* digit-string */
//    public static char digitH  = '2';       //* digit-string + Hangul*/
//
//    public static char VERB  =   'V';       //* verb                  */
//    public static char ADJ   =   'J';       //* adjective             */
//    public static char XVERB =   'W';       //* auxiliary verb        */
//    public static char XADJ  =   'K';       //* NOT USED YET          */
//
//    public static char ADV   =   'B';       //* adverb                */
//    public static char DET   =   'D';       //* determiner            */
//    public static char EXCL  =   'L';       //* exclamation           */
//
//    public static char JOSA   =  'j';       //* Korean Josa           */
//    public static char COPULA =  'c';       //* copula '-Wi-'         */
//    public static char EOMI   =  'e';       //* final Ending          */
//    public static char PEOMI  =  'f';       //* prefinal Ending       */
//    public static char NEOMI  =  'n';       //* nominalizing Eomi     */
//
//    public static char PREFIX =  'p';       //* prefixes              */
//    public static char SFX_N  =  's';       //* noun suffixes: '들/적'*/
//    public static char SFX_V  =  't';       //* 접미사*/
//
//    public static char ETC   =   'Z';       //* not decided yet       */
//
//    /* ASCII stem may be classified as follows: NOT USED YET    */
//    public static char ALPHA  =  'A';       //* English alphabet      */
//    public static char NUMBER =  '#';       //* Arabic numbers        */
//    public static char SMARK  =  'R';       //* sentence markers      */
//
//    public static char NVERBK  = 'Y';       //* guessed as noun+verb  */
//
//    public static char SQUOTE  = 's';       //* single quotation      */
//    public static char DQUOTE  = 'd';       //* double quotation      */
//    public static char LPAREN  = 'l';       //* left parenthesis      */
//    public static char RPAREN  = 'r';       //* right parenthesis     */


    public static long buildTags(String[] tags) {
        long result = 0L;
        Long tagNum = 0L;
        for (String each : tags) {
            tagNum = getTagNum(each.trim());
            if (tagNum != null) {
                result = result | tagNum;
            }
        }
        return result;
    }

    public static boolean isKindOf(long tagNum, long compareTag) {
        return Long.bitCount(tagNum & compareTag) > 0;
    }

    public static String getTag(long tagNum) {
        //System.out.println("==>" + tagNum);
        return TAG_NUM_HASH.get(tagNum);
    }
}
