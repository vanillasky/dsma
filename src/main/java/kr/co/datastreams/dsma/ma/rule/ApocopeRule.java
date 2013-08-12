package kr.co.datastreams.dsma.ma.rule;

import kr.co.datastreams.commons.util.StringUtil;
import kr.co.datastreams.dsma.dic.EomiDic;
import kr.co.datastreams.dsma.ma.model.Variant;

/**
 * '아/어'가 탈락되는 어미
 * 어간부의 끝음절이 '아/어'가 아니고 ('아/어'+어미부)가 사전에 있으면 '아/어'가 생략된 어미로 추정
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

    char[] charsToCheck = {'아', '어'};

    @Override
    public boolean canHandle() {
        if (StringUtil.nvl(endingPart).length() == 0) {
            return false;
        }

        for (int i=0; i < charsToCheck.length; i++) {
            if (lastCharInStemPart != charsToCheck[i] && EomiDic.exists(String.valueOf(charsToCheck[i]) + endingPart)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Variant split() {
        if (!canHandle())
            return Variant.EMPTY;

        // 아/어가 생략된 어미 해서는 -> 하(동사)+어서는(조사)로 분리
        //TODO 다른 경우가 없는지 확인해서 추가
        if (lastCharInStemPart == '해') {
            return Variant.createEnding(stemPart.substring(0, stemPart.length()-1) + "하", "어" + endingPart);
        }

        return Variant.EMPTY;
    }
}
