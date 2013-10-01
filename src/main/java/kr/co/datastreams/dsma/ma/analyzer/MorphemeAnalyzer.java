package kr.co.datastreams.dsma.ma.analyzer;

import kr.co.datastreams.commons.util.StringUtil;
import kr.co.datastreams.dsma.annotation.ThreadSafe;
import kr.co.datastreams.dsma.dic.AnalyzedDic;
import kr.co.datastreams.dsma.dic.Dictionary;
import kr.co.datastreams.dsma.dic.JosaDic;
import kr.co.datastreams.dsma.dic.SyllableDic;
import kr.co.datastreams.dsma.ma.PosTag;
import kr.co.datastreams.dsma.ma.model.*;
import kr.co.datastreams.dsma.ma.tokenizer.Tokenizer;
import kr.co.datastreams.dsma.util.Hangul;
import kr.co.datastreams.dsma.util.WordCounter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 형태소 분석기
 *
 * User: shkim
 * Date: 13. 9. 16
 * Time: 오후 3:53
 *
 */
@ThreadSafe
public class MorphemeAnalyzer implements IMorphemeAnalyzer {

    private final static Logger logger = LoggerFactory.getLogger(MorphemeAnalyzer.class);

    protected final Tokenizer tokenizer;
    protected final WordCounter wordCounter;
    private  final  HeuristicSearcher heuristicSearcher = new HeuristicSearcher();

    public MorphemeAnalyzer(Tokenizer tokenizer) {
        this(tokenizer, null);

    }

    public MorphemeAnalyzer(Tokenizer tokenizer, WordCounter wordCounter) {
        this.tokenizer = tokenizer;
        this.wordCounter = wordCounter;
    }

    /**
     * 문장단위 형태소 분석.
     * Tokenizer를 이용하여 인자로 받은 PlainSentence의 문자열을 어절단위(Token)로 분리한 다음에
     * 어절별로 형태소를 분석한다. 어절단위 분석결과인 Eojeol은 분석된 후보 개수만큼의 MorphemeList갖는다.
     * e.g.)
     * input = "감기는" --> Eojeol("감기는") --> List[0] = MorphemeList -> "감기(VV)" + "는(E)"
     *                                    --> List[1] = MorphemeList -> "감기(NNG)" + "는(J)"
     *
     * @param inputSentence - 입력 문장
     * @return 어절단위의 분석결과를 가지고 있는 Sentence 객체
     */
    @Override
    public Sentence analyze(PlainSentence inputSentence) {
        Sentence sentence = new Sentence(inputSentence);
        List<Token> tokens = tokenizer.tokenize(inputSentence.getSource());
        Token token;
        int i=0;
        for (Iterator<Token> it=tokens.iterator(); it.hasNext();) {
            token = it.next();
            if (token.isCharTypeOf(CharType.SPACE)) {
                continue;
            //TODO how to handle symbols, numbers, etc...?
            } else if (token.isCharTypeOf(CharType.SYMBOL)) {
                continue;
            }

            Eojeol eojeol = analyzeEojeol(token);
            sentence.add(eojeol);
        }
        return sentence;
    }

    /**
     * 어절단위 형태소 분석
     *
     * @param token - 분석대상 어절 토큰
     * @return
     */
    public Eojeol analyzeEojeol(Token token) {
        countWord(token.getString());
        return analyzeToken(token);
    }

    /**
     * 어절단위 형태소 분석
     *
     * @param token - 분석대상 어절 토큰
     * @return
     */
    protected Eojeol analyzeToken(Token token) {
        Eojeol result;

        // 기분석 사전 탐색해서 있으면 인덱스만 바꿔서 반환
        result = searchAnalyzedDic(token);
        if (result != null) {
            return result;
        }

//        // 경험적 방법으로 단어를 잘라서 찾아본다.
//        result = heuristicSearch(token);
//        if (result != null) {
//            return result;
//        }

        result = doAnalyze(token);
        if (result == null) {
            result = Eojeol.createFailure(token);
        }

        return result;
    }

    private Eojeol doAnalyze(Token token) {
        boolean josaFlag = true;
        boolean eomiFlag = true;
        Eojeol result = null;


        String inputString = token.getString();
        for (int i=inputString.length()-1; i > 0; i--) {
            String head = inputString.substring(0, i);
            String tail = inputString.substring(i);
            char firstCharOfTail = tail.charAt(0);
            List<MorphemeList> candidates = new ArrayList<MorphemeList>();

            // 단어의 마지막 음절이 조사의 첫음절로 사용되는 경우
            if (josaFlag && SyllableDic.isFirstJosaSyllable(firstCharOfTail)) {
                analyzeNoun(candidates, head, tail);
            }

//            if (eomiFlag) {
//                verbAnalyzer.analyze(candidates, word, i);
//            }

            // 조사의 두 번째 이상의 음절로 사용될 수 있는지 확인
            if (!SyllableDic.isSecondJosaSyllable(firstCharOfTail)) {
                josaFlag = false;
            }

            // 어미의 두 번째 이상의 음절로 사용될 수 있는지 확인
            if (!SyllableDic.isSecondEndingSyllable(firstCharOfTail)) {
                eomiFlag = false;
            }

            if (josaFlag == false && eomiFlag == false) {
                break;
            }
        }

        return result;
    }

    private void analyzeNoun(List<MorphemeList> candidates, String head, String tail) {
        if (StringUtil.nvl(head).length() == 0 || StringUtil.nvl(tail).length() == 0) return;

        if (tail.startsWith("로")) {
            if (head.endsWith("으")) {
                Hangul h = Hangul.split(head.charAt(head.length()-2));
                if (h.hasJong()) {
                    WordEntry josa = JosaDic.search("으" + tail);
                    System.out.println("자음으로 끝나는 단어 + 로 -> 조음소 '으' 처리: "+ josa);
                }
            } else {
                Hangul h = Hangul.split(head.charAt(head.length()-1));
                if (!h.hasJong() || h.jong == 'ㄹ') {

                }
            }
        }

        WordEntry nounWord = Dictionary.getNoun(head);
        if (nounWord == null) {
            return;
        }

        WordEntry josa = JosaDic.search(tail);
        Hangul chr = Hangul.split(head.charAt(head.length()-1));
        if (josa == null || !chr.isAppendableJosa(tail.substring(0, 1))) {
            return;
        }

        Morpheme h = new Morpheme(head, PosTag.decodeNoun(nounWord.tag()));
        Morpheme t = new Morpheme(tail, josa.tag());

        System.out.println(h);
        System.out.println(t);
    }

    private boolean isNoun(String word) {
        return Dictionary.getNoun(word) == null ? false : true;
    }

    private Eojeol searchAnalyzedDic(Token token) {
        Eojeol analyzedEojeol = AnalyzedDic.find(token.getString());
        if (analyzedEojeol != null) {
            if (logger.isDebugEnabled()) logger.debug("Found word in analyzed dictionary: {}", token.getString());
            analyzedEojeol = analyzedEojeol.copy(token.getIndex());
            analyzedEojeol.setScore(Score.AnalyzedDic);
            return analyzedEojeol;
        }
        return null;
    }


    private Eojeol heuristicSearch(Token token) {
        return heuristicSearcher.search(token);
    }

    void countWord(String word) {
        if (wordCounter != null) {
            wordCounter.count(word);
        }
    }
}
