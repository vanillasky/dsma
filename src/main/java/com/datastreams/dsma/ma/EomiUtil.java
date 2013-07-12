package com.datastreams.dsma.ma;

import com.datastreams.dsma.dic.Dictionary;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 6. 24
 * Time: 오후 3:55
 * To change this template use File | Settings | File Templates.
 */
public class EomiUtil {

    /**
     * 어미를 분리한다.
     *
     * 1. 규칙용언과 어간만 바뀌는 불규칙 용언
     * 2. 어미가 종성 'ㄴ/ㄹ/ㅁ/ㅂ'으로 시작되는 어절
     * 3. '여/거라/너라'의 불규칙 어절
     * 4. 어미 '아/어'가 탈락되는 어절
     * 5. '아/어'의 변이체 분리
     *
     * @param stem
     * @param end
     * @return
     */
    public static String[] splitEomi(String stem, String end) throws Exception {
        String[] strs = new String[2];
        int strlen = stem.length();
        if (strlen == 0) return strs;

        char endOfStem = stem.charAt(strlen-1);
        System.out.println("stem:" + stem);
        System.out.println("end of stem:" + endOfStem);


        char[] disassembledChars = MorphUtil.disassemble(endOfStem);
        System.out.println("disassembledChars[2]:"+disassembledChars[2]);

        if (disassembledChars == null)
            return strs;

        if ( (disassembledChars.length == 3) && (disassembledChars[2] == 'ㄴ' || disassembledChars[2] == 'ㅁ' || disassembledChars[2] == 'ㅂ') &&
                isNLMBSyl(endOfStem, disassembledChars[2]) && DictionaryUtil.combineAndEomiCheck(disassembledChars[2], end) != null) {
            System.out.println("======================");
            strs[0] = Character.toString(disassembledChars[2]);
            if (end.length() > 0) {
                strs[1] += end;
            }
            strs[0] = stem.substring(0, strlen-1) + MorphUtil.makeChar(endOfStem, 0);
        }

        return strs;
    }

    private static boolean isNLMBSyl(char endOfStem, char disassembledChar) throws Exception {
        char[] features = SyllableUtil.getFeature(endOfStem);

        switch (disassembledChar) {
            case 'ㄴ' :
                return (features[SyllableUtil.IDX_YNPNA]=='1' || features[SyllableUtil.IDX_YNPLN]=='1');
            case 'ㄹ' :
                return (features[SyllableUtil.IDX_YNPLA]=='1');
            case 'ㅁ' :
                return (features[SyllableUtil.IDX_YNPMA]=='1');
            case 'ㅂ' :
                return (features[SyllableUtil.IDX_YNPBA]=='1');
        }

        return false;
    }


}
