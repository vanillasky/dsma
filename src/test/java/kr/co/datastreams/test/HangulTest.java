package kr.co.datastreams.test;

import kr.co.datastreams.dsma.util.Hangul;
import org.junit.Test;
import static org.junit.Assert.*;

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
    }
}
