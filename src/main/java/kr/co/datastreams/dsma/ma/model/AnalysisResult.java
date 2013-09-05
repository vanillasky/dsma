package kr.co.datastreams.dsma.ma.model;

import kr.co.datastreams.commons.util.StringUtil;
import kr.co.datastreams.dsma.dic.AnalyzedDic;
import kr.co.datastreams.dsma.ma.PosTag;
import kr.co.datastreams.dsma.ma.WordPattern;
import kr.co.datastreams.dsma.util.Hangul;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

/**
 *
 * User: shkim
 * Date: 13. 7. 23
 * Time: 오후 1:30
 *
 */
public class AnalysisResult implements Serializable, Cloneable {
    public static final int SCORE_CORRECT       = 0;
    public static final int SCORE_ANALYZED_DIC  = 20;
    public static final int SCORE_COMPOUNDS     = 40;
    public static final int SCORE_ANALYSIS      = 60;
    public static final int SCORE_CANDIDATE     = 80;
    public static final int SCORE_FAIL          = 100;

    private List<CompoundWordEntry> compound = new ArrayList<CompoundWordEntry>(); // 복합명사
    private String source; // 분석하기 전 문자열
    private int score; // score of this result
    private WordPattern wordPattern;  // 분석대상 어절의 유형
    private char wordType;    // 어절의 유형을 개념적인 기준의 의해 분류한 정보를 저장

    private String stem; // 입력어절의 어휘형태소, 형태소 분석 사전 수록 어휘를 기준으로 한다.
    private String pos;  // stem의 중분류 수준의 품사정보
    private String simpleStemPos; // stem의 대분류 수준의 품사정보. 체언(N), 용언(V), 기타(Z:관형사, 부사, 감탄사)로 구분
    //private char dinf; // 어휘형태소 stem에 대한 품사정보. 분석 사전에 기술

    private String nounSuffix; // 체언 접미사
    private String verbSuffix; // 용언화 접미사

    private String josa; // 조사 string, 조사가 분리되었을 경우 그 스트링을 저장
    private List<String> josaList = new ArrayList<String>(); // unit-josa sequence, 복합조사인 경우 각 단위 조사들이 분리된 형태를 저장
    private String ending; // 어미 String, 어미가 분리되었을 경우 그 스트링을 저장,
    private List<String> endingList = new ArrayList<String>(); // unit-ending sequence, 복합어미인 경우 각 단위어미들이 분리된 형태를 저장
    private String prefinalEnding; // 선어말어미
    private String auxiliaryVerb; // xverb string, '먹어보다'와 같이 붙여쓴 보조용언의 분리되었을 때 그 스트링을 저장
    private char irregularVerbType = '\0'; // 불규칙 용언의 유형에 관한 정보


    public AnalysisResult() {
        score =  SCORE_FAIL;
    }

    public AnalysisResult(String text) {
        score =  SCORE_FAIL;
        source = text;
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
    public static AnalysisResult verb(long postag, String source, String stem, String ending, WordPattern pattern) {
        AnalysisResult result = new AnalysisResult(source, stem, ending, null, pattern, SCORE_ANALYSIS);
        result.setSimpleStemPos("V");
//        result.setPos(PosTag.decodeVerb(postag));
        result.resolvePattern();
        return result;
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
//        AnalysisResult result = verb(PosTag.V, source, stem, ending, pattern);
//        result.prefinalEnding = prefinal;
//        return result;
        return null;
    }

    public static AnalysisResult empty(String word) {
        return new AnalysisResult(word, null, null, null, null, SCORE_FAIL);
    }


    private AnalysisResult(String source, String stem, String ending, String josa, WordPattern pattern, int score) {
        this.source=source;
        this.score = score;
        this.stem = stem;
        this.josa = josa;
        this.ending = ending;
        this.wordPattern = pattern;
    }


    public static AnalysisResult noun(long tag, String source, String stem, String josa, WordPattern pattern) {
        AnalysisResult result = new AnalysisResult(source, stem, null, josa, pattern, SCORE_ANALYSIS);
        result.setSimpleStemPos("N");
//        result.setPos(PosTag.decodeNoun(tag));
        result.resolvePattern();
        return result;
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AnalysisResult {")
          .append("source:").append(source).append(", ")
          .append("pattern:").append(wordPattern).append(", ")
          .append("score:").append(score).append(", ")
          .append("stem:").append(stem).append(", ")
          .append("josa:").append(josa).append(", ");

        if (josaList.size() > 0) {
            sb.append("josaList:[");
            for (String each  : josaList) {
                sb.append(each).append(",");
            }
            sb.append("],");
        }

        sb.append("verbSuffix:").append(verbSuffix).append(", ");
        sb.append("nounSuffix:").append(nounSuffix).append(", ");
        sb.append("auxiliaryVerb:").append(auxiliaryVerb).append(", ");
        sb.append("irregularVerbType:").append(irregularVerbType).append(", ");

        sb.append("prefinal:").append(prefinalEnding).append(", ")
          .append("endiing:").append(ending).append(", ");

        if (endingList.size() > 0) {
            sb.append("endingList:[");
            for (String each  : endingList) {
                sb.append(each).append(",");
            }
            sb.append("]");
        }

        sb.append("}");
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

    public String getJosa() {
        return josa;
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

    public void setNounSuffix(String nounSuffix) {
        this.nounSuffix = nounSuffix;
    }

    public void setVerbSuffix(String verbSuffix) {
        this.verbSuffix = verbSuffix;
    }

    public String getStem() {
        return stem;
    }

    public String getSource() {
        return source;
    }

    //  N(1)  /* 체언 : N/PN/NM/XN/CN/UN/AS/HJ/ET */
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
    public void resolvePattern() {
        if (StringUtil.nvl(stem).length() > 0) {
            long tagNum = PosTag.getTagNum(pos);

//            if (PosTag.isKindOf(tagNum, PosTag.N)) {
//                wordPattern = resolveNounPattern();
//            }
//            else if (PosTag.isKindOf(tagNum, PosTag.V)) {
//                wordPattern = resolveVerbPattern();
//            }
//            else if (tagNum == PosTag.AD || tagNum == PosTag.EX || tagNum == PosTag.DT) {
//                if (tagNum == PosTag.AD && hasJosa()) {
//                    wordPattern = WordPattern.ADVJ;
//                } else {
//                    wordPattern = WordPattern.AID;
//                }
//            }
        }
    }

    private WordPattern resolveVerbPattern() {
        WordPattern pattern = WordPattern.VM;

        if (hasJosa()) {
            pattern = WordPattern.VMJ;
            if (hasAuxiliaryVerb()) {
                pattern = WordPattern.VMXMJ;
            }
        } else if (hasEnding())  {
            if ("이".equals(verbSuffix)) {
                pattern = WordPattern.VMCM;
            }

            if (hasAuxiliaryVerb()) {
                pattern = WordPattern.VMXM;
            }
        }

        return pattern;
    }

    private WordPattern resolveNounPattern() {
        WordPattern pattern = WordPattern.N;

        if (hasJosa()) {
            pattern = WordPattern.NJ;
        }


        if (hasVerbSuffix()) { // 용언화 접미사가 있는 경우
            if (hasEnding()) {
                pattern = WordPattern.NSM; // 체언 + 용언화접미사 + 어미

                // 보조동사 있는 경우
                if (hasAuxiliaryVerb()) {
                    char charBeforeXverb = source.charAt(source.lastIndexOf(auxiliaryVerb)-1); // 보조용언 앞 글자
                    if (charBeforeXverb == '아' || charBeforeXverb == '어') {
                        pattern = WordPattern.NSMXM; // 체언 + 용언화접미사 + '아/어' + 보조용언 + 어미
                    }
                }
            } else if (hasJosa()) {
                char charBeforeJosa = source.charAt(source.lastIndexOf(josa)-1);
                Hangul morpheme = Hangul.split(charBeforeJosa);
                if (charBeforeJosa == '기' || morpheme.endsWith('ㅁ')) {
                    pattern = WordPattern.NSMJ;  // 체언 + 용언화접미사 + 'ㅁ/기' + 조사
                }
            }
        }

        if (source.indexOf("에서") > 0 || source.indexOf("부터") > 0) {
            if ("이".equals(verbSuffix) && hasEnding()) {
                pattern = WordPattern.NJCM;  // 체언 + '에서/부터/에서부터' + '이' + 어미
            }
        }

        return pattern;
    }

    private boolean hasAuxiliaryVerb() {
        return StringUtil.nvl(auxiliaryVerb).length() > 0 ? true : false;
    }

    private boolean hasEnding() {
        return StringUtil.nvl(ending).length() > 0 ? true : false;
    }

    private boolean hasVerbSuffix() {
        return StringUtil.nvl(verbSuffix).length() > 0 ? true : false;
    }


    public boolean hasJosa() {
        return StringUtil.nvl(josa).length() > 0 ? true : false;
    }


    public String asString() {
        StringBuilder sb = new StringBuilder(source).append("/");
        sb.append("<").append(stem).append(", ").append(pos).append(">");

        if (wordPattern == WordPattern.VM) {
            appendEndingPart(sb);
        }
        else if (wordPattern == WordPattern.VMJ) {
            appendEndingPart(sb);
            appendJosaPart(sb);
        }
        else if (wordPattern == WordPattern.NJ) {
            appendJosaPart(sb);
        }
        else if (wordPattern == WordPattern.NSM) {
//           appendMorphemeTag(sb, verbSuffix, verbSuffix.equals("이") ? PosTag.CP : PosTag.SV);
           appendEndingPart(sb);
        }

        return sb.toString();
    }

    private void appendJosaPart(StringBuilder sb) {
        if (josa != null) {
//            appendMorphemeTag(sb, josa, PosTag.JO);
        }
        if (josaList != null) {
            for (String each : josaList) {
//                appendMorphemeTag(sb, each, PosTag.JO);
            }
        }
    }

    private void appendEndingPart(StringBuilder sb) {
        if (ending != null) {
            if (prefinalEnding != null) {
//                appendMorphemeTag(sb, prefinalEnding, PosTag.EP);
            }
//            appendMorphemeTag(sb, ending, PosTag.EM);
        }
    }

    public void appendMorphemeTag(StringBuilder sb, String morpheme, long tagNum) {
        sb.append(" + ").append("<").append(morpheme).append(", ").append(PosTag.getTag(tagNum)).append(">");
    }


    /**
     * 기분석 사전에 수록된 형태소 정보를 이용하여 분석결과를 만든다.
     *
     * @param word - 입력어절
     * @param taggedMorphemes - <같, VV> 형식의 형태소 정보를 담고있는 배열
     * @return
     */
    public static AnalysisResult buildResult(String word, String[] taggedMorphemes) {
        AnalysisResult result = new AnalysisResult(word);
        result.setScore(SCORE_ANALYZED_DIC);

        for (String each : taggedMorphemes) {
            Matcher matcher = AnalyzedDic.ANAL_RESULT_PATTERN.matcher(each);
            String pos, morpheme;

            while (matcher.find()) {
                morpheme = matcher.group(1);
                pos = matcher.group(2);

                long tagNum = PosTag.getTagNum(pos);
                if (tagNum == 0) {
                    throw new IllegalArgumentException("Tag not defined:" + pos);
                }

//                if (tagNum == PosTag.JO) {
//                    result.setJosa(morpheme);
//                } else if (tagNum == PosTag.EM) {
//                    result.setEnding(morpheme);
//                } else if (tagNum == PosTag.EP) {
//                    result.setPrefinalEnding(morpheme);
//                } else if (tagNum == PosTag.CP) {
//                    result.setVerbSuffix(morpheme);
//                } else if (PosTag.isKindOf(tagNum, PosTag.S)) {
//                    if (tagNum == PosTag.SN) {
//                        result.setNounSuffix(morpheme);
//                    } else if (tagNum == PosTag.SV || tagNum == PosTag.SJ || tagNum == PosTag.SA) {
//                        result.setVerbSuffix(morpheme);
//                    }
//                }
//                else if (PosTag.isKindOf(tagNum, PosTag.N)) {
//                    result.setStem(morpheme);
//                    result.setPos(PosTag.getTag(tagNum));
//                    result.setSimpleStemPos(PosTag.getTag(PosTag.N));
//                } else if (PosTag.isKindOf(tagNum, PosTag.V)) {
//                    result.setStem(morpheme);
//                    result.setPos(PosTag.getTag(tagNum));
//                    result.setSimpleStemPos(PosTag.getTag(PosTag.V));
//                } else if (tagNum == PosTag.AD || tagNum == PosTag.DT || tagNum == PosTag.EX) { // 단일어: 부사, 관형사, 감탄사
//                    result.setStem(morpheme);
//                    result.setPos(PosTag.getTag(tagNum));
//                    result.setSimpleStemPos(PosTag.getTag(tagNum));
//                }

            }
        }

        result.resolvePattern();
        return result;
    }


}
