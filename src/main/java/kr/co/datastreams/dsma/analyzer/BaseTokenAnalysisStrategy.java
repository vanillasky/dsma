package kr.co.datastreams.dsma.analyzer;

import kr.co.datastreams.dsma.dic.AnalyzedDic;
import kr.co.datastreams.dsma.model.Eojeol;
import kr.co.datastreams.dsma.model.Score;
import kr.co.datastreams.dsma.model.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 10. 2
 * Time: 오후 2:37
 * To change this template use File | Settings | File Templates.
 */
public class BaseTokenAnalysisStrategy implements TokenAnalysisStrategy {

    protected final static Logger logger = LoggerFactory.getLogger(BaseTokenAnalysisStrategy.class);

    @Override
    public Eojeol execute(Token token) {
        return searchAnalyzedDic(token);
    }


    protected Eojeol searchAnalyzedDic(Token token) {
        Eojeol analyzedEojeol = AnalyzedDic.find(token.getString());
        if (analyzedEojeol != null) {
            if (logger.isDebugEnabled()) logger.debug("Found word in analyzed dictionary: {}", token.getString());
            analyzedEojeol = analyzedEojeol.copy(token.getIndex());
            analyzedEojeol.setScore(Score.AnalyzedDic);
            return analyzedEojeol;
        }
        return null;
    }
}
