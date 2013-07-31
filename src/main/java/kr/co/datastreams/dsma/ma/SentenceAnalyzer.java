package kr.co.datastreams.dsma.ma;

import kr.co.datastreams.dsma.dic.Dictionary;
import kr.co.datastreams.dsma.ma.api.VerbAnalyzer;
import kr.co.datastreams.dsma.ma.model.*;
import kr.co.datastreams.dsma.util.Hangul;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * User: shkim
 * Date: 13. 7. 23
 * Time: 오전 10:50
 *
 */
public class SentenceAnalyzer extends AbstractMorphemeAnalyzer {

    private static final int POS_START = 1;
    private static final int POS_MID = 2;
    private static final int POS_END = 3;

    private final Tokenizer tokenizer = new Tokenizer();
    private final List<AnalysisResult> analysisResults = new ArrayList<AnalysisResult>();
    private final VerbAnalyzer verbAnalyzer = new RuleBaseVerbAnalyzer();


    @Override
    protected void preProcess(Sentence sentence) {
        List<Word> words = tokenizer.tokenize(sentence.getSource());
        sentence.setWords(words);
    }

    @Override
    protected void guessTail(Sentence sentence) {
        for (Iterator<Word> iter=sentence.getWords().iterator(); iter.hasNext();) {
            Word word = iter.next();
            if (word.is(CharType.HANGUL)) {
                List<AnalysisResult> candidates = analyzeWord(word);
                word.addResults(candidates);
            } else {
                word.addResult(AnalysisResult.empty(word.getString()));
            }
        }
    }

    @Override
    protected void confirmHead(Sentence sentence) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected void postProcess(Sentence sentence) {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    /**
     * 단어(어절)를 분석한다.
     *
     * @param word - the word to analyze.
     * @return - List of AnalysisResult
     */
    private List<AnalysisResult> analyzeWord(Word word) {
        List<AnalysisResult> candidates = new ArrayList<AnalysisResult>();
        String inputString = word.getString();

        boolean josaFlag = true;
        boolean eomiFlag = true;

        verbAnalyzer.analyze(candidates, inputString, "");

        for (int i=inputString.length()-1; i > 0; i--) {
            String stem = inputString.substring(0, i);
            String ending = inputString.substring(i);
            char ch = ending.charAt(0);

            // 단어의 마지막 음절이 조사의 첫음절로 사용되는 경우
            if (josaFlag && Hangul.isFirstJosaSyllable(ending.charAt(0))) {
//                analyzeJosa(candidates, stem, ending);
            }

            if (eomiFlag) {
                verbAnalyzer.analyze(candidates, stem, ending);
            }

            // 조사의 두 번째 이상의 음절로 사용될 수 있는지 확인
            if (!Hangul.isSecondJosaSyllable(ch)) {
                josaFlag = false;
            }

            // 어미의 두 번째 이상의 음절로 사용될 수 있는지 확인
            if (!Hangul.isSecondEndingSyllable(ch)) {
                eomiFlag = false;
            }

            if (josaFlag == false && eomiFlag == false) {
                break;
            }
        }

        return candidates;
    }


//    private void analyzeWithEomi(List<AnalysisResult> candidates, String stem, String ending) {
//        Variant morpheme = endingSplitter.splitEnding(stem, ending);
//        if (morpheme.isEmpty()) return;
//
//        Variant prefinal = endingSplitter.splitPrefinal(morpheme.getStem());
//        AnalysisResult result = AnalysisResult.verbWithPrefinal(prefinal, morpheme.getEnding(), Constants.PTN_VM);
//
//        WordEntry entry = Dictionary.getVerb(result.getStem());
////        if (entry != null && !"을".equals(ending) && !"은".equals(ending) && !"음".equals(ending)) {
////            if (entry.getFeature(WordEntry.IDX_REGURA) == )
////        }
//    }
}
