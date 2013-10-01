package kr.co.datastreams.test;

import kr.co.datastreams.dsma.ma.analyzer.HeuristicSearcher;
import kr.co.datastreams.dsma.ma.model.CharType;
import kr.co.datastreams.dsma.ma.model.Eojeol;
import kr.co.datastreams.dsma.ma.model.Token;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 9. 27
 * Time: 오후 4:12
 * To change this template use File | Settings | File Templates.
 */
public class HeuristicSearcherTest {

    HeuristicSearcher searcher = new HeuristicSearcher();

    @Test
    public void testVerb() throws Exception {
        Token token = new Token("가다듬어", CharType.HANGUL, 0);
        String expected = "가다듬어\t<Heuristic>\n\t<가다듬,VV>+<어,EF>\n";
        Eojeol result = searcher.search(token);
        assertEquals(expected, result.asMorphemeString());
    }

    @Test
    public void testNoun() throws Exception {
        Token token = new Token("언제까지라도", CharType.HANGUL, 0);
        String expected = "언제까지라도\t<Heuristic>\n\t<언제,NNG>+<까지라도,JC>\n";
        Eojeol result = searcher.search(token);
        assertEquals(expected, result.asMorphemeString());
    }
}
