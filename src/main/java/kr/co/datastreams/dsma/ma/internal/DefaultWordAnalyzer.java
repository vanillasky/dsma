package kr.co.datastreams.dsma.ma.internal;

import kr.co.datastreams.dsma.dic.AnalyzedDic;
import kr.co.datastreams.dsma.dic.SyllableDic;
import kr.co.datastreams.dsma.ma.model.AnalysisResult;
import kr.co.datastreams.dsma.ma.model.Eojeol;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * User: shkim
 * Date: 13. 8. 1
 * Time: 오후 2:53
 *
 */
public class DefaultWordAnalyzer implements WordAnalyzer {

    private VerbAnalyzer verbAnalyzer = new RuleBaseVerbAnalyzer();
    //private HeuristicAnalyzer heuristicAnalyzer = new HeuristicAnalyzer();

    /**
     * 단어 수준의 형태소 분석<br/>
     *
     * @param word - the word to analyzer
     * @return
     */
    @Override
    public List<AnalysisResult> analyzeWord(Eojeol word) {
        List<AnalysisResult> candidates = new ArrayList<AnalysisResult>();
        String inputString = word.getString();

        if (searchAnalyzedDictionary(candidates, word)) {
            return candidates;
        }

        if (heuristicAnalyze(candidates, word)) {
            return candidates;
        }

        return analyze(word);
    }

    private List<AnalysisResult> analyze(Eojeol word) {
        boolean josaFlag = true;
        boolean eomiFlag = true;

        //verbAnalyzer.analyze(candidates, word, word.getString().length());
        String inputString = word.getString();
        for (int i=inputString.length()-1; i > 0; i--) {
            String stemPart = inputString.substring(0, i);
            String endingPart = inputString.substring(i);
            char lastChar = endingPart.charAt(0);

            // 단어의 마지막 음절이 조사의 첫음절로 사용되는 경우
            if (josaFlag && SyllableDic.isFirstJosaSyllable(lastChar)) {
//                analyzeJosa(candidates, stem, ending);
            }

//            if (eomiFlag) {
//                verbAnalyzer.analyze(candidates, word, i);
//            }

            // 조사의 두 번째 이상의 음절로 사용될 수 있는지 확인
            if (!SyllableDic.isSecondJosaSyllable(lastChar)) {
                josaFlag = false;
            }

            // 어미의 두 번째 이상의 음절로 사용될 수 있는지 확인
            if (!SyllableDic.isSecondEndingSyllable(lastChar)) {
                eomiFlag = false;
            }

            if (josaFlag == false && eomiFlag == false) {
                break;
            }
        }
        return null;  //To change body of created methods use File | Settings | File Templates.
    }


    private boolean heuristicAnalyze(List<AnalysisResult> candidates, Eojeol word) {
        // Heuristic analyze
        //AnalysisResult result = heuristicAnalyzer.analyze(word);
        AnalysisResult result = null;
        if (result != null) {
            candidates.add(result);
            return true;
        }
        return false;
    }

    // 기분석 사전 조회
    private boolean searchAnalyzedDictionary(List<AnalysisResult> candidates, Eojeol word) {
        Eojeol analyzedWord = AnalyzedDic.find(word.getString().trim());
        if (analyzedWord != null) {
            //candidates.addAll(analyzedWord.getAnalysisResults());
            return true;
        }
        return false;
    }
}
