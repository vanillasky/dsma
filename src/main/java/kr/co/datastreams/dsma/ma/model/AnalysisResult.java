package kr.co.datastreams.dsma.ma.model;

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
    private int wordPattern;  // 분석대상 어절의 유형
    private char wordType;    // 어절의 유형을 개념적인 기준의 의해 분류한 정보를 저장

    private String stem; // 입력어절의 어휘형태소, 형태소 분석 사전 수록 어휘를 기준으로 한다.
    private char pos; // stem의 대분류 수준의 품사정보. 체언(N), 용언(V), 기타(Z:관형사, 부사, 감탄사)로 구분
    private char pos2; // stem의 중분류 수준의 품사정보, pos 만으로 품사정보가 부족할 때 내부적으로 사용
    private char dinf; // 어휘형태소 stem에 대한 품사정보. 분석 사전에 기술

    private String nsfx; // index of noun suffix, 체언 접미사에 대한 인덱스 값
    private String vsfx; // index of verb suffix, 용언화 접미사에 대한 인덱스 값
    private String josa; // 조사 string, 조사가 분리되었을 경우 그 스트링을 저장, 조사가 분리되지 않으면 '\0'
    private List<String> josaList = new ArrayList<String>(); // unit-josa sequence, 복합조사인 경우 각 단위 조사들이 분리된 형태를 저장
    private String eomi; // 어미 String, 어미가 분리되었을 경우 그 스트링을 저장,
    private List<String> eomiList = new ArrayList<String>(); // unit-eomi sequence, 복합어미인 경우 각 단위어미들이 분리된 형태를 저장
    private String pomi; // 선어말어미, 하위 4비트로 표현(0000xxxx)의 xxxx 부분을 순서대로 '시/었/었/겠'의 출현여부를 표시
    private String xverb; // xverb string, '먹어보다'와 같이 붙여쓴 보조용언의 분리되었을 때 그 스트링을 저장
    private char irregularVerbType; // 불규칙 용언의 유형에 관한 정보

    public AnalysisResult() {
        score =  SCORE_FAIL;
    }

    public static AnalysisResult verb(String stem, String ending, int pattern) {
        return new AnalysisResult(stem, null, ending, pattern);
    }

    private AnalysisResult(String stem, String josa, String ending, int pattern) {
        this.score = SCORE_ANALYSIS;
        this.stem = stem;
        this.josa = josa;
        this.eomi = ending;
        this.wordPattern = pattern;
    }

    public static AnalysisResult verbWithPrefinal(String stem, String prefinal, String ending, int pattern) {
        AnalysisResult result = verb(stem, ending, pattern);
        result.pomi = prefinal;
        return result;
    }

    public static AnalysisResult verbWithPrefinal(Variant prefinal, String ending, int pattern) {
        return verbWithPrefinal(prefinal.getStem(), prefinal.getPrefinalEnding(), ending, pattern);
    }

    public String getStem() {
        return stem;
    }
}
