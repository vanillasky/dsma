package kr.co.datastreams.dsma.ma.internal;

import kr.co.datastreams.dsma.dic.Dictionary;
import kr.co.datastreams.dsma.dic.EomiDic;
import kr.co.datastreams.dsma.dic.JosaDic;
import kr.co.datastreams.dsma.ma.WordPattern;
import kr.co.datastreams.dsma.ma.model.AnalysisResult;
import kr.co.datastreams.dsma.ma.model.Word;
import kr.co.datastreams.dsma.ma.model.WordEntry;

/**
 *
 * User: shkim
 * Date: 13. 8. 20
 * Time: 오후 3:18
 *
 */
public class HeuristicAnalyzer {

    //두 음절 이상의 단어는 처음 세,네, 두음절 순으로 용언을, 나머지를 어미로 가정
    public AnalysisResult analyzeVerb(Word word) {
        int[] tailLocations = {3,4,2};
        AnalysisResult candidate = null;

        if (word.length() > 3) {
            for (int i=0; i < tailLocations.length; i++) {
                candidate = confirmVerb(word, tailLocations[i]);
                if (candidate != null) {
                    break;
                }
            }
        }

        return candidate;
    }

    private AnalysisResult confirmVerb(Word word, int start) {
        if (word.getString().length() <= start) {
            return null;
        }

        AnalysisResult candidate = null;
        String stemPart = word.substring(0, start);
        String endingPart = word.substring(start);

        WordEntry entry = Dictionary.getVerb(stemPart);
        if (entry != null && EomiDic.exists(endingPart)) {
            candidate = AnalysisResult.verb(entry.tag(), word.getString(), stemPart, endingPart, WordPattern.VM);
        }

        return candidate;
    }

    //두 음절 이상의 단어는 처음 두,세,네, 두음절 순으로 체언을, 나머지를 조사로 가정
    public AnalysisResult analyzeNoun(Word word) {
        int[] tailLocations = {2,3,4};
        AnalysisResult candidate = null;

        if (word.length() > 2) {
            for (int i=0; i < tailLocations.length; i++) {
                candidate = confirmNoun(word, tailLocations[i]);
                if (candidate != null) {
                    break;
                }
            }
        }

        return candidate;
    }

    private AnalysisResult confirmNoun(Word word, int start) {
        if (word.getString().length() <= start) {
            return null;
        }

        AnalysisResult candidate = null;
        String stemPart = word.substring(0, start);
        String josaPart = word.substring(start);

        WordEntry entry = Dictionary.getNoun(stemPart);
        if (entry != null && JosaDic.exists(josaPart)) {
            candidate = AnalysisResult.noun(entry.tag(), word.getString(), stemPart, josaPart, WordPattern.NJ);
        }

        return candidate;
    }

    public AnalysisResult analyze(Word word) {
        AnalysisResult result = analyzeVerb(word);
        if (result != null) {
            return result;
        }

        result = analyzeNoun(word);
        return result;
    }
}
