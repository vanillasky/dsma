package kr.co.datastreams.dsma.ma.api;

import kr.co.datastreams.dsma.ma.model.AnalysisResult;

import java.util.List;

/**
 * 용언 분석기
 *
 * User: shkim
 * Date: 13. 7. 29
 * Time: 오후 3:52
 *
 */
public interface VerbAnalyzer {

    /**
     * 용언을 분석해서 결과를 List<AnalysisResult>에 저장한다.
     *
     * @param candidates - 분석결과 List<AnalysisResult>
     * @param stem - 어간부
     * @param ending - 어미후보
     */
    public void analyze(List<AnalysisResult> candidates, String stem, String ending);


}
