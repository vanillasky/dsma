package kr.co.datastreams.test;

import kr.co.datastreams.dsma.dic.AnalyzedDic;
import kr.co.datastreams.dsma.ma.model.AnalysisResult;
import kr.co.datastreams.dsma.ma.model.Word;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 8. 9
 * Time: 오전 9:25
 * To change this template use File | Settings | File Templates.
 */
public class AnalyzedDicTest {

    @Test
    // 용언 + 어미
    public void test_VM() throws Exception {
        String text = "같았다";  // <같, VV>  + <었, EP> + <다, EM>
        Word word = AnalyzedDic.find(text);
        AnalysisResult result = word.getAnalysisResults().get(0);
        assertEquals("같았다/<같, VV> + <었, EP> + <다, EM>", result.asResult());

    }

    @Test
    // 체언 + 조사
    public void test_NJ() throws Exception {
        String text = "거기에";
        Word word = AnalyzedDic.find(text);
        AnalysisResult result = word.getAnalysisResults().get(0);
        assertEquals("거기에/<거기, NN> + <에, JO>", result.asResult());

        text = "그걸로";
        word = AnalyzedDic.find(text);
        result = word.getAnalysisResults().get(0);
        assertEquals("그걸로/<그것, NP> + <으로, JO>", result.asResult());

        text = "걸";
        word = AnalyzedDic.find(text);
        result = word.getAnalysisResults().get(0);
        assertEquals("걸/<것, NX> + <을, JO>", result.asResult());

    }

    @Test
    // 체언 + 용언화접미사 + 어미 == (체언 + '이' + 선어미 + 어미)
    public void test_NSM() throws Exception {
        String text = "것이었다";
        Word word = AnalyzedDic.find(text);
        AnalysisResult result = word.getAnalysisResults().get(0);
        assertEquals("것이었다/<것, NX> + <이, CP> + <었, EP> + <다, EM>", result.asResult());

        text = "거니";
        word = AnalyzedDic.find(text);
        result = word.getAnalysisResults().get(0);
        assertEquals("거니/<것, NX> + <이, CP> + <니, EM>", result.asResult());

        text = "거다";
        word = AnalyzedDic.find(text);
        result = word.getAnalysisResults().get(0);
        assertEquals("거다/<것, NX> + <이, CP> + <다, EM>", result.asResult());

        text = "거란";
        word = AnalyzedDic.find(text);
        result = word.getAnalysisResults().get(0);
        assertEquals("거란/<것, NX> + <이, CP> + <란, EM>", result.asResult());

        text = "누군가";
        word = AnalyzedDic.find(text);
        result = word.getAnalysisResults().get(0);
        System.out.println(result);
        assertEquals("누구/<누구, NP> + <이, CP> + <ㄴ가, EM>", result.asResult());


    }


    @Test
    // 단일어
    public void test_AID() throws Exception {
        String text = "그래서";
        Word word = AnalyzedDic.find(text);
        AnalysisResult result = word.getAnalysisResults().get(0);
        assertEquals("그래서/<그래서, AD>", result.asResult());

        text = "그러한";
        word = AnalyzedDic.find(text);
        result = word.getAnalysisResults().get(0);
        assertEquals("그러한/<그러한, VJ>", result.asResult());
    }
}
