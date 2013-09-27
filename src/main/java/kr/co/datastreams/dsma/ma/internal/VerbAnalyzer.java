package kr.co.datastreams.dsma.ma.internal;

import kr.co.datastreams.dsma.ma.model.AnalysisResult;
import kr.co.datastreams.dsma.ma.model.Eojeol;

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
     * @param Word - 어절
     * @param location - stem/ending part 분석 시작 위치
     */
    public void analyze(List<AnalysisResult> candidates, Eojeol word, int location);


}
