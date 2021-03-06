package kr.co.datastreams.dsma.model;

import kr.co.datastreams.dsma.annotation.ThreadSafe;

/**
 *
 * User: shkim
 * Date: 13. 9. 9
 * Time: 오후 4:25
 *
 */
@ThreadSafe
public class Morpheme {

    private final String string;
    private final long tagNum;
    private final CharType charType;

    public Morpheme(String string, long tagNum) {
        this.string = string;
        this.tagNum = tagNum;
        this.charType = CharType.HANGUL;
    }

    public Morpheme(String string, String tag) {
        this(string, PosTag.getTagNum(tag));
    }

    public String toString() {
        return "<"+string + "," + PosTag.getTag(tagNum) + ">";
    }

}
