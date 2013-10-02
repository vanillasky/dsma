package kr.co.datastreams.test.rule;

import kr.co.datastreams.dsma.model.Variant;
import kr.co.datastreams.dsma.analyzer.rule.EndingCombineRule;
import kr.co.datastreams.dsma.analyzer.rule.EndingStartsWithNLMB;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 8. 5
 * Time: 오전 10:16
 * To change this template use File | Settings | File Templates.
 */
public class StartsWithNLMBEndingTest {

    //  끈다면서 -> 끄/ㄴ다면서(e), 끌/ㄴ다면서(e)
    @Test
    public void test_끈다면서() throws Exception {
        EndingCombineRule rule = new EndingStartsWithNLMB("끈", "다면서");
        Variant result = rule.split();
        assertEquals("끄", result.getStem());
        assertEquals("ㄴ다면서", result.getEnding());
    }



}
