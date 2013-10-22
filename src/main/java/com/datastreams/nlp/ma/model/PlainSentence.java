package com.datastreams.nlp.ma.model;

/**
 *
 * User: shkim
 * Date: 13. 9. 10
 * Time: 오후 5:38
 *
 */
public class PlainSentence implements Comparable {

    protected final String source;
    protected final int index;

    public PlainSentence(int index, String sentence) {
        this.index = index;
        this.source = sentence;
    }

    @Override
    public int compareTo(Object other) {
        if (other instanceof PlainSentence) {
            return index - ((PlainSentence)other).index;
        }
        return 0;
    }

    @Override
    public String toString() {
        return source;
    }

    public String getSource() {
        return source;
    }

    public int getIndex() {
        return index;
    }
}
