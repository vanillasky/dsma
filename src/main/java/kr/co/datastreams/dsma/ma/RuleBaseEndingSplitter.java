package kr.co.datastreams.dsma.ma;

import kr.co.datastreams.commons.util.StringUtil;
import kr.co.datastreams.dsma.dic.EomiDic;
import kr.co.datastreams.dsma.ma.api.EndingSplitter;
import kr.co.datastreams.dsma.ma.model.Variant;
import kr.co.datastreams.dsma.ma.rule.*;

import java.util.List;

/**
 *
 * User: shkim
 * Date: 13. 7. 23
 * Time: 오후 1:59
 *
 */
public class RuleBaseEndingSplitter implements EndingSplitter {

    /**
     * 어미를 분리한다.
     *
     * 1. 어간부가 받침 ㄴ/ㄹ/ㅁ/ㅂ 으로 끝나고 (ㄴ/ㄹ/ㅁ/ㅂ + 어미부)가 어미 사전에 어미로 등록되어 있으면 (ㄴ/ㄹ/ㅁ/ㅂ + 어미부)를 어미로 추정한다.
     * 2. 규칙용언과 어간만 변하는 불규칙 용언의 어미부가 사전에 있으면 어미로 추정
     * 3. '여/거라/너라'의 불규칙 어절
     * 4. 어미 '아/어'가 탈락되는 어절
     * 5. '아/어'의 변이체 분리
     *
     * @param stem - 문자열(어간후보)
     * @param ending - 어미후보
     * @return
     */
    @Override
    public Variant splitEnding(String stem, String ending) {
        List<EndingSplitRule> rules = RuleFactory.buildEndingSplitRules(stem, ending);
        Variant result = null;

        for (EndingSplitRule each : rules) {
            if (each.canHandle()) {
                result = each.split();
                if (result != null && !result.isEmpty()) {
                    break;
                }
            }
        }

        if (result == null || result.isEmpty()) {
            if (ending.length() > 0 && EomiDic.exists(ending)) {
                result = Variant.createEnding(stem, ending);
            }
        }

        return result == null ? Variant.EMPTY : result;
    }



    /**
     * 선어말어미 분석<br/>
     * 결합관계:<br/>
     * 으 -> 시 -> 었 -> 었 -> 겠
     * 으 -> 시 -> 였 -> 었 -> 겠
     * 으 -> 시 -> 았 -> 었 -> 겠
     * 으 -> 시 -> ㅆ -> 었 -> 겠
     * 으 -> 셨 -> 었 -> 겠
     *
     * @param stem
     * @return
     */
    @Override
    public Variant splitPrefinal(String stem) {
        if (StringUtil.nvl(stem).length() == 0 || "있".equals(stem)) return Variant.EMPTY;

        EndingSplitRule rule = new PrefinalEndingRule(stem);
        if (rule.canHandle()) {
            return rule.split();
        }

        return Variant.EMPTY;
    }


}
