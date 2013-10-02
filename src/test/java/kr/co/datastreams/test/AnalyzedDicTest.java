package kr.co.datastreams.test;

import kr.co.datastreams.dsma.dic.AnalyzedDic;
import kr.co.datastreams.dsma.model.Eojeol;
import kr.co.datastreams.dsma.model.MorphemeList;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.Assert.*;

/**
 *
 * User: shkim
 * Date: 13. 8. 9
 * Time: 오전 9:25
 *
 */
public class AnalyzedDicTest {

    String testString1 = "같았다    <같, VV>  + <었, EP> + <다, EF>";

    static AnalyzedDic dic;

    static {
        try {
            Constructor constructor = AnalyzedDic.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            dic = (AnalyzedDic)constructor.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Before
    public void setUp() throws Exception {

    }


    @Test
    public void testParseMorphemes() throws Exception {
        Method method = AnalyzedDic.class.getDeclaredMethod("parseMorphemes", String.class);
        method.setAccessible(true);

        List<String[]> result = (List<String[]>)method.invoke(dic, testString1);
        String[] expected = {"<같, VV>", "<었, EP>", "<다, EF>"};
        assertEquals(1, result.size());
        assertArrayEquals(expected, result.get(0));
    }

    @Test
    public void testParseMorphemes_분석결과2개() throws Exception {
        String testString = "감기는    <감기, NNG> + <는, JX> | <감기, VV> + <는, ETM>";
        Method method = AnalyzedDic.class.getDeclaredMethod("parseMorphemes", String.class);
        method.setAccessible(true);

        List<String[]> result = (List<String[]>)method.invoke(dic, testString);
        String[] expected1 = {"<감기, NNG>", "<는, JX>"};
        String[] expected2 = {"<감기, VV>", "<는, ETM>"};

        assertEquals(2, result.size());
        assertArrayEquals(expected1, result.get(0));
        assertArrayEquals(expected2, result.get(1));
    }

    @Test
    public void testAnalyzedEojeol_분석결과2개() throws Exception {
        String text = "감기는";
        Eojeol eojeol = AnalyzedDic.find(text);
        List<MorphemeList> morphemes = eojeol.getMorphemes();

        assertEquals(2, morphemes.size());
        System.out.println(eojeol.asMorphemeString());
        assertEquals("감기는\t<AnalyzedDic>\n\t<감기,NNG>+<는,JX>\n\t<감기,VV>+<는,ETM>\n", eojeol.asMorphemeString());
    }

    @Test
    public void testEojeols() throws Exception {
        String text = "같았다";
        Eojeol eojeol = AnalyzedDic.find(text);
        List<MorphemeList> morphemes = eojeol.getMorphemes();

        assertEquals(1, morphemes.size());
        assertEquals("같았다\t<AnalyzedDic>\n\t<같,VV>+<었,EP>+<다,EF>\n", eojeol.asMorphemeString());

        text = "있었다";
        eojeol = AnalyzedDic.find(text);
        morphemes = eojeol.getMorphemes();
        assertEquals(1, morphemes.size());
        assertEquals("있었다\t<AnalyzedDic>\n\t<있,VV>+<었,EP>+<다,EF>\n", eojeol.asMorphemeString());

        text = "했지만";
        eojeol = AnalyzedDic.find(text);
        assertEquals("했지만\t<AnalyzedDic>\n\t<하,VV>+<었,EP>+<지만,EC>\n", eojeol.asMorphemeString());
    }

//    @Test
//    public void test_VMJ() throws Exception {
//        String text = "했음은";
//        Eojeol word = AnalyzedDic.find(text);
//        AnalysisResult result = word.getAnalysisResults().get(0);
//        System.out.println(result);
//        assertEquals("했음은/<하, VV> + <었, EP> + <음, EM> + <은, JO>", result.asString());
//    }
//
//
//    @Test
//    // 체언 + 조사
//    public void test_NJ() throws Exception {
//        String text = "거기에";
//        Eojeol word = AnalyzedDic.find(text);
//        AnalysisResult result = word.getAnalysisResults().get(0);
//        assertEquals("거기에/<거기, NN> + <에, JO>", result.asString());
//
//        text = "그걸로";
//        word = AnalyzedDic.find(text);
//        result = word.getAnalysisResults().get(0);
//        assertEquals("그걸로/<그것, NP> + <으로, JO>", result.asString());
//
//        text = "걸";
//        word = AnalyzedDic.find(text);
//        result = word.getAnalysisResults().get(0);
//        assertEquals("걸/<것, NX> + <을, JO>", result.asString());
//
//    }
//
//    @Test
//    // 체언 + 용언화접미사 + 어미 == (체언 + '이' + 선어미 + 어미)
//    public void test_NSM() throws Exception {
//        String text = "것이었다";
//        Eojeol word = AnalyzedDic.find(text);
//        AnalysisResult result = word.getAnalysisResults().get(0);
//        assertEquals("것이었다/<것, NX> + <이, CP> + <었, EP> + <다, EM>", result.asString());
//
//        text = "거니";
//        word = AnalyzedDic.find(text);
//        result = word.getAnalysisResults().get(0);
//        assertEquals("거니/<것, NX> + <이, CP> + <니, EM>", result.asString());
//
//        text = "거다";
//        word = AnalyzedDic.find(text);
//        result = word.getAnalysisResults().get(0);
//        assertEquals("거다/<것, NX> + <이, CP> + <다, EM>", result.asString());
//
//        text = "거란";
//        word = AnalyzedDic.find(text);
//        result = word.getAnalysisResults().get(0);
//        assertEquals("거란/<것, NX> + <이, CP> + <란, EM>", result.asString());
//
//        text = "누군가";
//        word = AnalyzedDic.find(text);
//        result = word.getAnalysisResults().get(0);
//        System.out.println(result);
//        assertEquals("누군가/<누구, NP> + <이, CP> + <ㄴ가, EM>", result.asString());
//
//        text = "뭔지를";
//        word = AnalyzedDic.find(text);
//        result = word.getAnalysisResults().get(0);
//        System.out.println(result);
//        assertEquals("뭔지를/<무엇, NP> + <이, CP> + <ㄴ지를, EM>", result.asString());
//    }
//
//
//    @Test
//    // 단일어
//    public void test_AID() throws Exception {
//        String text = "그래서";
//        Eojeol word = AnalyzedDic.find(text);
//        AnalysisResult result = word.getAnalysisResults().get(0);
//        assertEquals("그래서/<그래서, AD>", result.asString());
//
//        text = "그러한";
//        word = AnalyzedDic.find(text);
//        result = word.getAnalysisResults().get(0);
//        assertEquals("그러한/<그러한, VJ>", result.asString());
//    }
}
