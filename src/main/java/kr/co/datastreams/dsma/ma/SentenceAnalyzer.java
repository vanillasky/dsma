package kr.co.datastreams.dsma.ma;

import kr.co.datastreams.dsma.ma.internal.DefaultWordAnalyzer;
import kr.co.datastreams.dsma.ma.internal.WordAnalyzer;
import kr.co.datastreams.dsma.ma.model.*;
import kr.co.datastreams.dsma.ma.tokenizer.CharTypeTokenizer;

/**
 *
 * User: shkim
 * Date: 13. 7. 23
 * Time: 오전 10:50
 *
 */
public class SentenceAnalyzer {

    private final CharTypeTokenizer tokenizer = new CharTypeTokenizer();
    private final WordAnalyzer wordAnalyzer = new DefaultWordAnalyzer();

    //public Sentence analyze(String inputString) {
        //System.out.println(Thread.currentThread().getName());
//        return Sentence.create(0, "XL:" + inputString);
//        if (StringUtil.nvl(inputString).trim().length() == 0) {
//            return Sentence.emptySentence();
//        }
//
//        Sentence sentence = Sentence.create(0, inputString);
//
//        preProcess(sentence);
//        guessTail(sentence);
//        confirmHead(sentence);
//        postProcess(sentence);
//
//        return sentence;
    //}

    public Sentence analyze(PlainSentence inputSentence) {
        System.out.println(Thread.currentThread().getName() + ", " + inputSentence.getSource());
        return Sentence.create(inputSentence);

//        if (StringUtil.nvl(inputString).trim().length() == 0) {
//            return Sentence.emptySentence();
//        }
//
//        Sentence sentence = Sentence.create(0, inputString);
//
//        preProcess(sentence);
//        guessTail(sentence);
//        confirmHead(sentence);
//        postProcess(sentence);
//
//        return sentence;
    }


    protected void preProcess(PlainSentence sentence) {
       //List<Eojeol> eojeols = tokenizer.tokenize(sentence.getSource());
//        sentence.setWords(eojeols);
    }

    /**
     * 조사/어미 분석<br/>
     *
     * 입력 문장의 각 단어를 가지고 조사/어미를 분석한 후에 결과를 Word 객체에 저장한다.
     *
     * @param sentence 입력 문장
     */
    protected void guessTail(Sentence sentence) {
//        for (Iterator<Eojeol> it=sentence.getWords().iterator(); it.hasNext();) {
//            Eojeol word = it.next();
//
//            if (word.is(CharType.HANGUL)) {
//                List<AnalysisResult> candidates = wordAnalyzer.analyzeWord(word);
//               // word.addResults(candidates);
//            } else {
//                //word.addResult(AnalysisResult.empty(word.getString()));
//            }
//        }
    }


    protected void confirmHead(Sentence sentence) {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    protected void postProcess(Sentence sentence) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

}
