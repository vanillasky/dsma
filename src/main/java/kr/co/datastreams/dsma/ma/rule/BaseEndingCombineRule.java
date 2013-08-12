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
public abstract class BaseEndingCombineRule implements EndingCombineRule {

    protected String stemPart;
    protected String endingPart;
    protected Hangul phonemes;
    protected char lastCharInStemPart;

    public BaseEndingCombineRule(String stemPart, String endingPart) {
        this.stemPart = stemPart;
        this.endingPart = endingPart;
        lastCharInStemPart = stemPart.charAt(stemPart.length() - 1);
        phonemes = Hangul.split(lastCharInStemPart); //음소단위로 분리
    }

    protected int tailCutPos() {
        return stemPart.length() - 1;
    }

    protected String cutStemTail() {
        return stemPart.substring(0, tailCutPos());
    }

    abstract public boolean canHandle();
    abstract public Variant split();
}
