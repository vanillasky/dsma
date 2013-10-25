package com.datastreams.nlp.ma.test.analyzer;

import com.datastreams.nlp.ma.analyzer.RuleBaseTokenAnalyzer;
import com.datastreams.nlp.ma.analyzer.TokenAnalyzer;
import com.datastreams.nlp.ma.constants.Score;
import com.datastreams.nlp.ma.constants.WordPattern;
import com.datastreams.nlp.ma.model.Eojeol;
import com.datastreams.nlp.ma.model.MorphemeList;
import com.datastreams.nlp.ma.model.Token;
import com.datastreams.nlp.ma.util.EojeolFormatter;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.List;

/**
 *
 * User: shkim
 * Date: 13. 10. 21
 * Time: 오전 9:50
 *
 */
public class RuleBaseTokenAnalyzerTest {

    TokenAnalyzer analyzer = new RuleBaseTokenAnalyzer();

    private EojeolFormatter formatter = new EojeolFormatter() {

        @Override
        public String format(Eojeol eojeol) {
            StringBuilder sb  = new StringBuilder();

            List<MorphemeList> candidates = eojeol.getMorphemes();
            Collections.sort(candidates);

            int len = sb.length();
            int i=0;
            for (MorphemeList each : candidates) {
                if (i == 0) {
                    sb.append(each);
                } else {
                    sb.append("\n");
                }

                i++;
            }

            return sb.toString();

        }
    };

    @Test
    public void testSimpleWord_Heuristic_NJ() throws Exception {
        Token t1 = Token.korean("학교에서만은");
        Eojeol result = analyzer.execute(t1);
        String expected = "<학교,NNG>+<에서만은,JKG>";
        assertEquals(expected, result.asMorphemeString(formatter));
        assertEquals(WordPattern.NJ, result.getMorphemes().get(0).getWordPattern());

        Token t2 = Token.korean("거리에서는");
        result = analyzer.execute(t2);
        expected = "<거리,NNG>+<에서는,JC>";

        assertEquals(1, result.getMorphemes().size());
        assertEquals(expected, result.asMorphemeString(formatter));
        assertEquals(WordPattern.NJ, result.getMorphemes().get(0).getWordPattern());

    }

    @Test
    public void testSimpleWord_Heuristic_ADVJ() throws Exception {
        Token t1 = Token.korean("갑자기는");
        Eojeol result = analyzer.execute(t1);
        String expected = "<갑자기,MAG>+<는,JC>";
        assertEquals(expected, result.asMorphemeString(formatter));
        assertEquals(WordPattern.ADVJ, result.getMorphemes().get(0).getWordPattern());
        assertEquals(1, result.getMorphemes().size());
    }

    @Test
    public void testSimpleWord_NJ() throws Exception {
        Token t3 = Token.korean("가격표시기보다는");
        Eojeol result = analyzer.execute(t3);
        String expected = "<가격표시기,NNG>+<보다는,JC>";

        assertEquals(1, result.getMorphemes().size());
        assertEquals(expected, result.asMorphemeString(formatter));
        assertEquals(WordPattern.NJ, result.getMorphemes().get(0).getWordPattern());
        assertEquals(Score.Success, result.getMorphemes().get(0).getScore());

    }


    @Test
    public void testSimpleWord_NJ_명사추정범주() throws Exception {
        Token t3 = Token.korean("깅깅는");
        Eojeol result = analyzer.execute(t3);
        System.out.println(result.asMorphemeString());
//        String expected = "<가격표시기,NNG>+<보다는,JC>";
//        assertEquals(expected, result.asMorphemeString(formatter));
    }
}
