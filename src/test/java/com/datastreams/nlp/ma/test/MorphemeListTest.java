package com.datastreams.nlp.ma.test;

import com.datastreams.nlp.ma.model.Morpheme;
import com.datastreams.nlp.ma.model.MorphemeList;
import com.datastreams.nlp.ma.constants.PosTag;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 10. 15
 * Time: 오전 11:15
 * To change this template use File | Settings | File Templates.
 */
public class MorphemeListTest {

    @Test
    public void testAdd() throws Exception {
        Morpheme m1 = new Morpheme("감기", PosTag.NNG);
        Morpheme m2 = new Morpheme("는", PosTag.JKS);

        MorphemeList morphemeList = MorphemeList.create(m1, m2);
        System.out.println(morphemeList);
    }

    @Test
    public void testSortByTailLength() throws Exception {
        Comparator<MorphemeList> comparable = new Comparator<MorphemeList>() {
            @Override
            public int compare(MorphemeList o1, MorphemeList o2) {
                return o2.getLast().length() - o1.getLast().length();
            }
        };

        Morpheme head = new Morpheme("감기", PosTag.N);
        Morpheme tail1 = new Morpheme("로", PosTag.JC);
        Morpheme tail2 = new Morpheme("로는", PosTag.JC);
        Morpheme tail3 = new Morpheme("로까지는", PosTag.JC);

        MorphemeList m1 = MorphemeList.create(head, tail1);
        MorphemeList m2 = MorphemeList.create(head, tail2);
        MorphemeList m3 = MorphemeList.create(head, tail3);

        List<MorphemeList> list = new ArrayList<MorphemeList>();
        list.add(m1);
        list.add(m3);
        list.add(m2);

        Collections.sort(list, comparable);
        assertEquals(m3.toString(), list.get(0).toString());

    }

    @Test
    public void testCopy() throws Exception {
        Morpheme head = new Morpheme("감기", PosTag.N);
        Morpheme tail1 = new Morpheme("로", PosTag.JC);

        MorphemeList m1 = MorphemeList.create(head, tail1);
        MorphemeList m1copy = m1.copy();

        assertFalse(m1 == m1copy);
        assertFalse(m1.getFirst() == m1copy.getFirst());

    }
}
