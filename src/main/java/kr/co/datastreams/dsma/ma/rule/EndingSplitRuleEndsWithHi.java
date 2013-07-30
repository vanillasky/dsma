package kr.co.datastreams.dsma.ma.rule;

import kr.co.datastreams.dsma.dic.EomiDic;
import kr.co.datastreams.dsma.ma.model.Variant;

/**
 *
 * User: shkim
 * Date: 13. 7. 25
 * Time: 오후 2:15
 *
 * 어간부가 '히'로 끝나고 ('이' + 어미)가 사전에 있는 경우 '어간+하' + '어'
 */
public class EndingSplitRuleEndsWithHi extends BaseEndingRule {


    public EndingSplitRuleEndsWithHi(String stemCandidate, String endingCandidate) {
        super(stemCandidate, endingCandidate);
    }

    @Override
    public boolean canHandle() {
        return endOfStemCandidate == '히' && EomiDic.exists("이" + endingCandidate);
    }

    @Override
    public Variant split() {
        if (!canHandle())
            return Variant.EMPTY;

        String initial = new StringBuilder(cutStemTail()).append('하').toString();
        String end = "이" + endingCandidate;
        return Variant.createEnding(initial, end);
    }
}
