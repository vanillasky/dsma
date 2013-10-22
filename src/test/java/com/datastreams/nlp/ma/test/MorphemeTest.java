package com.datastreams.nlp.ma.test;

import com.datastreams.nlp.ma.model.Morpheme;
import com.datastreams.nlp.ma.constants.PosTag;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 10. 14
 * Time: 오후 5:17
 * To change this template use File | Settings | File Templates.
 */
public class MorphemeTest {

    @Test
    public void testEquals() throws Exception {
        Morpheme m1 = new Morpheme("감기", PosTag.NNG);
        Morpheme m2 = new Morpheme("감기", PosTag.NNG);
        Morpheme o1 = new Morpheme("감기", PosTag.VV);

        assertEquals(m1, m2);
        assertNotEquals(m1, o1);
    }
}
