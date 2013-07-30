package kr.co.datastreams.dsma.ma.model;

import kr.co.datastreams.dsma.ma.model.WordEntry;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 7. 22
 * Time: 오전 10:36
 * To change this template use File | Settings | File Templates.
 */
public class CompoundWordEntry extends WordEntry {

    private final int offset;

    public CompoundWordEntry(String word, char[] features, int offset) {
        super(word, features);
        this.offset = offset;
    }

    public int getOffset() {
        return offset;
    }
}
