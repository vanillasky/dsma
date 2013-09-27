package kr.co.datastreams.dsma.ma.analyzer;

import kr.co.datastreams.dsma.ma.model.PlainSentence;
import kr.co.datastreams.dsma.ma.model.Sentence;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 9. 26
 * Time: 오후 3:44
 * To change this template use File | Settings | File Templates.
 */
public interface IMorphemeAnalyzer {

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
    public Sentence analyze(PlainSentence inputSentence);
}
