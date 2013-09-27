package kr.co.datastreams.test;

import kr.co.datastreams.dsma.dic.FilterTokenPattern;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;

/**
 *
 * User: shkim
 * Date: 13. 9. 25
 * Time: 오후 1:45
 *
 */
public class FilterTokenPatternTest {

    String[] lines = {
          "emoticon.negative.10=[ㅜㅠㅡ-]{1,}[_.]?[ㅜㅡㅠ-]{1,}[;:]*"
        , "number=[-]?[0-9]+([,][0-9]{3})*([.][0-9]+)?"
        , "combined=[a-zA-Z0-9]+[-][a-zA-Z0-9]+}"
    };


    @Test
    public void test_asTokenPattern() throws Exception {
        String[] expecteds = {
            "([ㅜㅠㅡ-]{1,}[_.]?[ㅜㅡㅠ-]{1,}[;:]*, EMOTICON)"
            ,"([-]?[0-9]+([,][0-9]{3})*([.][0-9]+)?, NUMBER)"
            ,"([a-zA-Z0-9]+[-][a-zA-Z0-9]+}, COMBINED)"
        };

        Method method = FilterTokenPattern.class.getDeclaredMethod("asTokenPattern", String.class);
        method.setAccessible(true);
        int i = 0;
        for (String each : lines) {
            FilterTokenPattern pattern = (FilterTokenPattern)method.invoke(FilterTokenPattern.class, each);
            assertEquals(expecteds[i++], pattern.toString());
        }
    }

    // File test_pattern.dic should be located in target\test-classes
    @Test
    public void test_getPredefinedPatterns() throws Exception {
        String fileName = "test_pattern.dic";

        FilterTokenPattern[] patterns = FilterTokenPattern.getPredefinedPatterns(fileName);
        assertEquals(3, patterns.length);
    }
}
