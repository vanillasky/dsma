package kr.co.datastreams.dsma.ma.model;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 9. 16
 * Time: 오후 2:58
 * To change this template use File | Settings | File Templates.
 */
public class Token implements Serializable, Comparable<Token> {

    protected final int index;
    protected final String string;
    protected final CharType charType;

    protected Token() {
        this.string = null;
        this.index = 0;
        this.charType = CharType.ETC;
    }

    public Token(String string, CharType charType, int index) {
        this.string = string;
        this.charType = charType;
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public String getString() {
        return string;
    }

    public CharType getCharType() {
        return charType;
    }

    public boolean isCharTypeOf(CharType type) {
        return this.charType == type;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(").append(index).append(",").append(string).append(",").append(charType).append(")");
        return sb.toString();
    }

    @Override
    public Object clone() {
        return new Token(this.string, this.charType, this.index);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Token)) return false;
        Token o = (Token)other;
        return this.string != null && o.getString() != null && this.string.equals(o.getString());
    }

    @Override
    public int hashCode() {
        int hashCode = 1;
        int multiplier = 31;
        hashCode = multiplier * hashCode + (string == null ? 0 : string.hashCode());
        return hashCode;
    }


    @Override
    public int compareTo(Token o) {
        return this.index - o.index;
    }

    public Token copy() {
        return new Token(this.string, this.charType, this.index);
    }

}
