package kr.co.datastreams.dsma.ma.internal;

import kr.co.datastreams.dsma.model.AnalysisResult;
import kr.co.datastreams.dsma.model.Eojeol;

import java.util.List;

/**
 * 어절 분석기
 *
 * User: shkim
 * Date: 13. 8. 1
 * Time: 오후 2:52
 *
 * 하나의 입력어절(단어)을 분석하여 결과를 반환한다.
 */
public interface WordAnalyzer {

    /**
     * 하나의 입력어절(단어)을 분석하여 결과를 반환한다.
     *
     * @param word - the word to analyzer
     * @return List<AnalysisResult>
     */
    public List<AnalysisResult> analyzeWord(Eojeol word);
}
