package kr.co.datastreams.test.rule;

import kr.co.datastreams.dsma.ma.model.Variant;
import kr.co.datastreams.dsma.ma.rule.ApocopeRule;
import kr.co.datastreams.dsma.ma.rule.EndingCombineRule;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 8. 1
 * Time: 오후 5:58
 * To change this template use File | Settings | File Templates.
 */
public class ApocopeRuleTest {

    @Test
    public void test_어_탈락어미() throws Exception {
        // "해서는" -> 어 생략, 따라서 하/어서/는
        EndingCombineRule rule = new ApocopeRule("해", "서는");
        Variant result = rule.split();
        assertEquals("하", result.getStem());
        assertEquals("어서는", result.getEnding());

    }
}
