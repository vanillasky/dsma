package kr.co.datastreams.dsma.ma.model;

import kr.co.datastreams.dsma.ma.WordPattern;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * User: shkim
 * Date: 13. 7. 23
 * Time: 오후 1:30
 *
 */
public class AnalysisResult implements Serializable, Cloneable {
    public static final int SCORE_CORRECT = 100;
    public static final int SCORE_COMPOUNDS = 70;
    public static final int SCORE_ANALYSIS = 30;
    public static final int SCORE_CANDIDATE = 10;
    public static final int SCORE_FAIL = 0;

    private List<CompoundWordEntry> compound = new ArrayList<CompoundWordEntry>(); // 복합명사
    private String source; // 분석하기 전 문자열
    private int score; // score of this result
    private WordPattern wordPattern;  // 분석대상 어절의 유형
    private char wordType;    // 어절의 유형을 개념적인 기준의 의해 분류한 정보를 저장

    private String stem; // 입력어절의 어휘형태소, 형태소 분석 사전 수록 어휘를 기준으로 한다.
    private char pos; // stem의 대분류 수준의 품사정보. 체언(N), 용언(V), 기타(Z:관형사, 부사, 감탄사)로 구분
    private char pos2; // stem의 중분류 수준의 품사정보, pos 만으로 품사정보가 부족할 때 내부적으로 사용
    private char dinf; // 어휘형태소 stem에 대한 품사정보. 분석 사전에 기술


    private String nounSuffixIndex; // index of noun suffix, 체언 접미사에 대한 인덱스 값
    private String verbSuffixIndex; // index of verb suffix, 용언화 접미사에 대한 인덱스 값
    private String josa; // 조사 string, 조사가 분리되었을 경우 그 스트링을 저장, 조사가 분리되지 않으면 '\0'
    private List<String> josaList = new ArrayList<String>(); // unit-josa sequence, 복합조사인 경우 각 단위 조사들이 분리된 형태를 저장
    private String ending; // 어미 String, 어미가 분리되었을 경우 그 스트링을 저장,
    private List<String> endingList = new ArrayList<String>(); // unit-ending sequence, 복합어미인 경우 각 단위어미들이 분리된 형태를 저장
    private String prefinalEnding; // 선어말어미, 하위 4비트로 표현(0000xxxx)의 xxxx 부분을 순서대로 '시/었/었/겠'의 출현여부를 표시
    private String auxiliaryVerb; // xverb string, '먹어보다'와 같이 붙여쓴 보조용언의 분리되었을 때 그 스트링을 저장
    private char irregularVerbType; // 불규칙 용언의 유형에 관한 정보


    public AnalysisResult() {
        score =  SCORE_FAIL;
    }


    public String getStem() {
        return stem;
    }

    /**
     * 용언 정보를 만든다.
     *
     * @param stem - 어간
     * @param ending - 어미
     * @param pattern - 단어 유형
     *
     * @return 분석결과
     */
    public static AnalysisResult verb(String source, String stem, String ending, WordPattern pattern) {
        return new AnalysisResult(source, stem, ending, null, pattern, SCORE_ANALYSIS);
    }

    public static AnalysisResult empty(String word) {
        return new AnalysisResult(word, null, null, null, null, SCORE_FAIL);
    }

//    private AnalysisResult(String stem, String josa, String ending, int pattern) {
//        this(stem, josa, ending, pattern, SCORE_ANALYSIS);
//
//    }

    /**
     * 선어말 어미가 포함된 용언을 만든다
     *
     * @param stem - 어간
     * @param prefinal - 선어말 어미
     * @param ending - 어미
     * @param pattern - 단어 유형
     * @return
     */
    public static AnalysisResult verbWithPrefinal(String source, String stem, String prefinal, String ending, WordPattern pattern) {
        AnalysisResult result = verb(source, stem, ending, pattern);
        result.prefinalEnding = prefinal;
        return result;
    }


    private AnalysisResult(String source, String stem, String ending, String josa, WordPattern pattern, int score) {
        this.source=source;
        this.score = score;
        this.stem = stem;
        this.josa = josa;
        this.ending = ending;
        this.wordPattern = pattern;
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AnalysisResult {")
          .append("source:").append(source).append(", ")
          .append("stem:").append(stem).append(", ")
          .append("prefinal:").append(prefinalEnding).append(", ")
          .append("endiing:").append(ending).append(", ")
          .append("josa:").append(josa)
          .append("}");
        return sb.toString();
    }

    //TODO clone 메소드 완성
    public AnalysisResult clone() throws CloneNotSupportedException {
        AnalysisResult result = (AnalysisResult)super.clone();

        result.dinf = this.dinf;
        result.ending = this.ending;
        result.josa = this.josa;

        return result;
    }

    public String getEnding() {
        return ending;
    }

    public String getPrefinalEnding() {
        return prefinalEnding;
    }

    public int getScore() {
        return score;
    }

    public void setPos(char pos) {
        this.pos = pos;
    }

    public void setWordPattern(WordPattern wordPattern) {
        this.wordPattern = wordPattern;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setStem(String stem) {
        this.stem = stem;
    }

    public void setJosa(String josa) {
        this.josa = josa;
    }

    public void setEnding(String ending) {
        this.ending = ending;
    }

    public void setPrefinalEnding(String prefinalEnding) {
        this.prefinalEnding = prefinalEnding;
    }

    public void setAuxiliaryVerb(String auxiliaryVerb) {
        this.auxiliaryVerb = auxiliaryVerb;
    }

    public void setSource(String source) {
        this.source = source;
    }

}
