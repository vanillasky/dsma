package kr.co.datastreams.dsma.ma.rule;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 7. 24
 * Time: 오후 5:21
 * To change this template use File | Settings | File Templates.
 */
public class RuleFactory {


    public static List<EndingCombineRule> buildEndingSplitRules(String stemCandidate, String endingCandidate) {
        List<EndingCombineRule> rules = new ArrayList<EndingCombineRule>();
        rules.add(new EndingStartsWithNLMB(stemCandidate, endingCandidate));
        rules.add(new EndingVariantHayoeGeoraNeora(stemCandidate, endingCandidate));
        rules.add(new ApocopeAEoRule(stemCandidate, endingCandidate));
        rules.add(new EndingSplitRuleVariantAEoi(stemCandidate, endingCandidate));
        rules.add(new EndingCombineWithEoi(stemCandidate, endingCandidate));
        rules.add(new EndingEndsWithHi(stemCandidate, endingCandidate));

        return rules;
    }
}
