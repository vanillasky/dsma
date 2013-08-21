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
 *
 * User: shkim
 * Date: 13. 7. 23
 * Time: 오후 5:54
 *
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
        return instance.existsEnding(eomi);
    }

    private boolean existsEnding(String eomi) {
        return eomiMap.containsKey(eomi);
    }

    /**
     * 음소와 어미후보가 결합하여 어미가 될 수 있는지 점검하여(어미사전 등록 여부)
     * 가능하면 어미가 합쳐진 형태로 반환하고 아니면 null 을 반환한다.
     *
     * 음소가 'ㄴ/ㄹ/ㅁ/ㅂ'인 경우에는 다음과 같이 은/을/음/습을 앞에 붙여서 어미를 찾는다.<br/>
     * 그 외의 경우에는 음소+어미를 어미사전에서 찾는다.<br/>
     * '은'+어미(e.g. -은가),<br/>
     * '을'+어미(e.g. -을수록),<br/>
     * '음'+어미(e.g. -음에도),<br/>
     * '습'+어미(e.g. 습니다)
     *
     * @param phoneme - 음소
     * @param ending - 어미사전에 등록된 어미
     * @return
     */
    public static String findEndingWith(char phoneme, String ending) {
        ending = StringUtil.nvl(ending);

        // ㄴ,ㄹ,ㅁ,ㅂ과 어미후보 결합
        switch (phoneme) {
            case 'ㄴ' : ending = "은" + ending; break; // e.g.)-은가, -은다는데 등
            case 'ㄹ' : ending = "을" + ending; break; // e.g.)-을수록
            case 'ㅁ' : ending = "음" + ending; break; // -음에도
            case 'ㅂ' : ending = "습" + ending; break; // -습니다
            default : ending = phoneme + ending; break;
        }

        boolean exists = exists(ending);
        return exists ? ending : null;
    }

    public static String search(String str) {
        return instance.eomiMap.get(str);
    }
}
