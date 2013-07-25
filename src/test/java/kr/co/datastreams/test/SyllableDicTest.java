package kr.co.datastreams.test;

import kr.co.datastreams.dsma.dic.SyllableDic;
import kr.co.datastreams.dsma.ma.EndingProcessor;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 7. 23
 * Time: 오후 4:18
 * To change this template use File | Settings | File Templates.
 */
public class SyllableDicTest {

    @Test
    public void testGetFeature() throws Exception {
        String str = "올해장마는유난히길고비가많이내린다";
        char[] chars = str.toCharArray();
        int i =0;
        for (char each : chars) {
            char[] result = SyllableDic.getFeature(each);
            assertEquals(chars[i], result[result.length-1]);
            i++;
        }
    }

    @Test
    public void testSplitEomi() throws Exception {
        String str = "간";
        EndingProcessor.splitEnding(str, "");
    }
}
