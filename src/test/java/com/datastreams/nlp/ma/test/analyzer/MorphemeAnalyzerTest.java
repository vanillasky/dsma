package com.datastreams.nlp.ma.test.analyzer;

import com.datastreams.nlp.ma.analyzer.IMorphemeAnalyzer;
import com.datastreams.nlp.ma.analyzer.MorphemeAnalyzer;

import com.datastreams.nlp.ma.analyzer.RuleBaseTokenAnalyzer;
import com.datastreams.nlp.ma.model.Eojeol;
import com.datastreams.nlp.ma.model.Sentence;
import com.datastreams.nlp.ma.strategy.SimpleWordCountStrategy;
import com.datastreams.nlp.ma.strategy.WordCountStrategy;
import com.datastreams.nlp.ma.tokenizer.CharTypeTokenizer;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 *
 * User: shkim
 * Date: 13. 10. 18
 * Time: 오후 5:05
 *
 */
public class MorphemeAnalyzerTest {

    // 기분석 사전에 있는 단어 테스트
    @Test
    public void testWordIndAnalyzedDic() throws Exception {
        IMorphemeAnalyzer analyzer = new MorphemeAnalyzer();
        String text = "감기는 만족스런 것이었다";

        Sentence result = analyzer.analyze(text);

        List<Eojeol> eojeols = result.getEojeols();
        assertEquals(3, eojeols.size());
        for (Eojeol each :eojeols) {
            System.out.println(each.asMorphemeString());
        }

    }


    @Test
    public void testWordCount() throws Exception {
        WordCountStrategy wordCountStrategy = new SimpleWordCountStrategy();
        IMorphemeAnalyzer analyzer = new MorphemeAnalyzer(new CharTypeTokenizer(), new RuleBaseTokenAnalyzer(), wordCountStrategy);
        String text = "가다듬어 가다듬고 가다듬어";

        analyzer.analyze(text);
        assertEquals(2, (int)wordCountStrategy.frequencyOfUse("가다듬어"));
    }

    @Test
    public void testSentence() throws Exception {
        IMorphemeAnalyzer analyzer = new MorphemeAnalyzer();
        String text = "최경주(32ㆍ슈페리어)가 미국프로골프(PGA)투어 마지막 공식대회인 투어챔피언십(총상금 500만달러)에서 공동9위에 오르며 시즌 7번째 톱10에 진입했다.";

        Sentence result = analyzer.analyze(text);

        List<Eojeol> eojeols = result.getEojeols();
        //assertEquals(3, eojeols.size());
        for (Eojeol each :eojeols) {
            System.out.println(each.asMorphemeString());
        }

    }

}
