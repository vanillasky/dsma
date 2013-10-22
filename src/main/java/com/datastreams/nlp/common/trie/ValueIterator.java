package com.datastreams.nlp.common.trie;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 7. 18
 * Time: 오후 1:51
 * To change this template use File | Settings | File Templates.
 */
public class ValueIterator extends NodeIterator {

    public ValueIterator(TrieNode node) {
        super(node);
    }

    public Object next() {
        return ((TrieNode)super.next()).getValue();
    }
}
