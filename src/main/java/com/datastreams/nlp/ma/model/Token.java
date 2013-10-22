package com.datastreams.nlp.ma.model;

import com.datastreams.nlp.ma.constants.CharType;

import java.io.Serializable;

/**
 * 형태소 분석을 위한 토큰
 * 기본은 어절 단위
 *
 * User: shkim
 * Date: 13. 9. 16
 * Time: 오후 2:58
 *
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

    public static Token korean(String string) {
        return new Token(string, CharType.HANGUL, 0);
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

    public boolean equalsString(String str) {
        return this.string != null && str != null && this.string.equals(str);
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

    public int length() {
        return string.length();
    }

    public char charAt(int index) {
        return string.charAt(index);
    }

    public String substring(int beginIndex) {
        return string.substring(beginIndex);
    }

    public String substring(int beginIndex, int endIndex) {
        return string.substring(beginIndex, endIndex);
    }

    public int indexOf(String str) {
        return string.indexOf(str);
    }
}
