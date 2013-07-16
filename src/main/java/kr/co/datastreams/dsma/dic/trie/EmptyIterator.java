package kr.co.datastreams.dsma.dic.trie;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 7. 15
 * Time: 오후 5:12
 * To change this template use File | Settings | File Templates.
 */
public class EmptyIterator implements Iterator {
    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public Object next() {
        throw new NoSuchElementException();
    }

    @Override
    public void remove() {

    }
}
