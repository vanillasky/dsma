package kr.co.datastreams.dsma.analyzer;

import kr.co.datastreams.dsma.model.Eojeol;
import kr.co.datastreams.dsma.model.Token;

/**
 * 어절 단위 형태소 분석기
 *
 * User: shkim
 * Date: 13. 10. 2
 * Time: 오후 3:24
 *
 */
public interface WordAnalyzer {

    public Eojeol analyzeWord(Token token);
}
