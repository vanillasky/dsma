package kr.co.datastreams.dsma.ma;

import kr.co.datastreams.dsma.ma.model.AnalysisResult;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 8. 6
 * Time: 오후 4:49
 * To change this template use File | Settings | File Templates.
 */
public class MorphemeBuilder {
    public static void buildPattern(WordPattern pattern, AnalysisResult result, List<AnalysisResult> candidates) {
        String ending = result.getPrefinalEnding() == null ? result.getEnding() : result.getPrefinalEnding();

        result.setWordPattern(pattern);
        //result.setPos(PosTag.VERB);

        if (result.getScore() == AnalysisResult.SCORE_CORRECT) {
            candidates.add(result);
        }
        else {

        }

    }
}
