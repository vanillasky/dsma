package kr.co.datastreams.dsma.ma.analyzer;

import kr.co.datastreams.dsma.annotation.ThreadSafe;
import kr.co.datastreams.dsma.dic.AnalyzedDic;
import kr.co.datastreams.dsma.ma.internal.HeuristicAnalyzer;
import kr.co.datastreams.dsma.ma.model.*;
import kr.co.datastreams.dsma.ma.tokenizer.Tokenizer;
import kr.co.datastreams.dsma.util.WordCounter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        Eojeol result = null;
        boolean josaFlag = true;
        boolean eomiFlag = true;

        // 기분석 사전 탐색해서 있으면 인덱스만 바꿔서 반환
        Eojeol analyzedEojeol = AnalyzedDic.find(token.getString());
        if (analyzedEojeol != null) {
            if (logger.isDebugEnabled()) logger.debug("Found word in analyzed dictionary: {}", token.getString());
            return analyzedEojeol.copy(token.getIndex());
        }

        result = heuristicSearch(token);

        String inputString = token.getString();



        return result;
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
