package kr.co.datastreams.test;

import kr.co.datastreams.dsma.dic.AnalyzedDic;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 8. 9
 * Time: 오전 9:25
 * To change this template use File | Settings | File Templates.
 */
public class AnalyzedDicTest {

    @Test
    public void test() throws Exception {
        String word = "무어라";
        AnalyzedDic.find(word);


        String[] TAG_ARR = new String[]{"NNG", "NNP"}; //, "NNB", "NNM", "NR", "NP", "VV", "VA", "VXV", "VXA", "VCP", "VCN", "MDN", "MDT", "MAG", "MAC", "IC", "JKS", "JKC", "JKG", "JKO", "JKM", "JKI", "JKQ", "JX", "JC", "EPH", "EPT", "EPP", "EFN", "EFQ", "EFO", "EFA", "EFI", "EFR", "ECE", "ECD", "ECS", "ETN", "ETD", "XPN", "XPV", "XSN", "XSV", "XSA", "XSM", "XSO", "XR", "SY", "SF", "SP", "SS", "SE", "SO", "SW", "UN", "UV", "UE", "OL", "OH", "ON", "BOS", "EMO"};
        long hgFuncNum = 0L;
        int i = 0;

        for(int stop = TAG_ARR.length; i < stop; ++i) {
            hgFuncNum = 1L << i;
            System.out.println(TAG_ARR[i] + "," + hgFuncNum);

            //TAG_HASH.put(TAG_ARR[i], new Long(hgFuncNum));
            //TAG_NUM_HASH.put(new Long(hgFuncNum), TAG_ARR[i]);
        }

        System.out.println(1L & Long.MAX_VALUE);
//        System.out.println(Long.bitCount(8L & 256L));

    }
}
