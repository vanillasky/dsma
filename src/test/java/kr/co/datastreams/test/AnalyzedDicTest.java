package kr.co.datastreams.test;

import kr.co.datastreams.dsma.dic.AnalyzedDic;
import kr.co.datastreams.dsma.ma.model.AnalysisResult;
import kr.co.datastreams.dsma.ma.model.Word;
import org.junit.Test;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 8. 9
 * Time: 오전 9:25
 * To change this template use File | Settings | File Templates.
 */
public class AnalyzedDicTest {

    @Test
    public void test_find() throws Exception {
        String text = "같았다";  // <같, VV>  + <었, EP> + <다, EM>
        Word word = AnalyzedDic.find(text);
//        List<AnalysisResult> results = word.getAnalysisResults();
//        System.out.println(results.get(0));
    }
}
