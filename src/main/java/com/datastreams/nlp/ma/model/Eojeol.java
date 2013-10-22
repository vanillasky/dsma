package com.datastreams.nlp.ma.model;


import com.datastreams.nlp.ma.constants.CharType;
import com.datastreams.nlp.ma.constants.Score;
import com.datastreams.nlp.ma.util.EojeolFormatter;
import com.datastreams.nlp.ma.util.SimpleScoreFormatter;

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

    public static final Eojeol EMPTY = new Eojeol(null, null, 0, Score.Failure);

    private final EojeolFormatter defaultForamatter = new SimpleScoreFormatter();
    private final String source;
    private final CharType charType;
    private final int index;
    private final List<MorphemeList> candidates;
    private Score score;


//    public static Eojeol createSuccess(Token token, List<MorphemeList> candidates) {
//        return new Eojeol(token.getString(), token.getCharType(), token.getIndex(), Score.Success, candidates);
//    }
//
    public static Eojeol createAnalyzed(String string, List<MorphemeList> candidates) {
        return new Eojeol(string, CharType.HANGUL, 0, Score.AnalyzedDic, candidates);
    }
//
//    public static Eojeol createHeuristic(Token token, List<MorphemeList> candidates) {
//        return new Eojeol(token, Score.Heuristic, candidates);
//    }
//
//    public static Eojeol createNumber(Token token) {
//        return createWithPosTag(token, kr.co.datastreams.dsma.model.PosTag.SN);
//
//    }
//
//    public static Eojeol createSymbol(Token token) {
//        WordEntry entry = Dictionary.find(token.getString());
//        long tagnum = entry == null ? kr.co.datastreams.dsma.model.PosTag.SW : entry.tag();
//        return createWithPosTag(token, tagnum);
//
//    }

//    public static Eojeol createOtherLanguage(Token token) {
//        return createWithPosTag(token, PosTag.SL);
//    }

//    private static Eojeol createWithPosTag(Token token, long tagNum) {
//        Morpheme m = new Morpheme(token.getString(), tagNum);
//        MorphemeList morphemeList = new MorphemeList();
//        morphemeList.add(m);
//
//        List<MorphemeList> candiates = new ArrayList<MorphemeList>(1);
//        candiates.add(morphemeList);
//        return new Eojeol(token, Score.Success, candiates);
//    }
//
//
//    public static Eojeol createFailure(Token token) {
//        return new Eojeol(token.getString(), token.getCharType(), token.getIndex());
//    }

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
        this.source = string;
        this.charType = charType;
        this.index = index;
        this.score = score;
        this.candidates = new ArrayList<MorphemeList>();
    }

    public Eojeol(String string, CharType charType, int index, Score score, List<MorphemeList> candidates) {
        this.source = string;
        this.charType = charType;
        this.index = index;
        this.score = score;
        this.candidates = candidates;
    }

    public List<MorphemeList> getMorphemes() {
        return candidates;
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
        return source != null && o != null && o.equals(source);
    }

    public Eojeol(Token token) {
        this(token.getString(), token.getCharType(), token.getIndex());
    }

    public Eojeol copy() {
        return copy(this.index);
    }

    public Eojeol copy(int newIndex) {
        Eojeol copy = new Eojeol(source, charType, newIndex);
        for (int i=0; i < this.candidates.size(); i++) {
            copy.candidates.add(i, this.candidates.get(i));
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
        sb.append("(").append(index).append(",").append(source).append(",").append(charType).append(")");
        return sb.toString();
    }


    public String asMorphemeString() {
        return asMorphemeString(defaultForamatter);
    }

    public String asMorphemeString(EojeolFormatter formatter) {
        return formatter.format(this);
    }



    public String getSource() {
        return source;
    }

    public int getIndex() {
        return index;
    }

    public int length() {
        return source.length();
    }

    public String substring(int beginIndex) {
        return source.substring(beginIndex);
    }

    public String substring(int beginIndex, int endIndex) {
        return source.substring(beginIndex, endIndex);
    }

    public char charAt(int index) {
        return source.charAt(index);
    }

    public Score getScore() {
        return score;
    }


    public void setScore(Score score) {
        this.score = score;
    }

    public boolean  isEmpty() {
        return this.source == null;
    }



}
