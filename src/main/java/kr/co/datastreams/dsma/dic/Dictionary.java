package kr.co.datastreams.dsma.dic;

import kr.co.datastreams.commons.util.FileUtil;
import kr.co.datastreams.commons.util.StopWatch;
import kr.co.datastreams.dsma.conf.ConfKeys;
import kr.co.datastreams.dsma.conf.Configuration;
import kr.co.datastreams.dsma.conf.ConfigurationFactory;
import kr.co.datastreams.dsma.dic.trie.Trie;
import kr.co.datastreams.dsma.ma.model.WordEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
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
    private static final String COMMENT_IDENTIFIER = "//";
    private static final Logger logger = LoggerFactory.getLogger(Dictionary.class);
    private static final Dictionary dictionary = new Dictionary();

    private final Trie<String, WordEntry> trie = new Trie<String, WordEntry>();
    private final HashSet<WordEntry> verbStemSet = new HashSet<WordEntry>();

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
//        List<WordEntry> entries = new ArrayList<WordEntry>();
        WordEntry entry;
        for (Iterator<String> iter = lines.iterator(); iter.hasNext(); ) {
            entry = composer.compose(iter.next());
            if (entry != null) {
                trie.add(entry.getString(), entry);
            }
        }

//        addToTrie(entries);
    }

    private void addToTrie(List<WordEntry> entries) {
        for (WordEntry entry : entries) {
            trie.add(entry.getString(), entry);
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


    /**
     * 사전에 수록된 단어를 찾아서 동사로 활용할 수 있으면 반환하고
     * 동사로 쓸 수 없으면 null을 반환한다.
     *
     * @param word - the word to find
     * @return WordEntry that is possible to use as verb.
     */
    public static WordEntry getVerb(String word) {
        WordEntry entry = get(word);
        if (entry == null) return null;

        if (entry.availableAsVerb()) return entry;

        return null;
    }


}
