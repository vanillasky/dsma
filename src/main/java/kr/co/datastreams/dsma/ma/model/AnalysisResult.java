package kr.co.datastreams.dsma.ma.model;

import kr.co.datastreams.commons.util.StringUtil;
import kr.co.datastreams.dsma.ma.PosTag;
import kr.co.datastreams.dsma.ma.WordPattern;
import kr.co.datastreams.dsma.util.Hangul;

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
    public static final int SCORE_ANALYZED_DIC = 90;
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
    private String pos;  // stem의 중분류 수준의 품사정보, pos 만으로 품사정보가 부족할 때 내부적으로 사용
    private String simpleStemPos; // stem의 대분류 수준의 품사정보. 체언(N), 용언(V), 기타(Z:관형사, 부사, 감탄사)로 구분
    //private char dinf; // 어휘형태소 stem에 대한 품사정보. 분석 사전에 기술


    private String nounSuffix; // 체언 접미사
    private String verbSuffix; // 용언화 접미사

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

    public AnalysisResult(String text) {
        score =  SCORE_FAIL;
        source = text;
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

    public void setNounSuffix(String nounSuffix) {
        this.nounSuffix = nounSuffix;
    }

    public void setVerbSuffix(String verbSuffix) {
        this.verbSuffix = verbSuffix;
    }

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
          .append("pattern:").append(wordPattern).append(", ")
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

    public void setPos(String pos) {
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

    public void setSimpleStemPos(String tag) {
        this.simpleStemPos = tag;
    }

    public void addJosa(String morpheme) {
        josaList.add(morpheme);
    }

    //TODO 미완성
    public void resolvePattern() {

        //N(1)        /* 체언 : N/PN/NM/XN/CN/UN/AS/HJ/ET */
        //, NJ(2)     /* 체언 + 조사 */
        //, NSM(3)    /* 체언 + 용언화접미사 + 어미 */
        //, NSMJ(4)   /* 체언 + 용언화접미사 + '음/기' + 조사 */
        //, NSMXM(5)  /* 체언 + 용언화접미사 + '아/어' + 보조용언 + 어미 */
        //, NJCM(6)   /* 체언 + '에서/부터/에서부터' + '이' + 어미 */
        //
        //, VM(11)    /* 용언 + 어미 */
        //, VMJ(12)   /* 용언 + '음/기' + 조사 */
        //, VMCM(13)  /* 용언 + '음/기' + '이' + 어미 */
        //, VMXM(14)  /* 용언 + '아/어' + 보조용언 + 어미 */
        //, VMXMJ(15) /* 용언 + '아/어' + 보조용언 + '음/기' + 조사 */
        //, AID(21)   /* 단일어 : 부사, 관형사, 감탄사 */
        //, ADVJ(22)  /* 부사 + 조사 : '빨리도' */
        //
        //, NVM(31)   /* 체언 + 동사 + 어미 */
        //, ZZZ(35)   /* 문장부호, KS 완성형 기호열, 단독조사/어미 */

        if (StringUtil.nvl(stem).length() > 0) {
            long tagNum = PosTag.getTagNum(pos);

            if (PosTag.isKindOf(tagNum, PosTag.N)) {
                wordPattern = WordPattern.N;

                if (StringUtil.nvl(josa).length() > 0) {
                    wordPattern = WordPattern.NJ;
                }

                if (StringUtil.nvl(verbSuffix).length() > 0) {
                    wordPattern = WordPattern.NSM; // 체언 + 용언화접미사 + 어미
                }

            }
            else if (PosTag.isKindOf(tagNum, PosTag.V)) {
                wordPattern = WordPattern.VM;
            }
            else {
                wordPattern = WordPattern.AID;
            }
        }

    }

    public String asResult() {
        System.out.println(wordPattern);
        StringBuilder sb = new StringBuilder(source).append("/");
        sb.append("<").append(stem).append(", ").append(pos).append(">");

        if (wordPattern == WordPattern.VM) {
            appendEndingPart(sb);
        }
        else if (wordPattern == WordPattern.NJ) {
            appendJosaPart(sb);
        }
        else if (wordPattern == WordPattern.NSM) {
           appendMorphemeTag(sb, verbSuffix, verbSuffix.equals("이") ? PosTag.CP : PosTag.SV);
           appendEndingPart(sb);
        }

        return sb.toString();
    }

    private void appendJosaPart(StringBuilder sb) {
        if (josa != null) {
            appendMorphemeTag(sb, josa, PosTag.JO);
        }
        if (josaList != null) {
            for (String each : josaList) {
                appendMorphemeTag(sb, each, PosTag.JO);
            }
        }
    }

    private void appendEndingPart(StringBuilder sb) {
        if (ending != null) {
            if (prefinalEnding != null) {
                appendMorphemeTag(sb, prefinalEnding, PosTag.EP);
            }
            appendMorphemeTag(sb, ending, PosTag.EM);
        }
    }

    public void appendMorphemeTag(StringBuilder sb, String morpheme, long tagNum) {
        sb.append(" + ").append("<").append(morpheme).append(", ").append(PosTag.getTag(tagNum)).append(">");
    }

    public String getSource() {
        return source;
    }
}
