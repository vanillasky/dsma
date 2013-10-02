package kr.co.datastreams.dsma.model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * 하나의 어절에 대한 형태소 정보(Morpheme)를 리스트 형태로 관리한다.
 *
 * User: shkim
 * Date: 13. 9. 16
 * Time: 오후 4:38
 *
 */
public class MorphemeList extends ArrayList implements Comparable<MorphemeList> {
    private Morpheme first = null;
    private Morpheme last = null;
    private Score score;

    public MorphemeList() {

    }

    public MorphemeList(Score score) {
        this.score = score;
    }

    public boolean add(Morpheme morpheme) {
        if (first == null) {
            first = morpheme;
        }

        last = morpheme;
        return super.add(morpheme);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        Iterator it = super.iterator();
        for (;it.hasNext();) {
            sb.append(it.next()).append("+");
        }
        String result = sb.substring(0, sb.length()-1);

        return result;
    }

    public Score getScore() {
        return score;
    }

    @Override
    public int compareTo(MorphemeList o) {
        return score.intValue() - o.getScore().intValue();
    }
}
