package kr.co.datastreams.test;

import kr.co.datastreams.dsma.model.PosTag;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 10. 1
 * Time: 오전 11:22
 * To change this template use File | Settings | File Templates.
 */
public class PosTagTest {

    @Test
    public void testDecodeNoun() throws Exception {
        long nounAndAdverb = PosTag.NNP | PosTag.AD;
        assertEquals("NNP", PosTag.decodeNoun(nounAndAdverb));
    }
}
