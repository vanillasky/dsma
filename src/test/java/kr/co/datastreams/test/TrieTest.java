package kr.co.datastreams.test;

import kr.co.datastreams.dsma.dic.trie.Trie;
import kr.co.datastreams.dsma.ma.Morpheme;
import org.junit.Test;

import java.io.PrintWriter;

import static org.junit.Assert.*;
/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 7. 16
 * Time: 오후 4:37
 * To change this template use File | Settings | File Templates.
 */
public class TrieTest {

    Trie<String, Morpheme> dic = new Trie<String, Morpheme>();

    @Test
    public void testAdd() throws Exception {
//        Morpheme m1 = new Morpheme("가게");
        Morpheme m3 = new Morpheme("가게방");
        Morpheme m2 = new Morpheme("가게집");
        Morpheme m4 = new Morpheme("가게집은");
        Morpheme m5 = new Morpheme("나비");
        Morpheme m6 = new Morpheme("나비효");
        Morpheme m8 = new Morpheme("나비효과");
        Morpheme m7 = new Morpheme("나비효리");

//        dic.add(m1.getWord(), m1);
        dic.add(m3.getWord(), m3);
        dic.add(m2.getWord(), m2);
        dic.add(m4.getWord(), m4);
        dic.add(m5.getWord(), m5);
        dic.add(m6.getWord(), m6);
        dic.add(m8.getWord(), m8);
        dic.add(m7.getWord(), m7);

//        assertNotNull(dic.get(m1.getWord()));
        dic.print(new PrintWriter(System.out));
    }

    @Test
    public void testGet() throws Exception {
//        Morpheme m1 = new Morpheme("가게");
        Morpheme m3 = new Morpheme("가게방");
        Morpheme m2 = new Morpheme("가게집");
        Morpheme m4 = new Morpheme("가게집은");
        Morpheme m5 = new Morpheme("나비");
        Morpheme m6 = new Morpheme("나비효");
        Morpheme m8 = new Morpheme("나비효과");
        Morpheme m7 = new Morpheme("나비효리");

//        dic.add(m1.getWord(), m1);
        dic.add(m3.getWord(), m3);
        dic.add(m2.getWord(), m2);
        dic.add(m4.getWord(), m4);
        dic.add(m5.getWord(), m5);
        dic.add(m6.getWord(), m6);
        dic.add(m8.getWord(), m8);
        dic.add(m7.getWord(), m7);

        assertNotNull(dic.get("나비효과"));
        System.out.println(dic.get("나비효과"));
    }

    @Test
    public void testMatch() throws Exception {
        String str1 = "abcde", str2 = "abc";
        int startOffset = 0, stopOffset = 5;
        int expected = -1;

        int result = dic.match(str1, str2, startOffset, stopOffset);
        assertEquals(expected, result);

        str2 = "abcde";
        startOffset = 0;
        stopOffset = str1.length();
        expected = -1;
        result = dic.match(str1, str2, startOffset, stopOffset);
        assertEquals(expected, result);

        str2 = "bcd";
        startOffset = 1;
        stopOffset = 3;
        expected = 2;
        result = dic.match(str1, str2, startOffset, stopOffset);
        assertEquals(expected, result);

        str2 = "abcd";
        startOffset = 1;
        stopOffset = str1.length();
        expected = 0;
        result = dic.match(str1, str2, startOffset, stopOffset);
        assertEquals(expected, result);

        str2 = "bXd";
        startOffset = 1;
        stopOffset = 5;
        expected = 1;
        result = dic.match(str1, str2, startOffset, stopOffset);
        assertEquals(expected, result);
        System.out.println(str1.charAt(result));

        str1 = "가게방";
        str2 = "가게집";
        startOffset = 0;
        stopOffset = str1.length();
        expected = 2;
        result = dic.match(str1, str2, startOffset, stopOffset);
        assertEquals(expected, result);
        System.out.println(str1.charAt(result));

    }


}
