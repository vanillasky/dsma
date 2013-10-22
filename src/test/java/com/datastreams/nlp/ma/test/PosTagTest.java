package com.datastreams.nlp.ma.test;


import com.datastreams.nlp.ma.constants.PosTag;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 10. 1
 * Time: 오전 11:22
 * To change this template use File | Settings | File Templates.
 */
public class PosTagTest {

    @Test
    public void testDecodeNoun() throws Exception {
        long nounAndAdverb = PosTag.NNP | PosTag.AD;
        assertEquals("NNP", PosTag.decodeNoun(nounAndAdverb));
    }

    @Test
    public void testAdverb() throws Exception {
        long nounAndAdverb = PosTag.NNG | PosTag.MAG;
        assertEquals("MAG", PosTag.decodeAdverb(nounAndAdverb));
        assertEquals("NNG", PosTag.decodeNoun(nounAndAdverb));
    }

    @Test
    public void testExtractNounTag() throws Exception {
        long nounAndAdverb = PosTag.NNP | PosTag.MAG;
        long result = PosTag.extractNounTag(nounAndAdverb);
        assertEquals(PosTag.NNP, result);

        //TODO: 같은 분류의 품사가 중복되는 경우 처리 기준 필요
        long duplicatedNounAndAdverb = PosTag.NNP | PosTag.NNG | PosTag.MAG;
        result = PosTag.extractNounTag(duplicatedNounAndAdverb);
        System.out.println("//TODO: 같은 분류의 품사가 중복되는 경우 처리 기준 필요, NNP or NNG:" + result);

    }


    @Test
    public void testExtractJosaTag() throws Exception {
        long jj = PosTag.JC | PosTag.JKS;
        long result = PosTag.extracteJosaTag(jj);

        System.out.println("JC:" + PosTag.JC);
        System.out.println("JKS:" + Long.bitCount(PosTag.JKS));
        System.out.println(""+ (jj & Long.lowestOneBit(jj)));
        System.out.println(PosTag.getTag(jj & Long.highestOneBit(jj)));


    }

}
