package com.datastreams.nlp.ma.test;

import com.datastreams.nlp.ma.dic.SyllableDic;
import com.datastreams.nlp.ma.model.Hangul;
import com.datastreams.nlp.ma.util.HangulUtil;
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
        Hangul h1 = HangulUtil.split(ch1);
        assertEquals('ㄱ', h1.cho);
        assertEquals('ㅣ', h1.jung);
        assertEquals('ㅁ', h1.jong);

        char ch2 = '화';
        Hangul h2 = HangulUtil.split(ch2);
        assertEquals('ㅎ', h2.cho);
        assertEquals('ㅘ', h2.jung);
        assertEquals('\0', h2.jong);
        assertFalse(h2.hasJong());

        char ch3 = '수';
        Hangul h3 = HangulUtil.split(ch3);
        assertEquals('ㅅ', h3.cho);
        assertEquals('ㅜ', h3.jung);
        assertEquals('\0', h3.jong);
        assertFalse(h3.hasJong());
        assertTrue(HangulUtil.isHangul(ch3));

        char ch4 = 'E';
        Hangul h4 = HangulUtil.split(ch4);
        assertFalse(HangulUtil.isHangul(ch4));

    }

    @Test
    public void testCombine() throws Exception {
        char c = '감';
        Hangul h = HangulUtil.split(c);
        assertEquals('감', h.combine());

        c = '삵';
        h = HangulUtil.split(c);
        assertEquals(c, h.combine());

        c = '모';
        h = HangulUtil.split(c);
        assertEquals(c, h.combine());
    }

    @Test
    public void testRemoveFinalConsonant() throws Exception {
        char result = HangulUtil.removeFinal('를');
        char expected = '르';
        assertEquals(expected, result);

        result = HangulUtil.removeFinal('는');
        expected = '느';
        assertEquals(expected, result);

        result = HangulUtil.removeFinal('달');
        expected = '다';
        assertEquals(expected, result);

        result = HangulUtil.removeFinal('맞');
        expected = '마';
        assertEquals(expected, result);

        result = HangulUtil.removeFinal('은');
        expected = '으';
        assertEquals(expected, result);
    }

    @Test
    public void testReplaceMedialConsonant() throws Exception {
        char ch = '와';
        char result = HangulUtil.replaceMedial(ch, 'ㅗ');
        assertEquals('오', result);

        ch = '워';
        result = HangulUtil.replaceMedial(ch, 'ㅜ');
        assertEquals('우', result);

        ch = '왜';
        result = HangulUtil.replaceMedial(ch, 'ㅚ');
        assertEquals('외', result);


        ch = '여';
        result = HangulUtil.replaceMedial(ch, 'ㅣ');
        assertEquals('이', result);
    }

    @Test
    public void testReplaceFinal() throws Exception {
        char ch = '와';
        char result = HangulUtil.replaceFinal(ch, '아');
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

    @Test
    // 용언의 표층형으로만 사용되는 음절이 있는지 확인
    public void testHasOnlyVerbSyllable() throws Exception {
        String[] words = {"줘봐", "갰", "냈", "괐", "뀐"};
        for (String word : words) {
            boolean result = SyllableDic.hasOnlyVerbSurfaceSyllable(word);
            assertTrue(result);
        }
    }

    @Test
    public void testIsAppendableJosa() throws Exception {
        String charHasJong = "학생";
        Hangul ch = HangulUtil.split(charHasJong.charAt(1));
        assertTrue(ch.isJosaAppendable("은"));
        assertTrue(ch.isJosaAppendable("이"));
        assertFalse(ch.isJosaAppendable("가"));

        String charNotHasJong = "학교";
        Hangul ch2 = HangulUtil.split(charNotHasJong.charAt(1));
        assertFalse(ch2.isJosaAppendable("은"));
        assertFalse(ch2.isJosaAppendable("과"));
        assertFalse(ch2.isJosaAppendable("아"));
        assertFalse(ch2.isJosaAppendable("을"));

        assertTrue(ch2.isJosaAppendable("가"));
        assertTrue(ch2.isJosaAppendable("는"));
        assertTrue(ch2.isJosaAppendable("와"));
    }
}
