package kr.co.datastreams.test.rule;

import kr.co.datastreams.dsma.ma.model.Variant;
import kr.co.datastreams.dsma.ma.rule.EndingCombineRule;
import kr.co.datastreams.dsma.ma.rule.EndingCombineWithEoi;
import kr.co.datastreams.dsma.ma.rule.VariantOfEndingStartsWithAEo;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 8. 22
 * Time: 오후 1:53
 * To change this template use File | Settings | File Templates.
 */
public class VariantOfEndingStartsWithAEoTest {

    //EndingCombineRule rule = new EndingCombineWithEoi();

    @Test
    public void test_해서까지() throws Exception {
        EndingCombineRule rule = new EndingCombineWithEoi("해", "서까지");
        Variant result = rule.split();
        System.out.println(result);
//        assertEquals("하", result.getStem());
//        assertEquals("어서까지", result.getEnding());
    }


    @Test
    public void test_개라() throws Exception {
        EndingCombineRule rule = new VariantOfEndingStartsWithAEo("개", "라");
        Variant result = rule.split();
        System.out.println(result);
        assertEquals("개", result.getStem());
        assertEquals("어라", result.getEnding());
    }

    @Test
    public void test_봐도() throws Exception {
        EndingCombineRule rule = new VariantOfEndingStartsWithAEo("봐", "도");
        Variant result = rule.split();
        System.out.println(result);
        assertEquals("보", result.getStem());
        assertEquals("아도", result.getEnding());
    }
}
