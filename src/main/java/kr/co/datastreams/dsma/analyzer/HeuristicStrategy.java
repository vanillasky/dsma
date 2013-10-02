package kr.co.datastreams.dsma.analyzer;

import kr.co.datastreams.dsma.model.Eojeol;
import kr.co.datastreams.dsma.model.Token;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 10. 2
 * Time: 오후 2:44
 * To change this template use File | Settings | File Templates.
 */
public class HeuristicStrategy extends BaseTokenAnalysisStrategy {

    @Override
    public Eojeol execute(Token token) {
        Eojeol result = searchAnalyzedDic(token);
        if (result != null) {
            return result;
        }

        return HeuristicSearcher.search(token);
    }
}
