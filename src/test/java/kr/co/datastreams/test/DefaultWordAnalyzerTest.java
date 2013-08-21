package kr.co.datastreams.test;

import kr.co.datastreams.dsma.ma.internal.DefaultWordAnalyzer;
import kr.co.datastreams.dsma.ma.internal.WordAnalyzer;
import kr.co.datastreams.dsma.ma.model.AnalysisResult;
import kr.co.datastreams.dsma.ma.model.CharType;
import kr.co.datastreams.dsma.ma.model.Word;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;

/**
 *
 * User: shkim
 * Date: 13. 8. 5
 * Time: 오전 10:42
 *
 */
public class DefaultWordAnalyzerTest {

    @Test
    public void test() throws Exception {
        String word = "사용하여";
        WordAnalyzer wa = new DefaultWordAnalyzer();
        List<AnalysisResult> result = wa.analyzeWord(new Word(word, CharType.HANGUL, 0));
        for (AnalysisResult each : result) {
            System.out.println(each);
        }

    }

    @Test
    public void test_압니다() throws Exception {
        String word = "압니다";
        WordAnalyzer wa = new DefaultWordAnalyzer();
        List<AnalysisResult> result = wa.analyzeWord(new Word(word, CharType.HANGUL, 0));
        for (AnalysisResult each : result) {
            System.out.println(each);
        }
    }

    @Test
    public void test_학생이었음은() throws Exception {
        String word = "학생이었음은";
        WordAnalyzer wa = new DefaultWordAnalyzer();
        List<AnalysisResult> result = wa.analyzeWord(new Word(word, CharType.HANGUL, 0));
        for (AnalysisResult each : result) {
            System.out.println(each);
        }
    }

    @Test
    public void test_사랑스러웠다() throws Exception {
        String word = "사랑스러웠다";
        WordAnalyzer wa = new DefaultWordAnalyzer();
        List<AnalysisResult> result = wa.analyzeWord(new Word(word, CharType.HANGUL, 0));
        for (AnalysisResult each : result) {
            System.out.println(each);
        }
    }

    @Test
    public void test_지나가다() throws Exception {
        String word = "지나가다";
        WordAnalyzer wa = new DefaultWordAnalyzer();
        List<AnalysisResult> result = wa.analyzeWord(new Word(word, CharType.HANGUL, 0));
        for (AnalysisResult each : result) {
            System.out.println(each);
        }
    }

    @Test
    public void test_올라가다() throws Exception {
        String word = "올라가다가";
        WordAnalyzer wa = new DefaultWordAnalyzer();
        List<AnalysisResult> result = wa.analyzeWord(new Word(word, CharType.HANGUL, 0));
        for (AnalysisResult each : result) {
            System.out.println(each);
        }
    }

    @Test
    public void test_AnalyzedWords() throws Exception {
        String word = "텐데";
        WordAnalyzer wa = new DefaultWordAnalyzer();
        List<AnalysisResult> results = wa.analyzeWord(new Word(word, CharType.HANGUL, 0));
        assertEquals("텐데/<터, NX> + <이, CP> + <ㄴ데, EM>", results.get(0).asString());
        assertEquals(AnalysisResult.SCORE_ANALYZED_DIC, results.get(0).getScore());
    }
}
