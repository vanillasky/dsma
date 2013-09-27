package kr.co.datastreams.dsma.ma.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 어절 구조체
 *
 * User: shkim
 * Date: 13. 7. 22
 * Time: 오후 1:33
 *
 */
public class Eojeol implements Serializable, Comparable {

    public static enum ResultCode {ANALYZED, SUCCESS, ASSUMPTION, FAILURE}; // 형태소 분석결과 반환값

    protected final String string;
    protected final CharType charType;
    protected final int index;
    private final ResultCode resultCode; // 분석결과 코드

    private List<MorphemeList> morphemes = new ArrayList<MorphemeList>(); // 분석결과 리스트
    private Integer[] selectedResults; // 분석 후보 중에서 선택된 분석결과의 index
    private List<AnalysisResult> analysisResults = new ArrayList<AnalysisResult>();


    public static Eojeol createAnalyzed(String string) {
        return new Eojeol(string, CharType.HANGUL, 0, ResultCode.ANALYZED);
    }

    public static Eojeol createAnalyzed(String string, List<MorphemeList> candidates) {
        return new Eojeol(string, CharType.HANGUL, 0, ResultCode.ANALYZED, candidates);
    }

    public Eojeol(String string, CharType charType) {
        this(string, charType, 0);
    }

    public Eojeol(String string, CharType charType, int index) {
        this(string, charType, index, ResultCode.FAILURE);
    }

    public Eojeol(String string, CharType charType, int index, ResultCode resultCode) {
        this.string = string;
        this.charType = charType;
        this.index = index;
        this.resultCode = resultCode;
//        this.analysisResults = new ArrayList<AnalysisResult>();
    }

    public Eojeol(String string, CharType charType, int index, ResultCode resultCode, List<MorphemeList> candidates) {
        this.string = string;
        this.charType = charType;
        this.index = index;
        this.resultCode = resultCode;
        this.morphemes = candidates;
    }

    public List<MorphemeList> getMorphemes() {
        return morphemes;
    }

    @Override
    public int compareTo(Object other) {
        if (other instanceof Eojeol) {
            return index - ((Eojeol)other).index;
        }
        return 0;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Eojeol)) return false;
        String o = (String)other;
        return string != null && o != null && o.equals(string);
    }

    public Eojeol(Token token) {
        this(token.getString(), token.getCharType(), token.getIndex());
    }

    public Eojeol copy() {
        return copy(this.index);
    }

    public Eojeol copy(int newIndex) {
        Eojeol copy = new Eojeol(string, charType, newIndex);
        for (int i=0; i < this.morphemes.size(); i++) {
            copy.morphemes.add(i, this.morphemes.get(i));
        }

        return copy;
    }

    public CharType getCharType() {
        return charType;
    }

    public boolean is(CharType type) {
        return charType == type;
    }

    public String toString() {
        StringBuilder sb  = new StringBuilder();
        sb.append("(").append(index).append(",").append(string).append(",").append(charType).append(")");
        return sb.toString();
    }


    public String asMorphemeString() {
        StringBuilder sb  = new StringBuilder();
        sb.append(string).append("\n");
        for (List<MorphemeList> each : morphemes) {
            sb.append("\t").append(each).append("\n");
        }

        return sb.toString();
    }

    public String getString() {
        return string;
    }

    public int getIndex() {
        return index;
    }

    public void addResults(List<AnalysisResult> candidates) {
        analysisResults.addAll(candidates);
    }

    public void addResult(AnalysisResult result) {
        analysisResults.add(result);
    }

    public int length() {
        return string.length();
    }

    public String substring(int beginIndex) {
        return string.substring(beginIndex);
    }

    public String substring(int beginIndex, int endIndex) {
        return string.substring(beginIndex, endIndex);
    }

    public char charAt(int index) {
        return string.charAt(index);
    }


    public ResultCode getResultCode() {
        return resultCode;
    }


}
