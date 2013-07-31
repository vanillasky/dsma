package kr.co.datastreams.dsma.ma.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 7. 22
 * Time: 오후 1:33
 * To change this template use File | Settings | File Templates.
 */
public class Word implements Serializable, Comparable {




    public static enum ResultCode {SUCCESS, ASSUMPTION, FAILURE}; // 형태소 분석결과 반환값

    protected final String string;
    protected CharType charType;
    protected int index;

    private ResultCode resultCode; // 분석결과 코드
    private List<AnalysisResult> analysisResults; // 분석 후보들
    private Integer[] selectedCandidates; // 분석 후보 중에서 선택된 분석결과의 index

    @Override
    public int compareTo(Object other) {
        if (other instanceof Word) {
            return index - ((Word)other).index;
        }
        return 0;
    }

    public Word() {
        this.string = null;
        this.charType = CharType.ETC;
        this.index = 0;
        this.resultCode = ResultCode.FAILURE;
    }

    public Word(String string, CharType charType) {
        this(string, charType, 0);
    }

    public Word(String string, CharType charType, int index) {
        this.string = string;
        this.charType = charType;
        this.index = index;
        resultCode = ResultCode.FAILURE;
        analysisResults = new ArrayList<AnalysisResult>();
    }


    public boolean equals(String other) {
        return string != null && other != null && other.equals(string);
    }

    public Word(Word token) {
        this(token.string, token.charType, token.index);
    }

    public Word copy() {
        Word copy = new Word(string, charType, index);
        return copy;
    }

    public CharType getCharType() {
        return charType;
    }

    public String getCharTypeName() {
        return charType.name();
    }


    public boolean is(CharType type) {
        return charType == type;
    }

    public String toString() {
        StringBuilder sb  = new StringBuilder();
        sb.append("(").append(index).append(",").append(string).append(",").append(getCharTypeName()).append(")");
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
}
