package kr.co.datastreams.dsma.dic;

import kr.co.datastreams.commons.util.FileUtil;
import kr.co.datastreams.commons.util.StopWatch;
import kr.co.datastreams.commons.util.StringUtil;
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

    /**
     * 음소와 어미후보가 결합하여 어미가 될 수 있는지 점검하여(어미사전에 등록 여부)
     * 가능하면 어미가 합쳐진 형태로 반환하고 아니면 null 을 반환한다.
     *
     * @param phoneme - 음소
     * @param ending
     * @return
     */
    public static String combineEomiWith(char phoneme, String ending) {
        ending = StringUtil.nvl(ending);

        // ㄴ,ㄹ,ㅁ,ㅂ과 어미후보 결합
        switch (phoneme) {
            case 'ㄴ' : ending = "은" + ending; break;
            case 'ㄹ' : ending = "을" + ending; break;
            case 'ㅁ' : ending = "음" + ending; break;
            case 'ㅂ' : ending = "습" + ending; break;
            default : ending = phoneme + ending; break;
        }

        boolean exists = exists(ending);
        return exists ? ending : null;
    }

}
