package kr.co.datastreams.test;

import kr.co.datastreams.dsma.dic.AnalyzedDic;
import kr.co.datastreams.dsma.ma.model.AnalysisResult;
import kr.co.datastreams.dsma.ma.model.Word;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * User: shkim
 * Date: 13. 8. 9
 * Time: 오전 9:25
 *
 */
public class AnalyzedDicTest {

    @Test
    // 용언 + 어미
    public void test_VM() throws Exception {
        String text = "같았다";  // <같, VV>  + <었, EP> + <다, EM>
        Word word = AnalyzedDic.find(text);
        AnalysisResult result = word.getAnalysisResults().get(0);
        assertEquals("같았다/<같, VV> + <었, EP> + <다, EM>", result.asString());

        text = "있었다";
        word = AnalyzedDic.find(text);
        result = word.getAnalysisResults().get(0);
        assertEquals("있었다/<있, VV> + <었, EP> + <다, EM>", result.asString());

        text = "했지만";
        word = AnalyzedDic.find(text);
        result = word.getAnalysisResults().get(0);
        assertEquals("했지만/<하, VV> + <었, EP> + <지만, EM>", result.asString());

    }

    @Test
    public void test_VMJ() throws Exception {
        String text = "했음은";
        Word word = AnalyzedDic.find(text);
        AnalysisResult result = word.getAnalysisResults().get(0);
        System.out.println(result);
        assertEquals("했음은/<하, VV> + <었, EP> + <음, EM> + <은, JO>", result.asString());
    }


    @Test
    // 체언 + 조사
    public void test_NJ() throws Exception {
        String text = "거기에";
        Word word = AnalyzedDic.find(text);
        AnalysisResult result = word.getAnalysisResults().get(0);
        assertEquals("거기에/<거기, NN> + <에, JO>", result.asString());

        text = "그걸로";
        word = AnalyzedDic.find(text);
        result = word.getAnalysisResults().get(0);
        assertEquals("그걸로/<그것, NP> + <으로, JO>", result.asString());

        text = "걸";
        word = AnalyzedDic.find(text);
        result = word.getAnalysisResults().get(0);
        assertEquals("걸/<것, NX> + <을, JO>", result.asString());

    }

    @Test
    // 체언 + 용언화접미사 + 어미 == (체언 + '이' + 선어미 + 어미)
    public void test_NSM() throws Exception {
        String text = "것이었다";
        Word word = AnalyzedDic.find(text);
        AnalysisResult result = word.getAnalysisResults().get(0);
        assertEquals("것이었다/<것, NX> + <이, CP> + <었, EP> + <다, EM>", result.asString());

        text = "거니";
        word = AnalyzedDic.find(text);
        result = word.getAnalysisResults().get(0);
        assertEquals("거니/<것, NX> + <이, CP> + <니, EM>", result.asString());

        text = "거다";
        word = AnalyzedDic.find(text);
        result = word.getAnalysisResults().get(0);
        assertEquals("거다/<것, NX> + <이, CP> + <다, EM>", result.asString());

        text = "거란";
        word = AnalyzedDic.find(text);
        result = word.getAnalysisResults().get(0);
        assertEquals("거란/<것, NX> + <이, CP> + <란, EM>", result.asString());

        text = "누군가";
        word = AnalyzedDic.find(text);
        result = word.getAnalysisResults().get(0);
        System.out.println(result);
        assertEquals("누군가/<누구, NP> + <이, CP> + <ㄴ가, EM>", result.asString());

        text = "뭔지를";
        word = AnalyzedDic.find(text);
        result = word.getAnalysisResults().get(0);
        System.out.println(result);
        assertEquals("뭔지를/<무엇, NP> + <이, CP> + <ㄴ지를, EM>", result.asString());
    }


    @Test
    // 단일어
    public void test_AID() throws Exception {
        String text = "그래서";
        Word word = AnalyzedDic.find(text);
        AnalysisResult result = word.getAnalysisResults().get(0);
        assertEquals("그래서/<그래서, AD>", result.asString());

        text = "그러한";
        word = AnalyzedDic.find(text);
        result = word.getAnalysisResults().get(0);
        assertEquals("그러한/<그러한, VJ>", result.asString());
    }
}
