package com.datastreams.nlp.ma.dic;

import com.datastreams.nlp.common.conf.Configuration;
import com.datastreams.nlp.ma.conf.ConfKeys;
import com.datastreams.nlp.ma.conf.Config;
import kr.co.datastreams.commons.util.FileUtil;
import kr.co.datastreams.commons.util.StopWatch;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 음절 사전
 *
 * User: shkim
 * Date: 13. 7. 23
 * Time: 오후 3:54
 *
 */
public class SyllableDic implements ConfKeys, SyllableFeatureIndex {

    private final List<byte[]> syllables = Collections.synchronizedList(new ArrayList<byte[]>());  // 음절특성 정보
    private static final SyllableDic instance = new SyllableDic();

    private SyllableDic() {
        Configuration conf = Config.get();
        load(conf.get(KOREAN_SYLLABLE_DIC));
    }

    private void load(String fileName) {
        synchronized (syllables) {
            List<String> lines = FileUtil.readLines(fileName);
            for (String line : lines) {
                if (line.trim().length() > 0) {
                    syllables.add(toByteArray(line.split(" //")[0]));
                }
            }
        }
    }

    private byte[] toByteArray(String feature) {
        char[] features = feature.toCharArray();
        byte[] bytes = new byte[features.length];
        for (int i=0;i < bytes.length; i++) {
            bytes[i] = (byte)Integer.parseInt(features[i]+"");
        }
        return bytes;
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
    public static boolean isVerbEndsWithNLMBSyllable(char ch, char phoneme) {

        byte[] features = getFeature(ch);

        switch(phoneme) {
            case 'ㄴ' :
                return (features[IDX_YNPNA]== 1      //(용언+'-ㄴ')에 의하여 생성되는 음절(e.g. 간)
                        || features[IDX_YNPLN]==1); // 받침 'ㄹ'로 끝나는 용언이 어미 '-ㄴ'과 결합할 때 생성되는 음절 (끌다 -> 끈)
            case 'ㄹ' :
                return (features[IDX_YNPLA]==1); // (용언+'-ㄹ')에 의해 생성되는 음절(갈,널 등)
            case 'ㅁ' :
                return (features[IDX_YNPMA]==1); // (용언+'-ㅁ')에 의해 생성되는 음절(감,댐 등)
            case 'ㅂ' :
                return (features[IDX_YNPBA]==1); // (용언+'-ㅂ')에 의해 생성되는 음절(갑,넙 등)
        }

        return false;
    }


    /**
     * 음절의 특성을 반환한다.
     *
     * @param syllable - 음절
     * @return 음절정보를 담고있는 char[]
     */
    public static byte[] getFeature(char syllable) {
        int index = syllable - 0xAC00;
        return instance.getFeature(index);
    }


    /**
     * 음절의 특성정보를 반환한다.
     * @param index - '가'(0xAC00)로부터의 offset
     * @return 음절정보를 담고있는 char[]
     */
    private byte[] getFeature(int index) {
        if (index < 0 || index >= syllables.size()) {
            return null;
        }
        return syllables.get(index);
    }

    /**
     * 조사의 첫음절로 사용되는 음절 48개에 속하는지 확인한다.
     *
     * @param ch
     * @return
     */
    public static boolean isFirstJosaSyllable(char ch) {
        byte[] features = getFeature(ch);
        return features == null ? false : features[IDX_JOSA1] == 1;
    }

    /**
     * 조사의 두 번째 이상의 음절로 사용되는 음절 58개에 속하는지 확인한다.
     * @return
     */
    public static boolean isSecondJosaSyllable(char ch) {
        byte[] features = getFeature(ch);
        return features == null ? false : features[IDX_JOSA2] == 1;
    }

    /**
     * 용언의 표층형으로만 사용되는 음절을 가지고 있는지 확인한다.
     *
     * @param word - the word
     * @return true if the word has syllable that used only verb in part of speech.
     */
    public static boolean hasOnlyVerbSurfaceSyllable(String word) {
        for (int i=word.length()-1; i >= 0; i--) {
            byte[] features = getFeature(word.charAt(i));
            if (features == null) return false;

            if (features[IDX_WDSURF] == 1) {
                return true;
            }
        }
        return false;
    }

    /**
     * 어미의 두 번째 이상의 음절로 사용되는 음절 105개에 속하는지 확인한다.
     *
     * @param ch
     * @return
     */
    public static boolean isSecondEndingSyllable(char ch) {
        byte[] features = getFeature(ch);
        return features == null ? false : features[IDX_EOMI2] == 1;
    }

}
