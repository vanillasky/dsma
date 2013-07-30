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


    public static List<EndingSplitRule> buildEndingSplitRules(String stemCandidate, String endingCandidate) {
        List<EndingSplitRule> rules = new ArrayList<EndingSplitRule>();
        rules.add(new StartsWithNLMBEomiRule(stemCandidate, endingCandidate));
        rules.add(new EndingSplitRuleVariantHayoeGeoraNeora(stemCandidate, endingCandidate));
        rules.add(new EndingSplitRuleApocope(stemCandidate, endingCandidate));
        rules.add(new EndingSplitRuleVariantAEoi(stemCandidate, endingCandidate));
        rules.add(new EndingSplitRuleVariant(stemCandidate, endingCandidate));
        rules.add(new EndingSplitRuleEndsWithHae(stemCandidate, endingCandidate));
        rules.add(new EndingSplitRuleEndsWithHi(stemCandidate, endingCandidate));

        return rules;
    }
}
