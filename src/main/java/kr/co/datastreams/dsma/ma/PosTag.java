package kr.co.datastreams.dsma.ma;

import java.util.Hashtable;

/**
 *
 * User: shkim
 * Date: 13. 8. 8
 * Time: 오후 12:21
 *
 */
public class PosTag {
    private static final String[] TAGS = new String[] {"NN", "NX", "NU", "NP", "VV", "VJ", "VX", "VCP", "AD", "J", "EM", "EP", "PF", "SF", "SN"};
    private static final Hashtable<String, Long> TAG_HASH = new Hashtable<String, Long>();
    private static final Hashtable<Long, String> TAG_NUM_HASH = new Hashtable<Long, String>();
    static final String[] ZIP_TAG_ARR;

    public static final long NN;  // 일반명사
    public static final long NX;  // 의존명사
    public static final long NU;  // 수사
    public static final long NP;  // 대명사
    public static final long N;

    public static final long VV;  // 동사
    public static final long VJ;  // 형용사
    public static final long VX;  // 보조용언
    public static final long VCP;  // 서술격조사 '이다'
    public static final long V;

    public static final long AD;  // 부사

    public static final long J;  // 조사

    public static final long EM;  // 어미
    public static final long EP;  // 선어말 어미

    public static final long PF;  // 접두사
    public static final long SF;  // 접미사
    public static final long SN;  // 명사화 접미사

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
        VCP = getTagNum("VCP");
        AD = getTagNum("AD");
        J = getTagNum("J");
        EM = getTagNum("EM");
        EP = getTagNum("EP");
        PF = getTagNum("PF");
        SF = getTagNum("SF");
        SN = getTagNum("SN");
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

    public static PosTag valueOf(String tag) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    public static long buildTags(String[] tags) {
        long tagNum = 0L;
        Long t = 0L;
        for (String each : tags) {
            t = getTagNum(each.trim());
            System.out.println("Tag:"+ each + ", tagNum:"+ t);
            if (t != null) {
                tagNum = tagNum | t;
            }
        }
        return tagNum;
    }

    public static String getTag(long tagNum) {
        System.out.println("==>" + tagNum);
        return TAG_NUM_HASH.get(tagNum);
    }
}
