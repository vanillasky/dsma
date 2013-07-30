package kr.co.datastreams.dsma.ma.rule;

import kr.co.datastreams.dsma.dic.EomiDic;
import kr.co.datastreams.dsma.ma.model.Variant;

/**
 *
 * User: shkim
 * Date: 13. 7. 25
 * Time: 오후 2:10
 *
 * 해로 끝나면서 '어 + 어미'가 사전에 있는 경우 -> '(어간 + 하) + 어'
 */
public class EndingSplitRuleEndsWithHae extends BaseEndingRule {

    public EndingSplitRuleEndsWithHae(String stemCandidate, String endingCandidate) {
        super(stemCandidate, endingCandidate);
    }

    @Override
    public boolean canHandle() {
        return endOfStemCandidate == '해' && EomiDic.exists("어" + endingCandidate);

    }

    @Override
    public Variant split() {
        if (!canHandle())
            return Variant.EMPTY;

        String initial = new StringBuilder(cutStemTail()).append('하').toString();
        String end = "어" + endingCandidate;
        return Variant.createEnding(initial, end);
    }
}
