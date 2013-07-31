package kr.co.datastreams.dsma.ma.api;

import kr.co.datastreams.dsma.ma.model.AnalysisResult;
import kr.co.datastreams.dsma.ma.model.Sentence;


import java.util.List;

/**
 * 형태소 분석기
 *
 * User: shkim
 * Date: 13. 7. 30
 * Time: 오후 2:50
 *
 */
public interface MorphemeAnalyzer {

    /**
     * 형태소 분석을 실행하여 결과를 AnalysisResult 리스트로 반환한다.
     *
     * @param inputString - 입력 문자열
     * @return result of morpheme analysis or empty list
     */
    Sentence analyze(String inputString);

}
