package kr.co.datastreams.test;

import kr.co.datastreams.dsma.ma.internal.HeuristicAnalyzer;
import kr.co.datastreams.dsma.ma.model.AnalysisResult;
import kr.co.datastreams.dsma.ma.model.CharType;
import kr.co.datastreams.dsma.ma.model.Eojeol;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 8. 20
 * Time: 오후 4:31
 * To change this template use File | Settings | File Templates.
 */
public class HeuristicAnalyzerTest {

    @Test
    public void testVerb() throws Exception {
        HeuristicAnalyzer analyzer = new HeuristicAnalyzer();
        Eojeol w = new Eojeol("가다듬어", CharType.HANGUL);
        AnalysisResult result = analyzer.analyze(w);
        assertEquals("가다듬", result.getStem());
        assertEquals("어", result.getEnding());
    }

    @Test
    public void testNoun() throws Exception {
        HeuristicAnalyzer analyzer = new HeuristicAnalyzer();
        Eojeol w = new Eojeol("언제까지라도", CharType.HANGUL);
        AnalysisResult result = analyzer.analyze(w);
        System.out.println(result.asString());
        assertEquals("언제", result.getStem());
        assertEquals("까지라도", result.getJosa());
    }
}
