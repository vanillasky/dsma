package kr.co.datastreams.dsma.dic;

import kr.co.datastreams.commons.util.FileUtil;
import kr.co.datastreams.commons.util.StopWatch;
import kr.co.datastreams.dsma.conf.ConfKeys;
import kr.co.datastreams.dsma.conf.Configuration;
import kr.co.datastreams.dsma.conf.ConfigurationFactory;
import kr.co.datastreams.dsma.dic.trie.Trie;
import kr.co.datastreams.dsma.model.PosTag;
import kr.co.datastreams.dsma.model.WordEntry;
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
    private static final Dictionary DICTIONARY = new Dictionary();

    private final Trie<String, WordEntry> trie = new Trie<String, WordEntry>();
    private final WordEntryComposer wordEntryComposer = new PosTagComposer();

    private Dictionary() {
        Configuration conf = ConfigurationFactory.getConfiguration();
        load(conf.get(ConfKeys.DICTIONARY_FILE));
        load(conf.get(ConfKeys.EXTENSION_DIC));
        //loadCompounds(conf.get(ConfKeys.COMPOUND_DIC));
    }

    private void load(String fileName) {
        StopWatch watch = StopWatch.create();
        watch.start();

        loadWithComposer(wordEntryComposer, fileName);

        watch.end();
        if (logger.isDebugEnabled()) {
            watch.println("Dictionary "+ fileName + "loaded, ");
        }
    }

    private void loadCompounds(String fileName) {
        StopWatch watch = StopWatch.create();
        watch.start();

        loadWithComposer(new CompoundWordEntryComposer(), fileName);

        watch.end();
        if (logger.isDebugEnabled()) {
            watch.println("Dictionary "+ fileName + "loaded, ");
        }
    }


    private void loadWithComposer(WordEntryComposer composer, String fileName) {
        List<String> lines = FileUtil.readLines(fileName, "UTF-8");
        WordEntry entry;
        for (Iterator<String> iter = lines.iterator(); iter.hasNext(); ) {
            entry = composer.compose(iter.next());
            if (entry != null && entry.tag() > 0) {
                trie.add(entry.getString(), entry);
            }
        }
    }

    public static WordEntry get(String word) {
        return DICTIONARY.getWord(word);
    }

    private WordEntry getWord(String key) {
        return (WordEntry)trie.get(key);
    }

    public static void printDictionary(PrintWriter writer) {
        DICTIONARY.trie.print(writer);
    }

    public static Iterator getPrefixedBy(String prefix) {
        return DICTIONARY.trie.getPrefixedBy(prefix);
    }


    /**
     * 사전에 수록된 단어를 찾아서 용언으 활용할 수 있으면 반환하고
     * 동사로 쓸 수 없으면 null을 반환한다.
     *
     * @param word - the word to find
     * @return WordEntry that is possible to use as verb.
     */
    public static WordEntry getVerb(String word) {
        return findWithPosTag(word, PosTag.V);
    }


    /**
     * 사전에 수록된 단어를 찾아서 체언 활용할 수 있으면 반환하고
     * 동사로 쓸 수 없으면 null을 반환한다.
     *
     * @param word - the word to find
     * @return WordEntry that is possible to use as verb.
     */
    public static WordEntry getNoun(String word) {
        return findWithPosTag(word, PosTag.N);
    }

    public static WordEntry findWithPosTag(String word, long tagNum) {
        WordEntry entry = get(word);
        if (entry == null) return null;

        if (entry.isTagOf(tagNum)) return entry;

        return null;
    }

    public static WordEntry findEnding(String str) {
        return EomiDic.search(str);
    }

    public static WordEntry findJosa(String str) {
        return JosaDic.search(str);
    }
}
