package kr.co.datastreams.test.rule;

import kr.co.datastreams.dsma.ma.model.Variant;
import kr.co.datastreams.dsma.ma.rule.ApocopeAEoRule;
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
        EndingCombineRule rule = new ApocopeAEoRule("가", "라");
        Variant result = rule.split();
        System.out.println(result);
        assertEquals("가", result.getStem());
        assertEquals("어라", result.getEnding());
    }

    @Test
    public void test_꺼도() throws Exception {
        EndingCombineRule rule = new ApocopeAEoRule("꺼", "도");
        Variant result = rule.split();
        System.out.println(result);
        assertEquals("끄", result.getStem());
        assertEquals("어도", result.getEnding());
    }

//    @Test
//    public void test_어_탈락어미_해서까지() throws Exception {
//        EndingCombineRule rule = new ApocopeAEoRule("해", "서까지");
//        Variant result = rule.split();
//        System.out.println(result);
//        assertEquals("하", result.getStem());
//        assertEquals("어서까지", result.getEnding());
//    }
//
//
//    @Test
//    public void test_어_탈락어미_개라() throws Exception {
//        EndingCombineRule rule = new ApocopeAEoRule("개", "라");
//        Variant result = rule.split();
//        System.out.println(result);
//        assertEquals("개", result.getStem());
//        assertEquals("어라", result.getEnding());
//    }
}
