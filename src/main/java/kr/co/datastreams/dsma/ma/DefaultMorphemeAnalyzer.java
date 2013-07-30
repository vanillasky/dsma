package kr.co.datastreams.dsma.ma;

import kr.co.datastreams.commons.util.StringUtil;
import kr.co.datastreams.dsma.dic.Dictionary;
import kr.co.datastreams.dsma.ma.api.EndingSplitter;
import kr.co.datastreams.dsma.ma.model.*;
import kr.co.datastreams.dsma.util.Hangul;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 *
 * User: shkim
 * Date: 13. 7. 23
 * Time: 오전 10:50
 *
 */
public class DefaultMorphemeAnalyzer extends AbstractMorphemeAnalyzer {

    private static final int POS_START = 1;
    private static final int POS_MID = 2;
    private static final int POS_END = 3;

    private final Tokenizer tokenizer = new Tokenizer();
    private final List<AnalysisResult> analysisResults = new ArrayList<AnalysisResult>();
    private final EndingSplitter endingSplitter = new RuleBaseEndingSplitter();

    @Override
    public List<AnalysisResult> analyze(String inputString) {
        if (StringUtil.nvl(inputString).length() == 0) {
            return Collections.EMPTY_LIST;
        }

        List<Token> tokens = tokenizeFrom(inputString, tokenizer);
        for (Iterator<Token> iter=tokens.iterator(); iter.hasNext();) {
            Token token = iter.next();
            if (token.is(CharType.HANGUL)) {
                AnalysisResult result = analyzeToken(token);
                analysisResults.add(result);
            } else {
                //TODO 한글이 아닌 경우 처리
            }
        }

        return analysisResults;
    }


    /**
     * 토큰에 대해 형태소 분석을 실시한다.
     *
     * @param token - the token to analyze.
     * @return List of AnalysisResult
     */
    private AnalysisResult analyzeToken(Token token) {
        List<AnalysisResult> candidates = analyzeWord(token.getString());
//        boolean hasOnlyVerbSyllable = Hangul.hasOnlyVerbSyllable(token.getString());


        return null;
    }

    private List<AnalysisResult> analyzeWord(String word) {
        List<AnalysisResult> candidates = new ArrayList<AnalysisResult>();

        boolean josaFlag = true;
        boolean eomiFlag = true;

        for (int i=word.length()-1; i > 0; i--) {
            String stem = word.substring(0, i);
            String ending = word.substring(i);
            char ch = ending.charAt(0);

            if (josaFlag && Hangul.isFirstJosaSyllable(ending.charAt(0))) { // 조사의 첫음절로 사용되는 경우
//                analyzeJosa(candidates, stem, ending);
            }

            if (eomiFlag) {
                analyzeWithEomi(candidates, stem, ending);
            }

            if (!Hangul.isSecondJosaSyllable(ch)) { // 조사의 두 번째 이상의 음절로 사용되는 경우
                josaFlag = false;
            }

            if (!Hangul.isSecondEndingSyllable(ch)) { // 어미의 두 번째 이상의 음절로 사용되는 경우
                eomiFlag = false;
            }

            if (josaFlag == false && eomiFlag == false) {
                break;
            }
        }

        return candidates;
    }


    private void analyzeWithEomi(List<AnalysisResult> candidates, String stem, String ending) {
        Variant morpheme = endingSplitter.splitEnding(stem, ending);
        if (morpheme.isEmpty()) return;

        Variant prefinal = endingSplitter.splitPrefinal(morpheme.getStem());
        AnalysisResult result = AnalysisResult.verbWithPrefinal(prefinal, morpheme.getEnding(), Constants.PTN_VM);

        WordEntry entry = Dictionary.getVerb(result.getStem());
//        if (entry != null && !"을".equals(ending) && !"은".equals(ending) && !"음".equals(ending)) {
//            if (entry.getFeature(WordEntry.IDX_REGURA) == )
//        }
    }
}
