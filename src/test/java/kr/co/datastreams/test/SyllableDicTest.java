package kr.co.datastreams.test;

import kr.co.datastreams.dsma.dic.SyllableDic;
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
        String str = "가";
        char[] feature = "1111111111110111100000000000000000000001".toCharArray();

        char[] result = SyllableDic.getFeature(str.charAt(0));
        assertArrayEquals(feature, result);
    }

}
