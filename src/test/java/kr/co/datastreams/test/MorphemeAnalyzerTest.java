package kr.co.datastreams.test;


import kr.co.datastreams.dsma.analyzer.BaseTokenAnalysisStrategy;
import kr.co.datastreams.dsma.analyzer.IMorphemeAnalyzer;
import kr.co.datastreams.dsma.tokenizer.CharTypeTokenizer;

import kr.co.datastreams.dsma.analyzer.MorphemeAnalyzer;
import kr.co.datastreams.dsma.tokenizer.Tokenizer;
import kr.co.datastreams.dsma.model.Eojeol;
import kr.co.datastreams.dsma.model.PlainSentence;
import kr.co.datastreams.dsma.model.Sentence;
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
        String expected = "가다듬어\t<Heuristic>\n\t<가다듬,VV>+<어,EF>\n";
        PlainSentence input = new PlainSentence(0, text);

        Sentence result = analyzer.analyze(input);
        List<Eojeol> eojeols = result.getEojeols();
        assertEquals(1, eojeols.size());
        assertEquals(expected, eojeols.get(0).asMorphemeString());
    }



    // 명사 + 조사
    @Test
    public void testNJ_로() throws Exception {
        analyzer = new MorphemeAnalyzer(tokenizer, null, new BaseTokenAnalysisStrategy());
          String[] words = {"돈으로만은", "마음만으로", "힘만으로", "사랑만으로", "돌로", "칼로", "콩으로", "몽둥이로", "만으로", "힘으로", "돌팔매로", "지우개로", "연필로", "직구로"};
//        String[] words = {"만두하고", "한국이랑", "야구와", "축구를", "좋아합니다", "그것만으로는"};

        for (String each : words) {
            PlainSentence sentence = new PlainSentence(0, each);
            Sentence result = analyzer.analyze(sentence);
            List<Eojeol> eojeols = result.getEojeols();
            for (Eojeol eachEojeol : eojeols) {
                System.out.println(eachEojeol.asMorphemeString());
            }

        }

//        String text = "그것만으로는";
//        String expected = "그것만으로는\t<Success>\n\t<그것,NP>+<만으로는,JC>\n";
        String text = "돈으로는";
        String expected = "돈으로는\t<Success>\n\t<그것,NP>+<만으로는,JC>\n";

        PlainSentence input = new PlainSentence(0, text);

        Sentence result = analyzer.analyze(input);
        List<Eojeol> eojeols = result.getEojeols();
        assertEquals(1, eojeols.size());
        assertEquals(expected, eojeols.get(0).asMorphemeString());


    }
}
