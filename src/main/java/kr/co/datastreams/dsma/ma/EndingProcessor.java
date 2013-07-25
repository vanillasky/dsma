package kr.co.datastreams.dsma.ma;

import kr.co.datastreams.commons.util.StringUtil;
import kr.co.datastreams.dsma.dic.Dictionary;
import kr.co.datastreams.dsma.dic.EomiDic;
import kr.co.datastreams.dsma.dic.SyllableDic;
import kr.co.datastreams.dsma.ma.rule.*;
import kr.co.datastreams.dsma.util.Hangul;

import java.util.List;

/**
 *
 * User: shkim
 * Date: 13. 7. 23
 * Time: 오후 1:59
 *
 */
public class EndingProcessor {

    /**
     * 어미를 분리한다.
     *
     * 1. 어간부가 받침 ㄴ/ㄹ/ㅁ/ㅂ 으로 끝나고 (ㄴ/ㄹ/ㅁ/ㅂ + 어미부)가 어미 사전에 어미로 등록되어 있으면 (ㄴ/ㄹ/ㅁ/ㅂ + 어미부)를 어미로 추정한다.
     * 2. 규칙용언과 어간만 변하는 불규칙 용언의 어미부가 사전에 있으면 어미로 추정
     * 3. '여/거라/너라'의 불규칙 어절
     * 4. 어미 '아/어'가 탈락되는 어절
     * 5. '아/어'의 변이체 분리
     *
     * @param stem - 문자열(어간후보)
     * @param ending - 어미후보
     * @return
     */
    public static Variant splitEnding(String stem, String ending) {
        List<EndingSplitRule> rules = RuleFactory.buildEndingSplitRules(stem, ending);
        Variant result = null;

        for (EndingSplitRule each : rules) {
            if (each.canHandle()) {
                result = each.split();
            }
        }

        if (result == null || result.isEmpty()) {
            if (ending.length() > 0 && EomiDic.exists(ending)) {
                result = Variant.create(stem, ending);
            }
        }

        return result == null ? Variant.EMPTY : result;
    }



    /**
     * 선어말어미 분석
     * @param stem
     * @return
     */
    public static String[] splitPrefinalEnding(String stem) {
        String[] result = new String[2];
        result[0] = stem;

        if (StringUtil.nvl(stem).length() == 0 || "있".equals(stem)) return result;

        char[] chars = stem.toCharArray();
        int len = chars.length;
        String pomi = "";
        int index = len - 1;

        Hangul phonemes = Hangul.split(chars[index]);
        if (chars[index] != '시' && chars[index] != 'ㅆ' && !phonemes.endsWith('ㅆ')) return result; // 선어말 어미 미발견


        if (chars[index] == '겠') {
            pomi = "겠";
            result[0] = stem;
            result[1] = pomi;

            if (--index <= 0 || (chars[index] != '시' && chars[index] != 'ㅆ' && !phonemes.endsWith('ㅆ'))) {
                return result;
            }
            phonemes = Hangul.split(chars[index]);
        }

        if (chars[index] == '었') { // 시었, ㅆ엇, 었
            pomi = chars[index] + pomi;
            result[0] = stem.substring(0, index);
            result[1] = pomi;
            if (--index <= 0 || (chars[index] != '시' && chars[index] != 'ㅆ' && !phonemes.endsWith('ㅆ'))) {
                return result;
            }
            phonemes = Hangul.split(chars[index]);
        }

        // [이/하]-였-었-겠
        if (chars[index] == '였') {
            pomi = Hangul.replaceFinal(chars[index], '어') + pomi;
            if (chars[index-1] == '하') {  // '-하였' 이면 '-하였'의 앞부분까지가 어간
                stem = stem.substring(0, index);
            } else {
                stem = stem.substring(0, index) + "이"; // '-이였'으로 처리
            }
            result[0] = stem;
            result[1] = pomi;
        } // [셨]-었-겠
        else if (chars[index] == '셨') {
            pomi = Hangul.replaceFinal(chars[index], '어') + pomi;
            stem = stem.substring(0, index);
            result[0] = stem;
            result[1] = "시" + pomi;
        } else if (chars[index] == '았' || chars[index] == '었') {
            pomi = chars[index] + pomi;
            result[0] = stem.substring(0, index);
            result[1] = pomi;
            if (--index <= 0 || (chars[index] != '시' && chars[index] != '으'))  return result;
        } else if (phonemes.hasJong() && phonemes.jong == 'ㅆ') {
            if(phonemes.cho == 'ㅎ' && phonemes.jung == 'ㅐ') {
                pomi = Hangul.replaceFinal(chars[index], '어')+pomi;
                stem = stem.substring(0,index)+"하";
            }else if(phonemes.cho!='ㅇ'&&(phonemes.jung=='ㅏ'||phonemes.jung=='ㅓ'||phonemes.jung=='ㅔ'||phonemes.jung=='ㅐ')) {
                pomi = "었"+pomi;
                stem = stem.substring(0,index) + Hangul.removeFinal(chars[index]);
            }else if(phonemes.cho!='ㅇ'&&(phonemes.jung=='ㅙ')) {
                pomi = "었"+pomi;
                stem = stem.substring(0,index)+Hangul.replaceMedial(chars[index], 'ㅚ');
            } else if(phonemes.jung=='ㅘ') {
                pomi = Hangul.replaceFinal(chars[index], '아')+pomi;
                stem = stem.substring(0,index)+Hangul.replaceMedial(chars[index], 'ㅗ');
            } else if(phonemes.jung=='ㅝ') {
                pomi = Hangul.replaceFinal(chars[index], '어')+pomi;
                stem = stem.substring(0,index)+Hangul.replaceMedial(chars[index], 'ㅜ');
            } else if(phonemes.jung=='ㅕ') {
                pomi = Hangul.replaceFinal(chars[index], '어')+pomi;
                stem = stem.substring(0,index) + Hangul.replaceMedial(chars[index], 'ㅣ');
            } else if(phonemes.jung=='ㅐ') {
                pomi = Hangul.replaceFinal(chars[index], '어')+pomi;
                stem = stem.substring(0,index);
            } else if(phonemes.jung=='ㅒ') {
                pomi = Hangul.replaceFinal(chars[index], '애')+pomi;
                stem = stem.substring(0,index);
            } else {
                pomi = "었"+pomi;
            }

            result[0] = stem;
            result[1] = pomi;

            if(chars[index] != '시' && chars[index] != '으') return result; // 다음이거나 선어말어미가 없다면...
            phonemes = Hangul.split(chars[index]);
        }

        Hangul nphonemes = null;
        char[] nChrs = null;
        if(index > 0) {
            nphonemes = Hangul.split(chars[index-1]);
            nChrs = new char[]{nphonemes.cho, nphonemes.jung, nphonemes.jong};
        }
        else nChrs = new char[2];

        if(nChrs.length==2&&chars[index]=='시'&&(chars.length<=index+1||
                (chars.length>index+1&&chars[index+1]!='셨'))) {
            if(Dictionary.get(result[0])!=null) return result;  //'시'가 포함된 단어가 있다. 성가시다/도시다/들쑤시다
            pomi = chars[index]+pomi;

            result[0] = stem.substring(0, index);
            result[1] = pomi;

            if(--index==0||chars[index]!='으') return result; // 다음이거나 선어말어미가 없다면...
            phonemes = Hangul.split(chars[index]);
        }

        if(index > 0) {
            nphonemes = Hangul.split(chars[index-1]);
            nChrs = new char[]{nphonemes.cho, nphonemes.jung, nphonemes.jong};
        }
        else nChrs = new char[2];

        if(chars.length>index+1&&nChrs.length==3&&(chars[index+1]=='셨'||chars[index+1]=='시')&&chars[index]=='으') {
            pomi = chars[index]+pomi;

            result[0] = stem.substring(0, index);
            result[1] = pomi;
        }

        return result;
    }
}
