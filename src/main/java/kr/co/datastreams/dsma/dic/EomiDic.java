package kr.co.datastreams.dsma.dic;

import kr.co.datastreams.commons.util.FileUtil;
import kr.co.datastreams.commons.util.StopWatch;
import kr.co.datastreams.dsma.conf.ConfKeys;
import kr.co.datastreams.dsma.conf.Configuration;
import kr.co.datastreams.dsma.conf.ConfigurationFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 7. 23
 * Time: 오후 5:54
 * To change this template use File | Settings | File Templates.
 */
public class EomiDic implements ConfKeys {

    private static final EomiDic instance = new EomiDic();
    private final Map<String, String> eomiMap = new HashMap<String, String>();

    private EomiDic() {
        Configuration conf = ConfigurationFactory.getConfiguration();
        load(conf.get(EOMI_DIC));
    }

    private void load(String fileName) {
        synchronized (eomiMap) {
            StopWatch watch = StopWatch.create();
            watch.start();

            List<String> lines = FileUtil.readLines(fileName);
            for (String line : lines) {
                if (line.trim().length() == 0 || line.trim().startsWith("//")) {
                    continue;
                }

                eomiMap.put(line.trim(), line);
            }

            watch.end();
            watch.println("dictionary "+ fileName + "loaded, ");
        }
    }

    public static boolean exists(String eomi) {
        return instance.existsEomi(eomi);
    }

    private boolean existsEomi(String eomi) {
        return eomiMap.containsKey(eomi);
    }

}
