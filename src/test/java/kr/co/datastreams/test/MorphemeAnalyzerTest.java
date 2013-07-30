package kr.co.datastreams.test;

import kr.co.datastreams.dsma.ma.DefaultMorphemeAnalyzer;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 7. 23
 * Time: 오전 10:59
 * To change this template use File | Settings | File Templates.
 */
public class MorphemeAnalyzerTest {

    @Test
    public void test() throws Exception {
        String input = "부동산거래 활성화 효과 하나 없는데 찌라시들 엉터리 보도에 내놓은 코미디 정책을 보고 웃었다.";
        DefaultMorphemeAnalyzer analyzer = new DefaultMorphemeAnalyzer();
        analyzer.analyze(input);
    }
}
