package kr.co.datastreams.dsma.ma.rule;

import kr.co.datastreams.dsma.dic.EomiDic;
import kr.co.datastreams.dsma.dic.SyllableDic;
import kr.co.datastreams.dsma.ma.model.Variant;

/**
 *
 * User: shkim
 * Date: 13. 7. 25
 * Time: 오전 10:47
 *
 * 중성이 'ㅏ/ㅓ/ㅔ/ㅐ' 이고
 * 종성이 없거나 모음으로 끝나는 음절 129개중 'ㅏ/ㅓ/ㅐ/ㅔ/ㅕ'로 끝나는 것이 선어말 어미 '-었-'과 결합할 때 생성되는 음절이고
 * '어'와 결합되는 어미
 */
public class EndingCombineWithEoi extends BaseEndingCombineRule {

    private char[] jungsungCheck = {'ㅏ','ㅓ', 'ㅔ', 'ㅐ'};
    public EndingCombineWithEoi(String stemCandidate, String endingCandidate) {
        super(stemCandidate, endingCandidate);
    }

    @Override
    public boolean canHandle() {
        if ( phonemes.cho == 'ㅇ') {
            return false;
        }

        if (phonemes.containsJungsung(jungsungCheck)) {
            // 모음으로 끝나는 음절 129개중 'ㅏ/ㅓ/ㅐ/ㅔ/ㅕ'로 끝나는 것이 선어말 어미 '-었-'과 결합할 때 생성되는 음절
            if (!phonemes.hasJong() || SyllableDic.getFeature(lastCharInStemPart)[SyllableDic.IDX_YNPAH] == '1') {
                if (EomiDic.findEndingWith('어', endingPart) != null) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Variant split() {
        if (!canHandle())
            return Variant.EMPTY;

        if (phonemes.hasJong()) {
            return Variant.createWithEnding(stemPart, endingPart);
        } else {
            return Variant.createWithEnding(stemPart, "어" + endingPart);
        }
    }
}
