package kr.co.datastreams.dsma.ma;

import kr.co.datastreams.commons.util.StringUtil;
import kr.co.datastreams.dsma.conf.ConfKeys;
import kr.co.datastreams.dsma.conf.ConfigurationFactory;
import kr.co.datastreams.dsma.dic.EomiDic;
import kr.co.datastreams.dsma.dic.SyllableDic;
import kr.co.datastreams.dsma.util.Hangul;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * User: shkim
 * Date: 13. 7. 23
 * Time: 오후 1:59
 *
 */
public class EomiProcessor {

    private static final Map<String, String> EOMIS = new HashMap<String, String>();

    static {
        load(ConfigurationFactory.getConfiguration().get(ConfKeys.EOMI_DIC));
    }

    private static void load(String filePath) {

    }

    /**
     * 어미를 분리한다.
     *
     * 1. 'ㄴ/ㄹ/ㅁ/ㅂ'으로 시작되는 어미
     * 2. 규칙용언과 어간만 바뀌는 불규칙 용언
     * 3. '여/거라/너라'의 불규칙 어절
     * 4. 어미 '아/어'가 탈락되는 어절
     * 5. '아/어'의 변이체 분리
     *
     * @param stemCandidate - 문자열(어간후보)
     * @param eomiCandidate - 어미후보
     * @return
     */
    public static String[] splitEomi(String stemCandidate, String eomiCandidate) {
        String[] result = new String[2];
        int strlen = stemCandidate.length();
        if(strlen==0) return result;

        char lastCharacter = stemCandidate.charAt(strlen-1);
        if (!Hangul.isHangul(lastCharacter)) {
            return result;
        }

        Hangul phonemes = Hangul.split(lastCharacter);

        // 어간부가 받침 ㄴ/ㄹ/ㅁ/ㅂ 으로 끝나고 (ㄴ/ㄹ/ㅁ/ㅂ + 어미부)가 문법형태소 사전에 어미로 등록되어 있으면 (ㄴ/ㄹ/ㅁ/ㅂ + 어미부)를 어미로 추정한다.
        if (phonemes.endsWithNLMB() && isNLMBSyllable(lastCharacter, phonemes.jong)) {
            if(combineEomiWith(phonemes.jong, eomiCandidate) != null) {
                result[1] = Character.toString(phonemes.jong);
                if (eomiCandidate.length() > 0) {
                    result[1] = result[1] + eomiCandidate;
                }
//                result[0] = stemCandidate.substring(0, strlen-1) + makeChar(lastCharacter, 0);
            }
        }
        
//        char[] chrs = MorphUtil.decompose(lastCharacter);
//
//        if (syllable.hasJong()) {
//
//        } else if (lastCharacter=='해' && DictionaryUtil.existEomi("어" + end)) {
//
//        } else if(lastCharacter=='히'&& DictionaryUtil.existEomi("이"+end)) {
//
//        } else if (syllable.cho != 'ㅇ' &&
//                  (syllable.jung == 'ㅏ' || syllable.jung == 'ㅓ' || syllable.jung == 'ㅔ' || syllable.jung == 'ㅐ') &&
//
//
//        if((chrs.length==3)&&(chrs[2]=='ㄴ'||chrs[2]=='ㄹ'||chrs[2]=='ㅁ'||chrs[2]=='ㅂ')&&
//                EomiUtil.IsNLMBSyl(lastCharacter,chrs[2])&&
//                DictionaryUtil.combineAndEomiCheck(chrs[2], end)!=null) {
//            strs[1] = Character.toString(chrs[2]);
//            if(end.length()>0) strs[1] += end;
//            strs[0] = string.substring(0,strlen-1) + MorphUtil.makeChar(lastCharacter, 0);
//        } else if(lastCharacter=='해'&&DictionaryUtil.existEomi("어" + end)) {
//            strs[0] = string.substring(0,strlen-1)+"하";
//            strs[1] = "어"+end;
//        } else if(lastCharacter=='히'&&DictionaryUtil.existEomi("이"+end)) {
//            strs[0] = string.substring(0,strlen-1)+"하";
//            strs[1] = "이"+end;
//        } else if(chrs[0]!='ㅇ'&&
//                (chrs[1]=='ㅏ'||chrs[1]=='ㅓ'||chrs[1]=='ㅔ'||chrs[1]=='ㅐ')&&
//                (chrs.length==2 || SyllableUtil.getFeature(lastCharacter)[SyllableUtil.IDX_YNPAH]=='1')&&
//                (DictionaryUtil.combineAndEomiCheck('어', end)!=null)) {
//
//            strs[0] = string;
//            if(chrs.length==2) strs[1] = "어"+end;
//            else strs[1] = end;
//        } else if(string.endsWith("하")&&"여".equals(end)) {
//            strs[0] = string;
//            strs[1] = "어";
//        }else if((chrs.length==2)&&(chrs[1]=='ㅘ'||chrs[1]=='ㅙ'||chrs[1]=='ㅝ'||chrs[1]=='ㅕ'||chrs[1]=='ㅐ'||chrs[1]=='ㅒ')&&
//                (DictionaryUtil.combineAndEomiCheck('어', end)!=null)) {
//
//            StringBuffer sb = new StringBuffer();
//
//            if(strlen>1) sb.append(string.substring(0,strlen-1));
//
//            if(chrs[1]=='ㅘ')
//                sb.append(MorphUtil.makeChar(lastCharacter, 8, 0)).append(MorphUtil.replaceJongsung('아',lastCharacter));
//            else if(chrs[1]=='ㅝ')
//                sb.append(MorphUtil.makeChar(lastCharacter, 13, 0)).append(MorphUtil.replaceJongsung('어',lastCharacter));
//            else if(chrs[1]=='ㅙ')
//                sb.append(MorphUtil.makeChar(lastCharacter, 11, 0)).append(MorphUtil.replaceJongsung('어',lastCharacter));
//            else if(chrs[1]=='ㅕ')
//                sb.append(Character.toString(MorphUtil.makeChar(lastCharacter, 20, 0))).append(MorphUtil.replaceJongsung('어',lastCharacter));
//            else if(chrs[1]=='ㅐ')
//                sb.append(MorphUtil.makeChar(lastCharacter, 0, 0)).append(MorphUtil.replaceJongsung('어',lastCharacter));
//            else if(chrs[1]=='ㅒ')
//                sb.append(MorphUtil.makeChar(lastCharacter, 20, 0)).append(MorphUtil.replaceJongsung('애',lastCharacter));
//
//
//            strs[0] = sb.toString();
//
//            end = strs[0].substring(strs[0].length()-1)+end;
//            strs[0] = strs[0].substring(0,strs[0].length()-1);
//
//            strs[1] = end;
//
//        }else if(!"".equals(end)&&DictionaryUtil.existEomi(end)) {
//            strs = new String[]{string, end};
//        }

        return result;
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
    private static boolean isNLMBSyllable(char ch, char phoneme) {

        char[] features = SyllableDic.getFeature(ch);

        switch(phoneme) {
            case 'ㄴ' :
                return (features[SyllableDic.IDX_YNPNA]=='1'      //(용언+'-ㄴ')에 의하여 생성되는 음절
                        || features[SyllableDic.IDX_YNPLN]=='1'); // 받침 'ㄹ'로 끝나는 용언이 어미 '-ㄴ'과 결합할 때 생성되는 음절
            case 'ㄹ' :
                return (features[SyllableDic.IDX_YNPLA]=='1'); // (용언+'-ㄹ')에 의해 생성되는 음절
            case 'ㅁ' :
                return (features[SyllableDic.IDX_YNPMA]=='1'); // (용언+'-ㅁ')에 의해 생성되는 음절
            case 'ㅂ' :
                return (features[SyllableDic.IDX_YNPBA]=='1'); // (용언+'-ㅂ')에 의해 생성되는 음절
        }

        return false;
    }

    /**
     * ㄴ,ㄹ,ㅁ,ㅂ과 어미후보가 결합하여 어미가 될 수 있는지 점검해서
     * 가능하면 어미가 합쳐진 형태로 반환하고 아니면 null 을 반환한다.
     * @param phoneme - 음소
     * @param eomiCandidate
     * @return
     */
    public static String combineEomiWith(char phoneme, String eomiCandidate) {
        String eomi = StringUtil.nvl(eomiCandidate);

        switch (phoneme) {
            case 'ㄴ' : eomi = "은" + eomi; break;
            case 'ㄹ' : eomi = "을" + eomi; break;
            case 'ㅁ' : eomi = "음" + eomi; break;
            case 'ㅂ' : eomi = "습" + eomi; break;
            default : eomi = phoneme + eomi; break;
        }

        boolean exists = EomiDic.exists(eomi);
        return exists ? eomi : null;
    }

}
