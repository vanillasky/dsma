package com.datastreams.nlp.ma.dic;

import com.datastreams.nlp.ma.conf.ConfKeys;
import kr.co.datastreams.commons.util.FileUtil;
import kr.co.datastreams.commons.util.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * User: shkim
 * Date: 13. 10. 14
 * Time: 오후 3:10
 *
 */
public abstract class MapBaseDic {

    private static final Logger logger = LoggerFactory.getLogger("Dictionary");

    protected final Map<String, WordEntry> entries = new HashMap<String, WordEntry>();
    protected final WordEntryComposer wordEntryComposer = new PosTagComposer();

    protected void load(String fileName) {
        if (fileName == null || fileName.length() == 0) {
            throw new IllegalArgumentException("Dictionary file name should not be null.");
        }
        StopWatch watch = null;
        if (logger.isDebugEnabled()) {
            watch = StopWatch.create();
            watch.start();
        }

        synchronized (entries) {
            List<String> lines = FileUtil.readLines(fileName);
            WordEntry entry;
            for (Iterator<String> iter = lines.iterator(); iter.hasNext(); ) {
                entry = wordEntryComposer.compose(iter.next());
                if (entry != null) {
                    entries.put(entry.getString(), entry);
                }
            }
        }

        if (logger.isDebugEnabled()) {
            watch.end();
            watch.println("dictionary "+ fileName + " loaded, ");
        }
    }

    protected WordEntry search(String word) {
        return entries.get(word);
    }

    protected boolean exists(String word) {
        return entries.containsKey(word);
    }

    protected WordEntry searchWith(String word, long tagNum) {
        WordEntry entry = search(word);
        return entry == null ? null : entry.isTagOf(tagNum) ? entry : null;
    }
}
