package com.datastreams.util.test;

import com.datastreams.dsma.ma.Trie;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 6. 25
 * Time: 오후 4:50
 * To change this template use File | Settings | File Templates.
 */
public class TrieTest {

    @Test
    public void testTrie() throws Exception  {
        Trie trie = new Trie();
        trie.insert("Hello");
        System.out.println(trie.search("Hello"));
        System.out.println(trie.search("hello"));

    }
}
