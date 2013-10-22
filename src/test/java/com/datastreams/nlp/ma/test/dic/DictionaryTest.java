package com.datastreams.nlp.ma.test.dic;


import com.datastreams.nlp.ma.constants.PosTag;
import com.datastreams.nlp.ma.dic.Dictionary;
import com.datastreams.nlp.ma.dic.WordEntry;
import org.junit.Before;
import org.junit.Test;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

/**
 *
 * User: shkim
 * Date: 13. 7. 18
 * Time: 오후 4:51
 *
 */
public class DictionaryTest {

    WordEntry[] eomis = {
            WordEntry.createWithTags("거나", new String[]{"EF"}),
            WordEntry.createWithTags("구먼", new String[]{"EF"})
    };

    WordEntry[] josas = {
            WordEntry.createWithTags("가", new String[]{"JC"}),
            WordEntry.createWithTags("같이", new String[]{"JC"}),
            WordEntry.createWithTags("라고", new String[]{"JC"}),
            WordEntry.createWithTags("를", new String[]{"JC"})
    };


    WordEntry[] prefiexes = {
            WordEntry.createWithTags("최", new String[]{""}),
            WordEntry.createWithTags("고", new String[]{""}),
            WordEntry.createWithTags("남", new String[]{""}),
            WordEntry.createWithTags("비", new String[]{""}),
    };

    WordEntry[] suffixes = {
            WordEntry.createWithTags("같", new String[]{""}),
            WordEntry.createWithTags("거리", new String[]{""}),
            WordEntry.createWithTags("당하", new String[]{""}),
            WordEntry.createWithTags("답", new String[]{""}),
    };

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testSearch_조사() throws Exception {
        for (WordEntry each : josas) {
            assertEquals(each.getString(), Dictionary.searchJosa(each.getString()).getString());
        }
    }


    @Test
    public void testSearch_어미() throws Exception {
        for (WordEntry each : eomis) {
            assertEquals(each.getString(), Dictionary.searchEomi(each.getString()).getString());
        }
    }


    @Test
    public void testSearch_접두사() throws Exception {
        for (WordEntry each : prefiexes) {
            assertEquals(each.getString(), Dictionary.searchPrefix(each.getString()).getString());
        }
    }


    @Test
    public void testSearch_접미사() throws Exception {
        for (WordEntry each : suffixes) {
            assertEquals(each.getString(), Dictionary.searchSuffix(each.getString()).getString());
        }
    }

    @Test
    public void testLoadDictionaryAndPrintTrie() throws Exception {
        WordEntry word = Dictionary.searchNoun("괴물");
        assertEquals("괴물", word.getString());
        Dictionary.printFixedWordDictionary(new PrintWriter(new FileWriter("d:/temp/ka.txt")));
        Dictionary.printVerbDictionary(new PrintWriter(new FileWriter("d:/temp/ka_verb.txt")));
    }

    @Test
    public void testGetPrefixedBy() throws Exception {
        //String[] expected = {"포르노", "포르르", "포르말린", "포르볼","포르노그라피"};
        List<String> candidates = new ArrayList<String>();
        Iterator iter = Dictionary.searchPrefixedBy("포르");
        assertNotNull(iter);
        while (iter.hasNext()) {
            WordEntry entry = (WordEntry)iter.next();
            System.out.println(entry.getString());
            candidates.add(entry.getString());
        }
        assertEquals(26, candidates.size());
    }

    @Test
    public void testFindWithPosTag_명사() throws Exception {
        String word = "가계약";
        WordEntry entry = Dictionary.searchNoun(word);
        assertEquals(word, entry.getString());
        assertTrue(entry.isTagOf(PosTag.N));
    }


    @Test
    public void testFindNounOrAdverb() throws Exception {
        String word = "가급적";
        assertEquals(word, Dictionary.searchNounOrAbVerb(word).getString());
    }


    @Test
    public void testSearchVerb() throws Exception {
        String word = "거칠";
        assertEquals(word, Dictionary.searchVerb(word).getString());
        assertEquals(PosTag.VA, Dictionary.searchVerb(word).tag());
    }


    @Test
    public void testSearchPrefixedBy_Verb() throws Exception {
        String word = "거치";
        Iterator iter = Dictionary.searchVerbPrefixedBy(word);
        while (iter.hasNext()) {
            WordEntry entry = (WordEntry)iter.next();

            WordEntry dicEntry = Dictionary.search(entry.getString(), PosTag.V);
            assertEquals(dicEntry, entry);
        }
    }

}
