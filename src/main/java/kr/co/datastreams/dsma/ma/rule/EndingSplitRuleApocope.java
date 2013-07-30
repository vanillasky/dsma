package kr.co.datastreams.dsma.ma.rule;

import kr.co.datastreams.commons.util.StringUtil;
import kr.co.datastreams.dsma.dic.EomiDic;
import kr.co.datastreams.dsma.ma.model.Variant;

/**
 *
 * User: shkim
 * Date: 13. 7. 25
 * Time: 오전 11:09
 * '아/어'가 탈락되는 어미
 * 어간부의 끝음절이 '아/어'가 아니고 ('아/어'+어미부)가 사전에 있으면 '아/어'가 생략된 어미로 추정
 */
public class EndingSplitRuleApocope extends BaseEndingRule {


    public EndingSplitRuleApocope(String stemCandidate, String endingCandidate) {
        super(stemCandidate, endingCandidate);
    }

    @Override
    public boolean canHandle() {
        if (endOfStemCandidate != '아'
            && StringUtil.nvl(endingCandidate).length() > 0
            && EomiDic.findEndingWith('아', endingCandidate) != null ) {

            return true;
        }
        else if (endOfStemCandidate != '어'
                && StringUtil.nvl(endingCandidate).length() > 0
                && EomiDic.findEndingWith('어', endingCandidate) != null ) {
            return true;
        }

        return false;
    }

    @Override
    public Variant split() {
        if (!canHandle())
            return Variant.EMPTY;

        if (endOfStemCandidate != '아') {
            return Variant.createEnding(stemCandidate, "아" + endingCandidate);
        }

        if (endOfStemCandidate != '어') {
            return Variant.createEnding(stemCandidate, "어" + endingCandidate);
        }
        return Variant.EMPTY;
    }
}
