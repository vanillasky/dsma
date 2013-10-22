package com.datastreams.nlp.ma.model;

import com.datastreams.nlp.ma.constants.PosTag;
import com.datastreams.nlp.ma.constants.Score;
import com.datastreams.nlp.ma.constants.WordPattern;
import com.datastreams.nlp.ma.dic.WordEntry;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * 어절 분석 결과를 표현하기 위한 형태소 리스트.
 * 하나의 어절에 대한 형태소 정보(Morpheme)를 리스트 형태로 관리한다.
 *
 * 형태소 분석결과 어절(Eojeol)은 한 개 이상의 MorphemeList를 가지게 된다.
 * 예) 감기는 -> 감기<NNG>, 는<JKS>
 *
 * User: shkim
 * Date: 13. 10. 14
 * Time: 오후 5:20
 *
 */
public class MorphemeList extends ArrayList implements Comparable<MorphemeList> {
    public static final MorphemeList EMPTY = new MorphemeList();

    private Morpheme first;
    private Morpheme last;
    private Score score; // 가중치
    private WordPattern wordPattern;

    public MorphemeList() {

    }

    public MorphemeList(Score score) {
        this.score = score;
    }

    public static MorphemeList create(Score score, WordPattern pattern, Morpheme... morphemes) {
        MorphemeList mlist = new MorphemeList();
        for (Morpheme each : morphemes) {
            mlist.add(each);
        }
        mlist.score = score;
        mlist.wordPattern = pattern;
        return mlist;
    }


    public static MorphemeList create(MorphemeList source) {
        MorphemeList mlist = new MorphemeList();
        Iterator<Morpheme> iter = source.iterator();
        for (;iter.hasNext();) {
            mlist.add(iter.next().copy());
        }
        mlist.score = source.score;
        mlist.wordPattern = source.getWordPattern();
        return mlist;
    }

    public static MorphemeList create(Score score, Morpheme... morphemes) {

        MorphemeList mlist = new MorphemeList();
        for (Morpheme each : morphemes) {
            mlist.add(each);
        }
        mlist.score = score;
        return mlist;
    }

    public static MorphemeList create(Morpheme... morphemes) {
        MorphemeList mlist = new MorphemeList();
        for (Morpheme each : morphemes) {
            mlist.add(each);
        }
        return mlist;
    }


    public boolean add(Morpheme morpheme) {
        if (first == null) {
            first = morpheme;
        }

        last = morpheme;
        return super.add(morpheme);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Iterator<Morpheme> it = super.iterator();
        for (;it.hasNext();) {
            sb.append(it.next()).append("+");
        }

        assert sb.length() > 0;
        String result = sb.substring(0, sb.length()-1);

        return result;
    }

    @Override
    public int compareTo(MorphemeList o) {
        return score.intValue() - o.score.intValue();
    }

    public Morpheme getFirst() {
        return first;
    }

    public Morpheme getLast() {
        return last;
    }

    public Score getScore() {
        return score;
    }

    public WordPattern getWordPattern() {
        return wordPattern;
    }

    public MorphemeList copy() {
        return MorphemeList.create(this);
    }
}
