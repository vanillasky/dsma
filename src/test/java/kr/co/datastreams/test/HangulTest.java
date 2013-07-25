package kr.co.datastreams.test;

import kr.co.datastreams.dsma.util.Hangul;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 7. 23
 * Time: 오후 2:36
 * To change this template use File | Settings | File Templates.
 */
public class HangulTest {

    @Test
    public void testSplit() throws Exception {
        char ch1 = '김';
        Hangul h1 = Hangul.split(ch1);
        assertEquals('ㄱ', h1.cho);
        assertEquals('ㅣ', h1.jung);
        assertEquals('ㅁ', h1.jong);

        char ch2 = '화';
        Hangul h2 = Hangul.split(ch2);
        assertEquals('ㅎ', h2.cho);
        assertEquals('ㅘ', h2.jung);
        assertEquals('\0', h2.jong);
        assertFalse(h2.hasJong());

        char ch3 = '수';
        Hangul h3 = Hangul.split(ch3);
        assertEquals('ㅅ', h3.cho);
        assertEquals('ㅜ', h3.jung);
        assertEquals('\0', h3.jong);
        assertFalse(h3.hasJong());
        assertTrue(Hangul.isHangul(ch3));

        char ch4 = 'E';
        Hangul h4 = Hangul.split(ch4);
        assertFalse(Hangul.isHangul(ch4));

    }

    @Test
    public void testCombine() throws Exception {
        char c = '감';
        Hangul h = Hangul.split(c);
        assertEquals('감', h.combine());

        c = '삵';
        h = Hangul.split(c);
        assertEquals(c, h.combine());

        c = '모';
        h = Hangul.split(c);
        assertEquals(c, h.combine());
    }

    @Test
    public void testRemoveFinalConsonant() throws Exception {
        char result = Hangul.removeFinal('를');
        char expected = '르';
        assertEquals(expected, result);

        result = Hangul.removeFinal('는');
        expected = '느';
        assertEquals(expected, result);

        result = Hangul.removeFinal('달');
        expected = '다';
        assertEquals(expected, result);

        result = Hangul.removeFinal('맞');
        expected = '마';
        assertEquals(expected, result);

        result = Hangul.removeFinal('은');
        expected = '으';
        assertEquals(expected, result);
    }

    @Test
    public void testReplaceMedialConsonant() throws Exception {
        char ch = '와';
        char result = Hangul.replaceMedial(ch, 'ㅗ');
        assertEquals('오', result);

        ch = '워';
        result = Hangul.replaceMedial(ch, 'ㅜ');
        assertEquals('우', result);

        ch = '왜';
        result = Hangul.replaceMedial(ch, 'ㅚ');
        assertEquals('외', result);


        ch = '여';
        result = Hangul.replaceMedial(ch, 'ㅣ');
        assertEquals('이', result);
    }

    @Test
    public void testReplaceFinal() throws Exception {
        char ch = '와';
        char result = Hangul.replaceFinal(ch, '아');
        assertEquals('아', result);

//        ch = '워';
//        result = Hangul.replaceMedial(ch, 'ㅜ');
//        assertEquals('우', result);
//
//        ch = '왜';
//        result = Hangul.replaceMedial(ch, 'ㅚ');
//        assertEquals('외', result);
//
//
//        ch = '여';
//        result = Hangul.replaceMedial(ch, 'ㅣ');
//        assertEquals('이', result);
    }
}
