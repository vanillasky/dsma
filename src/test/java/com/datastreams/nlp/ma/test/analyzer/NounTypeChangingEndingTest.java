package com.datastreams.nlp.ma.test.analyzer;

import com.datastreams.nlp.ma.analyzer.NounAnalyzer;
import com.datastreams.nlp.ma.constants.ConclusionPoint;
import com.datastreams.nlp.ma.model.MorphemeList;
import com.datastreams.nlp.ma.model.Token;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;

/**
 *
 * 명사형 전성어미 관련 테스트
 * User: shkim
 * Date: 13. 10. 25
 * Time: 오후 3:53
 *
 */
public class NounTypeChangingEndingTest {

    NounAnalyzer na = new NounAnalyzer();

    // 기 + 조사로 끝나는 패턴: 오기를
    // 오기<명사> + 를<조사>, 오<동사> + 기<명사형 전성어미> + 를<조사>
    @Test
    public void test_기_조사() throws Exception {
       Token token = Token.korean("오기를");
       printResult(token);
    }

    @Test
    public void test_2음절이상후절어_분석종료() throws Exception {
        Token token = Token.korean("가격표시기로만은");
        List<MorphemeList> candidates = anlyze(token);
        assertEquals(ConclusionPoint.MoreThan2SyllableGMFound, candidates.get(0).getConclusion());
    }

    @Test
    public void test_2음절이상후절어있지만_체언으로_확정되지않은_어절() throws Exception {
        Token token = Token.korean("배움에는");
        List<MorphemeList> candidates = anlyze(token);
        assertEquals(ConclusionPoint.NA, candidates.get(0).getConclusion());
    }

    private void printResult(Token token) {
        System.out.println("=====" + token.getString());
        List<MorphemeList> candidates = na.execute(token);
        for (MorphemeList each : candidates) {
            System.out.println("  " + each);
        }
    }

    private List<MorphemeList> anlyze(Token token) {
        return na.execute(token);
    }

//    // 체언 + 용언화접미사 + 'ㅁ/기' + 조사: 사랑하기가
//    @Test
//    public void test_기_조사() throws Exception {
//        // 오기(NNG) + 를(JKO), 오(V) + 기(
//        Token token = Token.korean("사랑하기가");
//        NounAnalyzer na = new NounAnalyzer();
//        List<MorphemeList> candidates = na.execute(token);
//        System.out.println(candidates.get(0));
//
//    }

}
