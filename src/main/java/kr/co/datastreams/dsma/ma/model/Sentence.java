package kr.co.datastreams.dsma.ma.model;

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
public class Sentence {

    public static final Sentence EMPTY = new Sentence();

    private final String source;
    private Integer type; // 평서문/의문문/명령문 등 문장의 유형
    private Integer wordCount; // 문장을 구성하는 어절의 개수. 마침표 등 문장부호는 독립어절로 간주.
    private List<Word> words;

    public static Sentence emptySentence() {
        return EMPTY;
    }

    public static Sentence create(String inputString) {
        return new Sentence(inputString);
    }

    private Sentence(String inputString) {
        source = inputString;
    }

    private Sentence() {
        source = null;
    }

    public String getSource() {
        return source;
    }

    public Integer getType() {
        return type;
    }

    public Integer getWordCount() {
        return wordCount;
    }

    public synchronized List<Word> getWords() {
        return words;
    }

    public boolean isEmpty() {
        return source == null;
    }

    public synchronized void setWords(List<Word> words) {
        this.words = words;
    }
}
