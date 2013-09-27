package kr.co.datastreams.dsma.dic;

import kr.co.datastreams.commons.util.FileUtil;
import kr.co.datastreams.commons.util.StopWatch;
import kr.co.datastreams.dsma.conf.ConfKeys;
import kr.co.datastreams.dsma.conf.Configuration;
import kr.co.datastreams.dsma.conf.ConfigurationFactory;
import kr.co.datastreams.dsma.ma.model.WordEntry;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 8. 21
 * Time: 오전 8:54
 * To change this template use File | Settings | File Templates.
 */
public class JosaDic implements ConfKeys {

    private static final JosaDic instance = new JosaDic();
    private final Map<String, WordEntry> josaMap = new HashMap<String, WordEntry>();
    private final WordEntryComposer wordEntryComposer = new PosTagComposer();

    private JosaDic() {
        Configuration conf = ConfigurationFactory.getConfiguration();
        load(conf.get(JOSA_DIC));
    }

    private void load(String fileName) {
        synchronized (josaMap) {
            StopWatch watch = StopWatch.create();
            watch.start();

            List<String> lines = FileUtil.readLines(fileName);
            WordEntry entry;
            for (Iterator<String> iter = lines.iterator(); iter.hasNext(); ) {
                entry = wordEntryComposer.compose(iter.next());
                if (entry != null) {
                    josaMap.put(entry.getString(), entry);
                }
            }


            watch.end();
            watch.println("dictionary "+ fileName + "loaded, ");
        }
    }

    public static boolean exists(String josa) {
        return instance.existsJosa(josa);
    }

    public static String search(String word) {
        WordEntry entry = instance.josaMap.get(word);
        return entry == null ? null : entry.getString();
    }

    private boolean existsJosa(String josa) {
        return josaMap.containsKey(josa);
    }


}
