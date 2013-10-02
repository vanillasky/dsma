package kr.co.datastreams.dsma.model;

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

    private final String string;
    private final CharType charType;
    private final int index;
    private final List<MorphemeList> morphemes;
    private Score score;


    public static Eojeol createAnalyzed(String string, List<MorphemeList> candidates) {
        return new Eojeol(string, CharType.HANGUL, 0, Score.AnalyzedDic, candidates);
    }

    public static Eojeol createHeuristic(Token token, List<MorphemeList> candidates) {
        return new Eojeol(token, Score.Heuristic, candidates);
    }

    public static Eojeol createFailure(Token token) {
        return new Eojeol(token.getString(), token.getCharType(), token.getIndex());
    }

    public Eojeol(String string, CharType charType) {
        this(string, charType, 0);
    }

    public Eojeol(String string, CharType charType, int index) {
        this(string, charType, index, Score.Failure);
    }

    public Eojeol(Token token, Score score, List<MorphemeList> morphemeLists) {
        this(token.getString(), token.getCharType(), token.getIndex(), score, morphemeLists);
    }

    public Eojeol(String string, CharType charType, int index, Score score) {
        this.string = string;
        this.charType = charType;
        this.index = index;
        this.score = score;
        this.morphemes = new ArrayList<MorphemeList>();
    }

    public Eojeol(String string, CharType charType, int index, Score score, List<MorphemeList> candidates) {
        this.string = string;
        this.charType = charType;
        this.index = index;
        this.score = score;
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
        Collections.sort(morphemes);
        StringBuilder sb  = new StringBuilder();
        sb.append(string).append("\t").append("<").append(score).append(">").append("\n");
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

    public Score getScore() {
        return score;
    }


    public void setScore(Score score) {
        this.score = score;
    }


}
