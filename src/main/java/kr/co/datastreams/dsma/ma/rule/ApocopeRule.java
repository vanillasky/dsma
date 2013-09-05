package kr.co.datastreams.dsma.ma.rule;

import kr.co.datastreams.commons.util.StringUtil;
import kr.co.datastreams.dsma.dic.EomiDic;
import kr.co.datastreams.dsma.ma.model.Variant;

/**
 * '아/어'가 탈락되는 어미
 * 어간부의 끝음절이 '아/어'가 아니고 ('아/어'+어미부)가 사전에 있으면 '아/어'가 생략된 어미로 추정
 * 그러나 꺼도 -> '끄'+'어도'와 같이 '으'탈락인 경우에도 여기서 걸러지는 경우가 있다. 따라서 용언에 대해서
 * 변이체(Variant)가 만들어지면 불규칙 용언의 원형을 복원하는 과정을 거쳐야 한다.
 *
 *
 * User: shkim
 * Date: 13. 7. 25
 * Time: 오전 11:09
 *
 */
public class ApocopeRule extends BaseEndingCombineRule {


    public ApocopeRule(String stemPart, String endingPart) {
        super(stemPart, endingPart);
    }

    @Override
    public boolean canHandle() {
        if (StringUtil.nvl(endingPart).length() == 0) {
            return false;
        }

        if (lastCharInStemPart != '아' && lastCharInStemPart != '어') {
            if (EomiDic.exists(String.valueOf('아' + endingPart)) ||
                EomiDic.exists(String.valueOf('어' + endingPart))) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Variant split() {
        if (!canHandle())
            return Variant.EMPTY;


        String candidate1 = EomiDic.search(String.valueOf('아' + endingPart));
        String candidate2 = EomiDic.search(String.valueOf('어' + endingPart));
        String candidate = candidate2 == null ? candidate1 : candidate2;

        if (candidate != null) {
            String stem = stemPart.substring(0, stemPart.length());
            String ending = candidate;

            return Variant.createWithEnding(stem, ending);
        }

        return Variant.EMPTY;
    }
}
