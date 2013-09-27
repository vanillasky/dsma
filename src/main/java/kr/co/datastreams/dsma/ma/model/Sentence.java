package kr.co.datastreams.dsma.ma.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 문장 구조체
 *
 * 입력 문장 및 문장을 구성하는 각 어절들에 대한 형태소 분석 결과를 저장한다.
 *
 * User: shkim
 * Date: 13. 7. 31
 * Time: 오전 10:44
 *
 */
public class Sentence implements Comparable {

    public static final Sentence EMPTY = null; //new Sentence(0, null);

    private final PlainSentence plainSentence;
    private final int index;
    private List<Eojeol> eojeols;

    public Sentence(PlainSentence plainSentence) {
        this.plainSentence = plainSentence;
        this.index = plainSentence.getIndex();
        this.eojeols = new ArrayList<Eojeol>();
    }

    public static Sentence emptySentence() {
        return EMPTY;
    }

    public static Sentence create(PlainSentence plainSentence) {
        return new Sentence(plainSentence);
    }

    public PlainSentence getPlainSentence() {
        return plainSentence;
    }

    public String getSourceString() {
        return plainSentence.getSource();
    }

    public synchronized List<Eojeol> getEojeols() {
        return eojeols;
    }

    public synchronized void setEojeols(List<Eojeol> words) {
        this.eojeols = words;
    }

    public int index() {
        return index;
    }


    @Override
    public int compareTo(Object other) {
        if (other instanceof Sentence) {
            return index - ((Sentence)other).index;
        }
        return 0;
    }

    public String toString() {
        return plainSentence.getSource();
    }

    public void add(Eojeol eojeol) {
        eojeols.add(eojeol);
    }
}
