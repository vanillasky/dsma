package com.datastreams.nlp.ma.constants;

/**
 *
 * 형태소 분석 결과 점수
 *
 * User: shkim
 * Date: 13. 10. 15
 * Time: 오전 11:04
 *
 */
public class Score {

    public static final int threshold = 54;


    public static final Score Failure = new Score(AnalysisScore.Failure);    // 분석 실패
    public static final Score AnalyzedDic = new Score(AnalysisScore.ADic);   // 기분석사전 단어
    public static final Score Analysis = new Score(AnalysisScore.Analysis);  // 분석 레벨
    public static final Score Success = new Score(AnalysisScore.Correct);    // 분석 성공
    public static final Score Candidate = new Score(AnalysisScore.Candidate); // 후보 레벨


    public static final Score MeetSeparationConstraint = new Score(AnalysisScore.Analysis.intValue() + 5); // 문법형태소 분리제약을 충족함


    private AnalysisScore analysisScore;

    private Integer value;

    public Score(int value) {
        this.value = value;
    }

    public Score(AnalysisScore analysisScore) {
        this.analysisScore = analysisScore;
    }

    public Integer intValue() {
        return analysisScore.intValue();
    }

    public String toString() {
        return "[" + analysisScore + ":" + intValue() +"]";
    }

    public static enum AnalysisScore {
          Failure(0)
        , Candidate(40)
        , Analysis(90)
        , Correct(100)
        , ADic(101)
        ;

        private final int score;

        AnalysisScore(int value) {
            score = value;
        }

        public int intValue() {
            return score;
        }
    }


}


