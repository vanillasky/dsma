package kr.co.datastreams.dsma.dic;

import kr.co.datastreams.commons.util.FileUtil;
import kr.co.datastreams.commons.util.StopWatch;
import kr.co.datastreams.commons.util.StringUtil;
import kr.co.datastreams.dsma.conf.ConfKeys;
import kr.co.datastreams.dsma.conf.Configuration;
import kr.co.datastreams.dsma.conf.ConfigurationFactory;
import kr.co.datastreams.dsma.dic.trie.Trie;
import kr.co.datastreams.dsma.ma.WordEntry;

import java.io.*;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 7. 18
 * Time: 오후 4:44
 * To change this template use File | Settings | File Templates.
 */
public class Dictionary {
    private static final Dictionary dictionary = new Dictionary();

    private final Trie<String, WordEntry> trie = new Trie<String, WordEntry>();

    private Dictionary() {
        Configuration conf = ConfigurationFactory.getConfiguration();
        load(conf.get(ConfKeys.DICTIONARY_FILE));
        load(conf.get(ConfKeys.EXTENSION_DIC));
        load(conf.get(ConfKeys.COMPOUND_DIC));
    }

    private void load(String fileName) {
        StopWatch watch = StopWatch.create();
        watch.start();
        List<String> lines = FileUtil.readLines(fileName, "UTF-8");
        String trimedLine = null;
        for (String line : lines) {
        trimedLine = line.trim();
            if (trimedLine.startsWith("//") || StringUtil.nvl(trimedLine).length() == 0) {
                continue;
            }

            String[] wordData = line.split(",");
            WordEntry morpheme = new WordEntry(wordData[0].trim());
            trie.add(morpheme.getWord(), morpheme);
        }

        watch.end();
        watch.println("dictionary "+ fileName + "loaded, ");
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
