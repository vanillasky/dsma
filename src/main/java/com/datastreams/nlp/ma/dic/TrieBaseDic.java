package com.datastreams.nlp.ma.dic;

import com.datastreams.nlp.common.trie.Trie;
import kr.co.datastreams.commons.util.FileUtil;
import kr.co.datastreams.commons.util.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

/**
 *
 * User: shkim
 * Date: 13. 10. 16
 * Time: 오후 5:07
 *
 */
public class TrieBaseDic {

    protected static final Logger logger = LoggerFactory.getLogger("Dictionary");

    protected final Trie<String, WordEntry> trie = new Trie<String, WordEntry>();
    protected final WordEntryComposer wordEntryComposer;

    TrieBaseDic(WordEntryComposer wordEntryComposer) {
        this.wordEntryComposer = wordEntryComposer;
    }

    protected void load(String fileName) {
        if (fileName == null || fileName.length() == 0) {
            throw new IllegalArgumentException("Dictionary file name should not be null.");
        }
        StopWatch watch = null;
        if (logger.isDebugEnabled()) {
            watch = StopWatch.create();
            watch.start();
        }

        loadWithComposer(wordEntryComposer, fileName);

        if (logger.isDebugEnabled()) {
            watch.end();
            watch.println("dictionary "+ fileName + " loaded, ");
        }
    }


    protected void loadWithComposer(WordEntryComposer composer, String fileName) {
        List<String> lines = FileUtil.readLines(fileName, "UTF-8");

        WordEntry entry;
        for (Iterator<String> iter = lines.iterator(); iter.hasNext(); ) {
            entry = composer.compose(iter.next());
            if (entry != null && entry.tag() > 0) {
                trie.add(entry.getString(), entry);
            }
        }
    }

    WordEntry getWord(String key) {
        return (WordEntry) trie.get(key);
    }


    void printDictionary(PrintWriter writer) {
        trie.print(writer);
    }

    Iterator searchPrefixedBy(String prefix) {
        return trie.getPrefixedBy(prefix);
    }

    WordEntry searchWith(String word, long tagNum) {
        WordEntry entry = getWord(word);
        if (entry == null) return null;

        if (entry.isTagOf(tagNum)) return entry;

        return null;
    }


    boolean exists(String word) {
        return getWord(word) == null ? null : true;
    }

    public void print(PrintWriter writer) {
        trie.print(writer);
    }
}
