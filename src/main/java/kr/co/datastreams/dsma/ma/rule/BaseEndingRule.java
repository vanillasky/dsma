package kr.co.datastreams.dsma.ma.rule;

import kr.co.datastreams.dsma.ma.model.Variant;
import kr.co.datastreams.dsma.util.Hangul;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 7. 24
 * Time: 오후 5:26
 * To change this template use File | Settings | File Templates.
 */
public abstract class BaseEndingRule implements EndingSplitRule {

    protected String stemCandidate;
    protected String endingCandidate;
    protected Hangul phonemes;
    protected char endOfStemCandidate;

    public BaseEndingRule(String stemCandidate, String endingCandidate) {
        this.stemCandidate = stemCandidate;
        this.endingCandidate = endingCandidate;
        endOfStemCandidate = stemCandidate.charAt(stemCandidate.length() - 1);
        phonemes = Hangul.split(endOfStemCandidate); //음소단위로 분리
    }

    protected int tailCutPos() {
        return stemCandidate.length() - 1;
    }

    protected String cutStemTail() {
        return stemCandidate.substring(0, tailCutPos());
    }

    abstract public boolean canHandle();
    abstract public Variant split();
}
