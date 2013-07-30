package kr.co.datastreams.dsma.dic;

import kr.co.datastreams.commons.util.FileUtil;
import kr.co.datastreams.commons.util.StopWatch;
import kr.co.datastreams.dsma.conf.ConfKeys;
import kr.co.datastreams.dsma.conf.Configuration;
import kr.co.datastreams.dsma.conf.ConfigurationFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 7. 23
 * Time: 오후 3:54
 * To change this template use File | Settings | File Templates.
 */
public class SyllableDic implements ConfKeys {

    public static int IDX_JOSA1 = 0; // 조사의 첫음절로 사용되는 음절 48개
    public static int IDX_JOSA2 = 1; // 조사의 두 번째 이상의 음절로 사용되는 음절 58개
    public static int IDX_EOMI1 = 2; // 어미의 첫음절로 사용되는 음절 72개
    public static int IDX_EOMI2 = 3; // 어미의 두 번째 이상의 음절로 사용되는 음절 105개
    public static int IDX_YONG1 = 4; // 1음절 용언에 사용되는 음절 362개
    public static int IDX_YONG2 = 5; // 2음절 용언의 마지막 음절로 사용되는 음절 316개
    public static int IDX_YONG3 = 6; // 3음절 이상 용언의 마지막 음절로 사용되는 음절 195개
    public static int IDX_CHEON1 = 7; // 1음절 체언에 사용되는 음절 680개
    public static int IDX_CHEON2 = 8; // 2음절 체언의 마지막 음절로 사용되는 음절 916개
    public static int IDX_CHEON3 = 9; // 3음절 체언의 마지막 음절로 사용되는 음절 800개
    public static int IDX_CHEON4 = 10; // 4음절 체언의 마지막 음절로 사용되는 음절 610개
    public static int IDX_CHEON5 = 11; // 5음절 이상 체언의 마지막 음절로 사용되는 음절 330개
    public static int IDX_BUSA1 = 12; // 1음절 부사의 마지막 음절로 사용되는 음절 191개
    public static int IDX_BUSA2 = 13; // 2음절 부사의 마지막 음절로 사용되는 음절 519개
    public static int IDX_BUSA3 = 14; // 3음절 부사의 마지막 음절로 사용되는 음절 139개
    public static int IDX_BUSA4 = 15; // 4음절 부사의 마지막 음절로 사용되는 음절 366개
    public static int IDX_BUSA5 = 16; // 5음절 부사의 마지막 음절로 사용되는 음절 79개
    public static int IDX_PRONOUN = 17; // 대명사의 마지막 음절로 사용되는 음절 77개
    public static int IDX_EXCLAM = 18; // 관형사와 감탄사의 마지막 음절로 사용되는 음절 241개

    public static int IDX_YNPNA = 19; // (용언+'-ㄴ')에 의하여 생성되는 음절 129개
    public static int IDX_YNPLA = 20; // (용언+'-ㄹ')에 의해 생성되는 음절 129개
    public static int IDX_YNPMA = 21; // (용언+'-ㅁ')에 의해 생성되는 음절 129개
    public static int IDX_YNPBA = 22; // (용언+'-ㅂ')에 의해 생성되는 음절 129개
    public static int IDX_YNPAH = 23; // 모음으로 끝나는 음절 129개중 'ㅏ/ㅓ/ㅐ/ㅔ/ㅕ'로 끝나는 것이 선어말 어미 '-었-'과 결합할 때 생성되는 음절
    public static int IDX_YNPOU = 24; // 모음 'ㅗ/ㅜ'로 끝나는 음절이 '아/어'로 시작되는 어미나 선어말 어미 '-었-'과 결합할 때 생성되는 음절
    public static int IDX_YNPEI = 25; // 모음 'ㅣ'로 끝나는 용언이 '아/어'로 시작되는 어미나 선어말 어미 '-었-'과 결합할 때 생성되는 음절
    public static int IDX_YNPOI = 26; // 모음 'ㅚ'로 끝나는 용언이 '아/어'로 시작되는 어미나 선어말 어미 '-었-'과 결합할 때 생성되는 음절
    public static int IDX_YNPLN = 27; // 받침 'ㄹ'로 끝나는 용언이 어미 '-ㄴ'과 결합할 때 생성되는 음절
    public static int IDX_IRRLO = 28; // '러' 불규칙(8개)에 의하여 생성되는 음절 : 러, 렀
    public static int IDX_IRRPLE = 29; // '르' 불규칙(193개)에 의하여 생성되는 음절
    public static int IDX_IRROO = 30; // '우' 불규칙에 의하여 생성되는 음절 : 퍼, 펐
    public static int IDX_IRROU = 31; // '어' 불규칙에 의하여 생성되는 음절 : 해, 했
    public static int IDX_IRRDA = 32; // 'ㄷ' 불규칙(37개)에 의하여 생성되는 음절
    public static int IDX_IRRBA = 33; // 'ㅂ' 불규칙(446개)에 의하여 생성되는 음절
    public static int IDX_IRRSA = 34; // 'ㅅ' 불규칙(39개)에 의하여 생성되는 음절
    public static int IDX_IRRHA = 35; // 'ㅎ' 불규칙(96개)에 의하여 생성되는 음절
    public static int IDX_PEND = 36; // 선어말 어미 : 시 셨 았 었 였 겠

    public static int IDX_YNPEOMI = 37; // 용언이 어미와 결합할 때 생성되는 음절의 수 734개

    /**	용언의 표층 형태로만 사용되는 음절 */
    public static int IDX_WDSURF = 38;
    public static int IDX_EOGAN = 39; // 어미 또는 어미의 변형으로 존재할 수 있는 음 (즉 IDX_EOMI 이거나 IDX_YNPNA 이후에 1이 있는 음절)

    private final List<char[]> syllables = Collections.synchronizedList(new ArrayList<char[]>());  // 음절특성 정보
    private static final SyllableDic instance = new SyllableDic();

    private SyllableDic() {
        Configuration conf = ConfigurationFactory.getConfiguration();
        load(conf.get(SYLLABLE_DIC));
    }



    private void load(String fileName) {
        synchronized (syllables) {
            StopWatch watch = StopWatch.create();
            watch.start();

            List<String> lines = FileUtil.readLines(fileName);
            for (String line : lines) {
                if (line.trim().length() == 0 || line.trim().startsWith("//")) {
                    continue;
                }

//                syllables.add(line.toCharArray());
                syllables.add(line.split(" //")[0].toCharArray());
            }

            watch.end();
            watch.println("dictionary "+ fileName + "loaded, ");
        }
    }

    /**
     * 음절의 특성을 반환한다.
     *
     * @param syllable - 음절
     * @return 음절정보를 담고있는 char[]
     */
    public static char[] getFeature(char syllable) {
        int index = syllable - 0xAC00;
        return instance.getFeature(index);
    }


    public static byte[] getFeatureInByte(char syllable) {
        int index = syllable - 0xAC00;
        char[] features =  instance.getFeature(index);
        byte[] bytes = new byte[features.length];
        for (int i=0;i < bytes.length; i++) {
            bytes[i] = (byte)Integer.parseInt(features[i]+"");
        }
        return bytes;
    }


    /**
     * 음절의 특성정보를 반환한다.
     * @param index - '가'(0xAC00)로부터의 offset
     * @return 음절정보를 담고있는 char[]
     */
    private char[] getFeature(int index) {
        if (index < 0 || index >= syllables.size()) {
            return null;
        }
        return syllables.get(index);
    }

    /**
     * 다음 조건에 부합하면 true, 아니면 false 를 반환<br/>
     * 음소가<br/>
     *  'ㄴ' -> (용언+'-ㄴ')에 의하여 생성되는 음절 or 받침 'ㄹ'로 끝나는 용언이 어미 '-ㄴ'과 결합할 때 생성되는 음절 <br/>
     *  'ㄹ' -> (용언+'-ㄹ')에 의해 생성되는 음절<br/>
     *  'ㅁ' -> (용언+'-ㅁ')에 의해 생성되는 음절<br/>
     *  'ㅂ' -> (용언+'-ㅂ')에 의해 생성되는 음절<br/>
     * @param ch - 한 개의 음절
     * @param phoneme - 음소(ㄴ/ㄹ/ㅁ/ㅂ)
     * @return
     */
    public static boolean isNLMBSyllable(char ch, char phoneme) {

        char[] features = getFeature(ch);

        switch(phoneme) {
            case 'ㄴ' :
                return (features[IDX_YNPNA]=='1'      //(용언+'-ㄴ')에 의하여 생성되는 음절(e.g. 간)
                        || features[IDX_YNPLN]=='1'); // 받침 'ㄹ'로 끝나는 용언이 어미 '-ㄴ'과 결합할 때 생성되는 음절 (끌다 -> 끈)
            case 'ㄹ' :
                return (features[IDX_YNPLA]=='1'); // (용언+'-ㄹ')에 의해 생성되는 음절(갈,널 등)
            case 'ㅁ' :
                return (features[IDX_YNPMA]=='1'); // (용언+'-ㅁ')에 의해 생성되는 음절(감,댐 등)
            case 'ㅂ' :
                return (features[IDX_YNPBA]=='1'); // (용언+'-ㅂ')에 의해 생성되는 음절(갑,넙 등)
        }

        return false;
    }
}
