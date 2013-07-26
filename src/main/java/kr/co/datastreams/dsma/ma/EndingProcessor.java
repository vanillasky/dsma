package kr.co.datastreams.dsma.ma;

import kr.co.datastreams.commons.util.StringUtil;
import kr.co.datastreams.dsma.dic.Dictionary;
import kr.co.datastreams.dsma.dic.EomiDic;
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
                result = Variant.createEnding(stem, ending);
            }
        }

        return result == null ? Variant.EMPTY : result;
    }



    /**
     * 선어말어미 분석<br/>
     * 결합관계:<br/>
     * 으 -> 시 -> 었 -> 었 -> 겠
     * 으 -> 시 -> 였 -> 었 -> 겠
     * 으 -> 시 -> 았 -> 었 -> 겠
     * 으 -> 시 -> ㅆ -> 었 -> 겠
     * 으 -> 셨 -> 었 -> 겠
     *
     * @param stem
     * @return
     */
    public static Variant splitPrefinalEnding(String stem) {
        Variant result = null;
        char[] chars = stem.toCharArray();
        int index = chars.length - 1;
        String pomi = "";

        if (StringUtil.nvl(stem).length() == 0 || "있".equals(stem)) return Variant.EMPTY;

        Hangul phonemes = Hangul.split(chars[index]);
        if (!Hangul.containsPrefinalEnding(chars[index])) return Variant.EMPTY; // 선어말 어미 미발견


        if (chars[index] == '겠') {
            pomi = "겠";
            if (!hasMorePrefinalElements(chars, index-1)) {
                return Variant.createPrefinal(stem.substring(0, index), pomi);
            }
            index--;
            phonemes = Hangul.split(chars[index]);
        }

        if (chars[index] == '었') {
            pomi = chars[index] + pomi;
            if (!hasMorePrefinalElements(chars, index-1)) {
                return Variant.createPrefinal(stem.substring(0, index), pomi);
            }
            index--;
            phonemes = Hangul.split(chars[index]);
        }

        // [였] -> 었
        if (chars[index] == '였') {
            //TODO 하였다 -> 하(V),었(f),다(e), 그냥 하였다로 하는게 나을 것 같은
            pomi = Hangul.replaceFinal(chars[index], '어') + pomi;
            if (chars[index-1] == '하') {  // 어간의 마지막이 '하' 이면 그대로 처리
                stem = stem.substring(0, index);
            } else { // 어간의 마지막이 '하'가 아니면 어간부에 '이'를 붙인다.
                stem = stem.substring(0, index) + "이";
            }
            result = Variant.createPrefinal(stem, pomi);
        } // [셨] -> 시었
        else if (chars[index] == '셨') {
            pomi = "시" + Hangul.replaceFinal(chars[index], '어') + pomi;
            stem = stem.substring(0, index);
            result = Variant.createPrefinal(stem, pomi);
        } // [았/었] -> 그대로 처리
        else if (chars[index] == '았' || chars[index] == '었') {
            pomi = chars[index] + pomi;
            result = Variant.createPrefinal(stem.substring(0, index), pomi);
            index--;
            if (index <= 0 || chars[index] != '시' && chars[index] != '으') {
                return result;
            }
            phonemes = Hangul.split(chars[index]);
        }
        else if (phonemes.hasJong() && phonemes.jong == 'ㅆ') {
            if(phonemes.cho == 'ㅎ' && phonemes.jung == 'ㅐ') {
                pomi = Hangul.replaceFinal(chars[index], '어') + pomi;
                stem = stem.substring(0,index)+"하";
            }else if(phonemes.cho!='ㅇ'&&(phonemes.jung=='ㅏ'||phonemes.jung=='ㅓ'||phonemes.jung=='ㅔ'||phonemes.jung=='ㅐ')) {
                pomi = "었" + pomi;
                stem = stem.substring(0,index) + Hangul.removeFinal(chars[index]);
            }else if(phonemes.cho!='ㅇ'&&(phonemes.jung=='ㅙ')) {
                pomi = "었" + pomi;
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

            result = Variant.createPrefinal(stem, pomi);
            if (chars[index] != '시' && chars[index] != '으') {
                return result;
            }
            phonemes = Hangul.split(chars[index]);
        }

        phonemes = Hangul.split(chars[index-1]);
        if(chars[index] == '시' && (chars.length <= index+1 || (chars.length > index+1 && chars[index+1] != '셨'))) {
            if(Dictionary.get(stem)!= null) {
                return Variant.EMPTY;  //'시'가 포함된 단어가 있다. 성가시다/도시다/들쑤시다
            }

            pomi = chars[index]+pomi;
            result = Variant.createPrefinal(stem.substring(0, index), pomi);
            index--;

            if(index == 0 || chars[index]!='으') {
                return result;
            }
            phonemes = Hangul.split(chars[index]);
        }

        phonemes = Hangul.split(chars[index-1]);
        if(chars.length > index+1 && phonemes.hasJong() && (chars[index+1] == '셨' || chars[index+1]=='시') &&chars[index]=='으') {
            pomi = chars[index]+pomi;
            result = Variant.createPrefinal(stem.substring(0, index), pomi);
        }

        return result == null ? Variant.EMPTY : result;
    }

    private static boolean hasMorePrefinalElements(char[] chars, int index) {
        return index > 0 && Hangul.containsPrefinalEnding(chars[index]);
    }
}
