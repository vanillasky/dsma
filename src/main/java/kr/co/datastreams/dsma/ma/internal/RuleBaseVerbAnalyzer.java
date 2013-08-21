package kr.co.datastreams.dsma.ma.internal;

import kr.co.datastreams.commons.util.StringUtil;
import kr.co.datastreams.dsma.dic.Dictionary;
import kr.co.datastreams.dsma.dic.EomiDic;
import kr.co.datastreams.dsma.ma.IrregularVerb;
import kr.co.datastreams.dsma.ma.MorphemeBuilder;
import kr.co.datastreams.dsma.ma.PosTag;
import kr.co.datastreams.dsma.ma.WordPattern;
import kr.co.datastreams.dsma.ma.model.AnalysisResult;
import kr.co.datastreams.dsma.ma.model.Variant;
import kr.co.datastreams.dsma.ma.model.Word;
import kr.co.datastreams.dsma.ma.model.WordEntry;
import kr.co.datastreams.dsma.ma.rule.*;

import java.util.List;

/**
 *
 * User: shkim
 * Date: 13. 7. 23
 * Time: 오후 1:59
 *
 */
public class RuleBaseVerbAnalyzer implements VerbAnalyzer {

    @Override
    public void analyze(List<AnalysisResult> candidates, Word word, int location) {
        String stemPart = word.substring(0, location);
        String endingPart = word.substring(location);

        if (StringUtil.nvl(stemPart).trim().length() == 0) {
            return;
        }

        AnalysisResult candidate;

        Variant ending = splitEnding(stemPart, endingPart);
        if (ending.isEmpty()) return;

        Variant prefinal = splitPrefinal(ending.getStem());

        if (prefinal.isEmpty())
            candidate = createVerb(stemPart + endingPart, ending, WordPattern.VM);
        else
            candidate = createVerbWithPrefinal(stemPart + endingPart, prefinal, WordPattern.VM);


        WordEntry entry = Dictionary.getVerb(candidate.getStem());
        if (entry != null) {
            if (!"을".equals(endingPart) && !"은".equals(endingPart) && !"음".equals(endingPart)) {
                if (entry.getFeature(WordEntry.IDX_REGURA) != IrregularVerb.IRR_TYPE_LIUL
                    && entry.getFeature(WordEntry.IDX_REGURA) != IrregularVerb.IRR_TYPE_BIUP) {

                    try {
                        AnalysisResult output = candidate.clone();
                        MorphemeBuilder.buildPattern(WordPattern.VM, output, candidates);


                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        candidates.add(candidate);

    }


    private AnalysisResult createVerb(String source, Variant ending, WordPattern wordPattern) {
        return AnalysisResult.verb(PosTag.V, source, ending.getStem(), ending.getEnding(), wordPattern);
    }

    private AnalysisResult createVerbWithPrefinal(String source, Variant prefinal, WordPattern wordPattern) {
        return AnalysisResult.verbWithPrefinal(source, prefinal.getStem(), prefinal.getPrefinalEnding(), prefinal.getEnding(), wordPattern);
    }

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
    public Variant splitEnding(String stem, String ending) {
        List<EndingCombineRule> rules = RuleFactory.buildEndingSplitRules(stem, ending);
        Variant result = null;

        for (EndingCombineRule each : rules) {
            result = each.split();
            if (result != null && !result.isEmpty()) {
                break;
            }
        }

        if (result == null || result.isEmpty()) {
            if (ending.length() > 0 && EomiDic.exists(ending)) {
                result = Variant.createWithEnding(stem, ending);
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
    public Variant splitPrefinal(String stem) {
        if (StringUtil.nvl(stem).length() == 0 || "있".equals(stem)) return Variant.EMPTY;

        EndingCombineRule rule = new PrefinalEndingRule(stem);
        if (rule.canHandle()) {
            return rule.split();
        }

        return Variant.EMPTY;
    }



}
