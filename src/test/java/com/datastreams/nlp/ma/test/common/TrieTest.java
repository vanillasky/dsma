package com.datastreams.nlp.ma.test.common;


import com.datastreams.nlp.common.trie.Trie;
import com.datastreams.nlp.common.trie.ValueIterator;
import com.datastreams.nlp.ma.constants.PosTag;
import com.datastreams.nlp.ma.dic.WordEntry;
import org.junit.Before;
import org.junit.Test;

import java.io.PrintWriter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 *
 * User: shkim
 * Date: 13. 7. 16
 * Time: 오후 4:37
 *
 */
public class TrieTest {

    Trie<String, WordEntry> trie;

    @Before
    public void setUp() throws Exception {
        trie = new Trie<String, WordEntry>();
    }

    @Test
    public void testGet() throws Exception {
        WordEntry m3 = WordEntry.create("가게방");
        WordEntry m2 = WordEntry.create("가게집");
        WordEntry m4 = WordEntry.create("가게집은");
        WordEntry m5 = WordEntry.create("나비");
        WordEntry m6 = WordEntry.create("나비효");
        WordEntry m8 = WordEntry.create("나비효과");
        WordEntry m7 = WordEntry.create("나비효리");

        trie.add(m3.getString(), m3);
        trie.add(m2.getString(), m2);
        trie.add(m4.getString(), m4);
        trie.add(m5.getString(), m5);
        trie.add(m6.getString(), m6);
        trie.add(m8.getString(), m8);
        trie.add(m7.getString(), m7);

        assertNotNull(trie.get("나비효과"));
        //trie.print(new PrintWriter(System.out));
    }

    @Test
    public void testGetPrefixedBy() throws  Exception {
        WordEntry m1 = WordEntry.create("가게");
        WordEntry m3 = WordEntry.create("가게방");
        WordEntry m2 = WordEntry.create("가게집");
        WordEntry m4 = WordEntry.create("가게집은");
        WordEntry m5 = WordEntry.create("나비");
        WordEntry m6 = WordEntry.create("나비효");
        WordEntry m8 = WordEntry.create("나비효과");
        WordEntry m7 = WordEntry.create("나비효는");
        WordEntry m9 = WordEntry.create("나비효다");
        WordEntry m10 = WordEntry.create("나비효리다");
        WordEntry m11 = WordEntry.create("나비효리다가");
        WordEntry m12 = WordEntry.create("나비효리다가나");

        trie.add(m1.getString(), m1);
        trie.add(m3.getString(), m3);
        trie.add(m2.getString(), m2);
        trie.add(m4.getString(), m4);
        trie.add(m5.getString(), m5);
        trie.add(m6.getString(), m6);
        trie.add(m8.getString(), m8);
        trie.add(m7.getString(), m7);
        trie.add(m9.getString(), m9);
        trie.add(m10.getString(), m10);
        trie.add(m11.getString(), m11);
        trie.add(m12.getString(), m12);



        ValueIterator iter = trie.getPrefixedBy("나비");
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
    }


    @Test
    public void testMatch() throws Exception {
        String str1 = "abcde", str2 = "abc";
        int startOffset = 0, stopOffset = 5;
        int expected = -1;

        int result = trie.match(str1, str2, startOffset, stopOffset);
        assertEquals(expected, result);

        str2 = "abcde";
        startOffset = 0;
        stopOffset = str1.length();
        expected = -1;
        result = trie.match(str1, str2, startOffset, stopOffset);
        assertEquals(expected, result);

        str2 = "bcd";
        startOffset = 1;
        stopOffset = 3;
        expected = 2;
        result = trie.match(str1, str2, startOffset, stopOffset);
        assertEquals(expected, result);

        str2 = "abcd";
        startOffset = 1;
        stopOffset = str1.length();
        expected = 0;
        result = trie.match(str1, str2, startOffset, stopOffset);
        assertEquals(expected, result);

        str2 = "bXd";
        startOffset = 1;
        stopOffset = 5;
        expected = 1;
        result = trie.match(str1, str2, startOffset, stopOffset);
        assertEquals(expected, result);
        System.out.println(str1.charAt(result));

        str1 = "가게방";
        str2 = "가게집";
        startOffset = 0;
        stopOffset = str1.length();
        expected = 2;
        result = trie.match(str1, str2, startOffset, stopOffset);
        assertEquals(expected, result);
        System.out.println(str1.charAt(result));

    }


    @Test
    public void testDuplicated() throws Exception {
        trie.add("가가린", WordEntry.create("가가린"));
        trie.add("가가린", WordEntry.create("가가린"));
        trie.add("가가린", WordEntry.createWithTags("가가린", new String[]{"MAJ"}));
        trie.add("가가린2", WordEntry.createWithTags("가가린2", new String[]{"MAJ"}));
        assertNotNull(trie.get("가가린"));
        trie.print(new PrintWriter(System.out));
    }

}
