package kr.co.datastreams.dsma.ma.rule;

import kr.co.datastreams.dsma.ma.model.Variant;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 7. 25
 * Time: 오전 10:58
 *
 * '하여/거라/너라' 불규칙어미
 * 어간부의 마지막 음절과 어미부의 첫 부분이 각각 '하-' 와 '-여', '가-'와 '-거라', '오-'와 '-너라' 이면
 * 어미부를 어미로 추정한다.
 */
public class EndingSplitRuleVariantHayoeGeoraNeora extends BaseEndingRule {

    public EndingSplitRuleVariantHayoeGeoraNeora(String stemCandidate, String endingCandidate) {
        super(stemCandidate, endingCandidate);
    }

    @Override
    public boolean canHandle() {
        if (stemCandidate.endsWith("하") && "여".equals(endingCandidate)) {
            return true;
        }//TODO KoreanAnalyzer에 없는 부분 확인 필요
        else if (stemCandidate.endsWith("오") && "너라".equals(endingCandidate)) {
            return true;
        }//TODO KoreanAnalyzer에 없는 부분 확인 필요
        else if (stemCandidate.endsWith("가") && "거라".equals(endingCandidate)) {
            return true;
        }

        return false;
    }

    @Override
    public Variant split() {
        if (!canHandle())
            return Variant.EMPTY;

        if (stemCandidate.endsWith("하") && "여".equals(endingCandidate)) {
            return Variant.createEnding(stemCandidate, "어");
        }
        else if (stemCandidate.endsWith("오") && "너라".equals(endingCandidate)) {
            return Variant.createEnding(stemCandidate, "너라");
        }
        else if (stemCandidate.endsWith("가") && "거라".equals(endingCandidate)) {
            return Variant.createEnding(stemCandidate, "거라");
        }

        return Variant.EMPTY;
    }
}
