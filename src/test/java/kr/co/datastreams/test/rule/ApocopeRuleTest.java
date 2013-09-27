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
    public void test_어_탈락어미_가라() throws Exception {
        EndingCombineRule rule = new ApocopeRule("가", "라");
        Variant result = rule.split();
        System.out.println(result);
        assertEquals("가", result.getStem());
        assertEquals("어라", result.getEnding());
    }

    @Test
    public void test_꺼도() throws Exception {
        EndingCombineRule rule = new ApocopeRule("꺼", "도");
        Variant result = rule.split();
        System.out.println(result);
        assertEquals("꺼", result.getStem());
        assertEquals("어도", result.getEnding());
    }


}
