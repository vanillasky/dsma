package kr.co.datastreams.dsma.ma.rule;

import kr.co.datastreams.dsma.dic.EomiDic;
import kr.co.datastreams.dsma.ma.model.Variant;
import kr.co.datastreams.dsma.util.Hangul;

/**
 *
 * User: shkim
 * Date: 13. 7. 24
 * Time: 오후 5:25
 *
 * 어간부가 받침 ㄴ/ㄹ/ㅁ/ㅂ 으로 끝나고 (ㄴ/ㄹ/ㅁ/ㅂ + 어미부)가 어미 사전에 어미로
 * 등록되어 있으면 (ㄴ/ㄹ/ㅁ/ㅂ + 어미부)를 어미로 추정한다.
 */
public class StartsWithNLMBEomiRule extends BaseEndingRule {

    public StartsWithNLMBEomiRule(String stemCandidate, String endingCandidate) {
        super(stemCandidate, endingCandidate);
    }


    @Override
    /**
     * 어간부가 받침 ㄴ/ㄹ/ㅁ/ㅂ 으로 끝나고 (ㄴ/ㄹ/ㅁ/ㅂ + 어미부)가 어미 사전에 어미로 등록되어 있으면 (ㄴ/ㄹ/ㅁ/ㅂ + 어미부)를 어미로 추정한다.
     */
    public boolean canHandle() {
        return phonemes.endsWithNLMB() // ㄴ/ㄹ/ㅁ/ㅂ 으로 끝나고
                && Hangul.isNLMBSyllable(endOfStemCandidate, phonemes.jong) // 음절 생성 규칙을 만족하고
                && EomiDic.findEndingWith(phonemes.jong, endingCandidate) != null; // 종성 + 어미부가 사전에 있으면
    }

    @Override
    public Variant split() {
        if (!canHandle())
            return Variant.EMPTY;

        String initial;
        String end;

        if (endingCandidate.length() > 0) {
            StringBuilder buf = new StringBuilder(Character.toString(phonemes.jong)); // 어간의 종성
            buf.append(endingCandidate);
            end = buf.toString();
        } else {
            end = Character.toString(phonemes.jong);
        }

        // 어간 + (종성이 제거된 어미 후보)
        initial = new StringBuilder(cutStemTail()).append(Hangul.removeFinal(endOfStemCandidate)).toString();

        return Variant.createEnding(initial, end);
    }

}
