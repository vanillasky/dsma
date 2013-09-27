package kr.co.datastreams.test;

import kr.co.datastreams.dsma.ma.tokenizer.CharTypeTokenizer;
import kr.co.datastreams.dsma.ma.analyzer.IMorphemeAnalyzer;
import kr.co.datastreams.dsma.ma.analyzer.MorphemeAnalyzer;
import kr.co.datastreams.dsma.ma.tokenizer.Tokenizer;
import kr.co.datastreams.dsma.ma.model.Eojeol;
import kr.co.datastreams.dsma.ma.model.PlainSentence;
import kr.co.datastreams.dsma.ma.model.Sentence;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;

/**
 *
 * User: shkim
 * Date: 13. 9. 16
 * Time: 오후 4:42
 *
 */
public class MorphemeAnalyzerTest {

    Tokenizer tokenizer = new CharTypeTokenizer();
    IMorphemeAnalyzer analyzer = new MorphemeAnalyzer(tokenizer);

    @Test
    public void testAnalyzedToken() throws Exception {
        String text = "감기는 만족스런 것이었다";
//        String text = "목감기 때문에 골이 띵하다ㅠㅠ 이노므 목감기";
        PlainSentence input = new PlainSentence(0, text);

        Sentence result = analyzer.analyze(input);
        List<Eojeol> eojeols = result.getEojeols();
        assertEquals(3, eojeols.size());
        for (Eojeol each :eojeols) {
            System.out.println(each.asMorphemeString());
        }

    }


    @Test
    public void testHeuristicSearch_Verb() throws Exception {
        String text = "가다듬어";
        String expected = "가다듬어\n\t<가다듬,VV>+<어,EF>\n";
        PlainSentence input = new PlainSentence(0, text);

        Sentence result = analyzer.analyze(input);
        List<Eojeol> eojeols = result.getEojeols();
        assertEquals(1, eojeols.size());
        assertEquals(expected, eojeols.get(0).asMorphemeString());
    }
}
