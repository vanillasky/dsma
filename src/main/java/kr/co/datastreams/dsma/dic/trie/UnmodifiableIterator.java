package kr.co.datastreams.dsma.dic.trie;

import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 7. 15
 * Time: 오후 5:02
 * To change this template use File | Settings | File Templates.
 */
public abstract class UnmodifiableIterator implements Iterator {
    @Override
    public void remove() {
        throw new UnsupportedOperationException("Cannot remove from this iterator");
    }
}
