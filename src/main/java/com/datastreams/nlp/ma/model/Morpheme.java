package com.datastreams.nlp.ma.model;


import com.datastreams.nlp.common.annotation.ThreadSafe;
import com.datastreams.nlp.ma.constants.CharType;
import com.datastreams.nlp.ma.constants.PosTag;

/**
 * 한 개의 형태소와 매핑되는 객체
 *
 * User: shkim
 * Date: 13. 9. 9
 * Time: 오후 4:25
 *
 */
@ThreadSafe
public class Morpheme {

    private final String source;
    private final long tagNum;
    private final CharType charType;

    public Morpheme(String string, long tagNum) {
        this.source = string;
        this.tagNum = tagNum;
        this.charType = CharType.HANGUL;
    }

    public Morpheme(Morpheme sourceObject) {
        this.source = sourceObject.getSource();
        this.tagNum = sourceObject.getTagNum();
        this.charType = sourceObject.charType;
    }

    public Morpheme(String string, String tag) {
        this(string, PosTag.getTagNum(tag));
    }

    public String toString() {
        return "<"+ source + "," + PosTag.getTag(tagNum) + ">";
    }

    public String getSource() {
        return source;
    }

    public long getTagNum() {
        return tagNum;
    }

    @Override
    public int hashCode() {
        int hashCode = 1;
        int multiplier = 31;
        hashCode = multiplier * hashCode + (source == null ? 0 : source.hashCode());
        hashCode = multiplier * hashCode + (int)tagNum;
        hashCode = multiplier * hashCode + charType.hashCode();

        return hashCode;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Morpheme)) return false;

        Morpheme o = (Morpheme)other;
        if (this.source != null && o.getSource() != null && this.source.equals(o.getSource())) {
            if (this.tagNum == o.getTagNum()) {
                return true;
            }
        }

        return false;

    }

    public boolean startsWith(String str) {
        return source.startsWith(str);
    }

    public boolean endsWith(String str) {
        return source.endsWith(str);
    }

    public int length() {
        return source.length();
    }

    public char charAt(int index) {
        return source.charAt(index);
    }

    public Morpheme copy() {
        return new Morpheme(this);
    }
}
