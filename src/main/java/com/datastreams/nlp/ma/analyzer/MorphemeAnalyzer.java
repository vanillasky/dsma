package com.datastreams.nlp.ma.analyzer;


import com.datastreams.nlp.common.annotation.ThreadSafe;
import com.datastreams.nlp.ma.constants.Score;
import com.datastreams.nlp.ma.model.Eojeol;
import com.datastreams.nlp.ma.model.PlainSentence;
import com.datastreams.nlp.ma.model.Sentence;
import com.datastreams.nlp.ma.model.Token;
import com.datastreams.nlp.ma.strategy.NullWordCountStrategy;
import com.datastreams.nlp.ma.strategy.WordCountStrategy;
import com.datastreams.nlp.ma.tokenizer.CharTypeTokenizer;
import com.datastreams.nlp.ma.tokenizer.Tokenizer;

import com.datastreams.nlp.ma.dic.AnalyzedDic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;

import static com.datastreams.nlp.ma.constants.CharType.*;

/**
 * 문장 단위 형태소 분석기
 *
 * Breaks up the argument that can be PlainSentence or String object into tokens.
 * Then try to analyze morphemes for each tokens.
 *
 * PlainSentence 또는 문자열(String)을 입력으로 받아서 어절단위로(Token) 분리한다.
 * 특수 문자, 숫자 등 형태소 분석 대상이 아닌 문자들은 토큰분리 과정에서 공백으로 대치한다.
 * 그리고 나서 분리된 어절별로 형태소 분석을 시도한다. 어절이 공백이면 무시한다.
 * 어절이 기분석 사전에 수록되어 있으면 기분석 어절을 복사하는 것으로 어절 분석을 종료한다.
 * 기분석 사전에 수록되지 않은 어절은 어절 단위 분석기인 TokenAnalyzer를 이용하여 형태소를 분석한다.
 * 분석이 완료된 어절은(Eojeol) 문장단위 분석 결과인 Sentence 에 추가된다.
 *
 * MorphemeAnalyzer를 생성할 때 WordCountStrategy 객체를 인자로 넘겨주면 각 입력어절(Token)의 출현빈도를 계산할 수 있다.
 *
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
    protected final WordCountStrategy wordcounter;
    protected final TokenAnalyzer tokenAnalyzer;

    public MorphemeAnalyzer() {
        this(new CharTypeTokenizer());
    }

    public MorphemeAnalyzer(Tokenizer tokenizer) {
        this(tokenizer, new RuleBaseTokenAnalyzer(), new NullWordCountStrategy());
    }

    public MorphemeAnalyzer(Tokenizer tokenizer, TokenAnalyzer tokenAnalyzer, WordCountStrategy wordCountStrategy) {
        this.tokenizer = tokenizer;
        this.wordcounter = wordCountStrategy;
        this.tokenAnalyzer = tokenAnalyzer;
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
        Eojeol eojeol;

        for (Iterator<Token> it=tokens.iterator(); it.hasNext();) {
            token = it.next();
            if (token.isCharTypeOf(SPACE)) {
                continue;
            }
            else {
                wordcounter.count(token.getString());
                eojeol = analyzeEojeol(token);
            }

            sentence.add(eojeol);
        }
        return sentence;
    }

    @Override
    public Sentence analyze(String text) {
        return analyze(new PlainSentence(0, text));
    }

    /**
     * 어절단위 형태소 분석
     *
     * @param token - 분석대상 어절 토큰
     * @return
     */
    public Eojeol analyzeEojeol(Token token) {

        Eojeol result = searchAnalyzedDic(token);

        if (result.isEmpty()) {
            return tokenAnalyzer.execute(token);
        }

        return result;
    }

    /**
     * 기분석 사전 탐색
     * @param token
     * @return
     */
    protected Eojeol searchAnalyzedDic(Token token) {
        Eojeol analyzedEojeol = AnalyzedDic.search(token.getString());

        if (analyzedEojeol != null) {
            if (logger.isDebugEnabled()) logger.debug("Found word in analyzed dictionary: {}", token.getString());
            analyzedEojeol = analyzedEojeol.copy(token.getIndex());
            analyzedEojeol.setScore(Score.AnalyzedDic);
            return analyzedEojeol;
        }
        return Eojeol.EMPTY;
    }
}
