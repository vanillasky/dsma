package kr.co.datastreams.dsma.analyzer;

import kr.co.datastreams.dsma.model.Eojeol;
import kr.co.datastreams.dsma.model.Token;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 10. 2
 * Time: 오후 2:30
 * To change this template use File | Settings | File Templates.
 */
public interface TokenAnalysisStrategy {
    public Eojeol execute(Token token);
}
