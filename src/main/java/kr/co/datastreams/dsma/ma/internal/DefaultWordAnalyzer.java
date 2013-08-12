package kr.co.datastreams.dsma.ma.internal;

import kr.co.datastreams.dsma.dic.SyllableDic;
import kr.co.datastreams.dsma.ma.model.AnalysisResult;
import kr.co.datastreams.dsma.ma.model.Word;

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

    /**
     * 단어 수준의 형태소 분석<br/>
     *
     * @param word - the word to analyzer
     * @return
     */
    @Override
    public List<AnalysisResult> analyzeWord(Word word) {
        List<AnalysisResult> candidates = new ArrayList<AnalysisResult>();
        String inputString = word.getString();

        boolean josaFlag = true;
        boolean eomiFlag = true;

        verbAnalyzer.analyze(candidates, word, word.getString().length());

        for (int i=inputString.length()-1; i > 0; i--) {
            String stemPart = inputString.substring(0, i);
            String endingPart = inputString.substring(i);
            char lastChar = endingPart.charAt(0);

            // 단어의 마지막 음절이 조사의 첫음절로 사용되는 경우
            if (josaFlag && SyllableDic.isFirstJosaSyllable(lastChar)) {
//                analyzeJosa(candidates, stem, ending);
            }

            if (eomiFlag) {
                verbAnalyzer.analyze(candidates, word, i);
            }

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

        return candidates;
    }
}
