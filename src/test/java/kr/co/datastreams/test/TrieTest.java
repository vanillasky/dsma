package kr.co.datastreams.test;

import kr.co.datastreams.dsma.dic.trie.SampleTrie;
import kr.co.datastreams.dsma.dic.trie.Trie;
import kr.co.datastreams.dsma.dic.trie.TrieEdge;
import kr.co.datastreams.dsma.ma.Morpheme;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

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
        Morpheme m1 = new Morpheme("가게");
        Morpheme m2 = new Morpheme("가게집");

        dic.add(m1.getWord(), m1);
        dic.add(m2.getWord(), m2);

        assertNotNull(dic.get(m1.getWord()));
        System.out.println(dic.get(m1.getWord()));
        System.out.println(dic.get(m2.getWord()));
    }
}
