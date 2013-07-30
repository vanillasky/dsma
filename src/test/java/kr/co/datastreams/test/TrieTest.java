package kr.co.datastreams.test;

import kr.co.datastreams.dsma.dic.trie.Trie;
import kr.co.datastreams.dsma.dic.trie.ValueIterator;
import kr.co.datastreams.dsma.ma.model.WordEntry;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 7. 16
 * Time: 오후 4:37
 * To change this template use File | Settings | File Templates.
 */
public class TrieTest {

    Trie<String, WordEntry> dic;

    @Before
    public void setUp() throws Exception {
        dic = new Trie<String, WordEntry>();
    }

    @Test
    public void testGet() throws Exception {
        WordEntry m3 = new WordEntry("가게방");
        WordEntry m2 = new WordEntry("가게집");
        WordEntry m4 = new WordEntry("가게집은");
        WordEntry m5 = new WordEntry("나비");
        WordEntry m6 = new WordEntry("나비효");
        WordEntry m8 = new WordEntry("나비효과");
        WordEntry m7 = new WordEntry("나비효리");

        dic.add(m3.getString(), m3);
        dic.add(m2.getString(), m2);
        dic.add(m4.getString(), m4);
        dic.add(m5.getString(), m5);
        dic.add(m6.getString(), m6);
        dic.add(m8.getString(), m8);
        dic.add(m7.getString(), m7);

        assertNotNull(dic.get("나비효과"));
    }

    @Test
    public void testGetPrefixedBy() throws  Exception {
        WordEntry m1 = new WordEntry("가게");
        WordEntry m3 = new WordEntry("가게방");
        WordEntry m2 = new WordEntry("가게집");
        WordEntry m4 = new WordEntry("가게집은");
        WordEntry m5 = new WordEntry("나비");
        WordEntry m6 = new WordEntry("나비효");
        WordEntry m8 = new WordEntry("나비효과");
        WordEntry m7 = new WordEntry("나비효는");
        WordEntry m9 = new WordEntry("나비효다");
        WordEntry m10 = new WordEntry("나비효리다");
        WordEntry m11 = new WordEntry("나비효리다가");
        WordEntry m12 = new WordEntry("나비효리다가나");

        dic.add(m1.getString(), m1);
        dic.add(m3.getString(), m3);
        dic.add(m2.getString(), m2);
        dic.add(m4.getString(), m4);
        dic.add(m5.getString(), m5);
        dic.add(m6.getString(), m6);
        dic.add(m8.getString(), m8);
        dic.add(m7.getString(), m7);
        dic.add(m9.getString(), m9);
        dic.add(m10.getString(), m10);
        dic.add(m11.getString(), m11);
        dic.add(m12.getString(), m12);



        ValueIterator iter = dic.getPrefixedBy("나비");
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
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
