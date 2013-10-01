package kr.co.datastreams.dsma.ma.model;

/**
 * The Scores of morpheme analysis.
 *
 * User: shkim
 * Date: 13. 10. 1
 * Time: 오후 1:30
 *
 */
public enum Score {
    Failure(0)
    , Assumption(10)
    , Heuristic(20)
    , Success(40)
    , AnalyzedDic(100);

    private int score;

    Score(int score) {
        this.score = score;
    }


    public int intValue() {
        return score;
    }
}
