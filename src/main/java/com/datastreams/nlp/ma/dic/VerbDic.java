package com.datastreams.nlp.ma.dic;

import com.datastreams.nlp.common.conf.Configuration;
import com.datastreams.nlp.ma.conf.ConfKeys;
import com.datastreams.nlp.ma.conf.Config;
import kr.co.datastreams.commons.util.FileUtil;
import kr.co.datastreams.commons.util.StopWatch;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

/**
 * 용언 사전
 * 동사, 형용사, 보조용언을 수록한다.
 *
 *
 * User: shkim
 * Date: 13. 10. 16
 * Time: 오후 4:51
 * To change this template use File | Settings | File Templates.
 */
final class VerbDic extends TrieBaseDic implements ConfKeys {

    private static final VerbDic INSTANCE = new VerbDic();
    private final Configuration conf = Config.get();

    private VerbDic() {
        super(new PosTagComposer());
        if (conf.getBoolean(USE_VERB_EXPANSION)) {
            expand(conf.get(KOREAN_VERB_DIC));
        } else {
            load(conf.get(KOREAN_VERB_DIC));
        }
    }


    protected void expand(String fileName) {
        if (fileName == null || fileName.length() == 0) {
            throw new IllegalArgumentException("Dictionary file name should not be null.");
        }
        StopWatch watch = null;
        if (logger.isDebugEnabled()) {
            watch = StopWatch.create();
            watch.start();
        }

        List<String> lines = FileUtil.readLines(fileName, "UTF-8");

        WordEntry entry;
        for (Iterator<String> iter = lines.iterator(); iter.hasNext(); ) {
            entry = wordEntryComposer.compose(iter.next());
            if (entry != null && entry.tag() > 0) {
                trie.add(entry.getString(), entry);
                expandWord(entry);
            }
        }

        if (logger.isDebugEnabled()) {
            watch.end();
            watch.println("dictionary "+ fileName + " loaded, ");
        }
    }

    private void expandWord(WordEntry wordEntry) {
        List<WordEntry> expandedEntries = VerbDicExpander.expand(wordEntry);

        for (Iterator<WordEntry> iter = expandedEntries.iterator(); iter.hasNext();) {
            WordEntry entry = iter.next();
            trie.add(entry.getString(), entry);
        }
    }


    public static TrieBaseDic getInstance() {
        return INSTANCE;
    }
}
