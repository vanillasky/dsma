package kr.co.datastreams.dsma.dic;

import kr.co.datastreams.commons.util.FileUtil;
import kr.co.datastreams.commons.util.StopWatch;
import kr.co.datastreams.dsma.conf.ConfKeys;
import kr.co.datastreams.dsma.conf.Configuration;
import kr.co.datastreams.dsma.conf.ConfigurationFactory;
import kr.co.datastreams.dsma.dic.trie.Trie;
import kr.co.datastreams.dsma.ma.WordEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Iterator;
import java.util.List;

/**
 * 사전
 *
 * User: shkim
 * Date: 13. 7. 18
 * Time: 오후 4:44
 *
 */
public class Dictionary {

    private static final Logger logger = LoggerFactory.getLogger(Dictionary.class);
    private static final Dictionary dictionary = new Dictionary();

    private final Trie<String, WordEntry> trie = new Trie<String, WordEntry>();

    private Dictionary() {
        Configuration conf = ConfigurationFactory.getConfiguration();
        load(conf.get(ConfKeys.DICTIONARY_FILE));
        load(conf.get(ConfKeys.EXTENSION_DIC));
        loadCompounds(conf.get(ConfKeys.COMPOUND_DIC));
    }

    private void load(String fileName) {
        StopWatch watch = StopWatch.create();
        watch.start();

        loadWithComposer(new DefaultWordEntryComposer(), fileName);

        watch.end();
        watch.println("dictionary "+ fileName + "loaded, ");
    }

    private void loadCompounds(String fileName) {
        StopWatch watch = StopWatch.create();
        watch.start();

        loadWithComposer(new CompoundWordEntryComposer(), fileName);

        watch.end();
        watch.println("dictionary "+ fileName + "loaded, ");
    }


    private void loadWithComposer(WordEntryComposer composer, String fileName) {
        List<String> lines = FileUtil.readLines(fileName, "UTF-8");
        List<WordEntry> entries = composer.compose(lines.toArray(new String[lines.size()]));
        addToTrie(entries);
    }

    private void addToTrie(List<WordEntry> entries) {
        for (WordEntry entry : entries) {
            trie.add(entry.getWord(), entry);
        }
    }

    private void logWrongFeaturedWords(String[] lines) {
        if (logger.isWarnEnabled()) {
            for (String each : lines) {
                logger.warn("Invalid Word information: {}", each);
            }
        }
    }


    public static WordEntry get(String word) {
        return dictionary.getWord(word);
    }

    private WordEntry getWord(String key) {
        return (WordEntry)trie.get(key);
    }

    public static void printDictionary(PrintWriter writer) {
        dictionary.trie.print(writer);
    }

    public static Iterator getPrefixedBy(String prefix) {
        return dictionary.trie.getPrefixedBy(prefix);
    }
}
