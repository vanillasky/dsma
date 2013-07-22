package kr.co.datastreams.dsma.ma;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 7. 22
 * Time: 오후 1:33
 * To change this template use File | Settings | File Templates.
 */
public class Token implements Serializable, Comparable {

    protected String string;
    protected CharType charType;
    protected int index;

    @Override
    public int compareTo(Object other) {
        if (other instanceof Token) {
            return index - ((Token)other).index;
        }
        return 0;
    }

    public Token() {
        this.string = null;
        this.charType = CharType.ETC;
        this.index = 0;
    }

    public Token(String string, CharType charType) {
        this(string, charType, 0);
    }

    public Token(String string, CharType charType, int index) {
        this.string = string;
        this.charType = charType;
        this.index = index;
    }


    public boolean equals(String other) {
        return string != null && other != null && other.equals(string);
    }

    public Token(Token token) {
        this(token.string, token.charType, token.index);
    }

    public Token copy() {
        Token copy = new Token(string, charType, index);
        return copy;
    }

    public CharType getCharType() {
        return charType;
    }

    public String getCharTypeName() {
        return charType.name();
    }


    public String toString() {
        StringBuilder sb  = new StringBuilder();
        sb.append("(").append(index).append(",").append(string).append(",").append(getCharTypeName()).append(")");
        return sb.toString();
    }

    public String getString() {
        return string;
    }

    public int getIndex() {
        return index;
    }
}
