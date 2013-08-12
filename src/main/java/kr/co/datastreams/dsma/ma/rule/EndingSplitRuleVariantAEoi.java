package kr.co.datastreams.dsma.ma.rule;

import kr.co.datastreams.dsma.dic.EomiDic;
import kr.co.datastreams.dsma.ma.model.Variant;
import kr.co.datastreams.dsma.util.Hangul;

/**
 *
 * User: shkim
 * Date: 13. 7. 25
 * Time: 오전 9:51
 *
 * '아/어'로 시작되는 어미의 변이체
 * 어간부 끝음절의 중성이 'ㅘ/ㅝ/ㅕ/ㅐ/ㅔ/ㅒ/ㅙ' 이고
 * ('아/어'+어미부)가 사전에 어미로 등록되어 있으면 '아/어'의 변이체 어미로 추정한다.
 */
public class EndingSplitRuleVariantAEoi extends BaseEndingCombineRule {

    private char[] jungsungCheck = {'ㅘ','ㅝ', 'ㅕ', 'ㅐ', 'ㅔ', 'ㅒ', 'ㅙ'};

    public EndingSplitRuleVariantAEoi(String stemCandidate, String endingCandidate) {
        super(stemCandidate, endingCandidate);
    }

    @Override
    public boolean canHandle() {
        return phonemes.containsJungsung(jungsungCheck) ?
               (EomiDic.findEndingWith('어', endingPart) != null) : false;

    }

    @Override
    public Variant split() {
        if (!canHandle())
            return Variant.EMPTY;

        StringBuilder buf = new StringBuilder();

        buf.append(stemPart.substring(0, tailCutPos()));

        if (phonemes.jung == 'ㅘ') { // ㅘ 로 끝나면서 '어'와 결합이 가능하면 'ㅗ'+'아' 로 만든다. e.g.)봐 -> 보아
            buf.append(Hangul.replaceMedial(lastCharInStemPart, 'ㅗ'))
               .append(Hangul.replaceFinal(lastCharInStemPart, '아'));
        }
        else if (phonemes.jung == 'ㅝ') {
            buf.append(Hangul.replaceMedial(lastCharInStemPart, 'ㅜ'))
                    .append(Hangul.replaceFinal(lastCharInStemPart, '어'));
        }
        else if (phonemes.jung == 'ㅙ') {
            buf.append(Hangul.replaceMedial(lastCharInStemPart, 'ㅚ'))
                    .append(Hangul.replaceFinal(lastCharInStemPart, '어'));
        }
        else if (phonemes.jung == 'ㅕ') {
            buf.append(Hangul.replaceMedial(lastCharInStemPart, 'ㅣ'))
                    .append(Hangul.replaceFinal(lastCharInStemPart, '어'));
        }
        else if (phonemes.jung == 'ㅐ') {
            buf.append(Hangul.replaceMedial(lastCharInStemPart, 'ㅏ'))
                    .append(Hangul.replaceFinal(lastCharInStemPart, '어'));
        }
        else if (phonemes.jung == 'ㅒ') {
            buf.append(Hangul.replaceMedial(lastCharInStemPart, 'ㅣ'))
               .append(Hangul.replaceFinal(lastCharInStemPart, '어'));
        }

        String ending = buf.substring(buf.length()-1) + endingPart;
        return Variant.createEnding(buf.substring(0, buf.length() - 1), ending);
    }
}
