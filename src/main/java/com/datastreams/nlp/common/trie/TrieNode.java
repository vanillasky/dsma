package com.datastreams.nlp.common.trie;

import kr.co.datastreams.commons.util.UnmodifiableIterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * User: shkim
 * Date: 13. 7. 15
 * Time: 오후 4:39
 *
 */
public final class TrieNode<V> {

    private V value = null;
    private final ArrayList<TrieEdge<V>> children = new ArrayList<TrieEdge<V>>(0);

    public TrieNode(V value) {
        this.value = value;
    }

    public TrieNode() {

    }

    public void setValue(V value) {
        this.value = value;
    }

    public V getValue() {
        return value;
    }


    public final TrieEdge<V> getChildAt(int index) {
        return children.get(index);
    }


    /**
     * Inserts an edge to this.
     * Insertion position will be determined by searching closest lower or equal one.
     * If there's not match, 1) Trie is empty or 2) the closest position is the last edge location
     *
     * @param label - the label to add
     * @param child - the child node for the edge to add
     */
    public void addChild(String label, TrieNode<V> child) {
        int index = closestFirstCharIndexOf(label.charAt(0));
        children.add(index + 1, new TrieEdge<V>(label, child));
    }

    /**
     * Removes the edge whose label starts with the given character.
     * @param c - the character to search
     * @return true if an edge removed.
     */
    public boolean remove(char c) {
        int index = startsWithIndexOf(c);
        if (index < 0) {
            return false;
        }
        children.remove(index);
        return true;
    }

    /**
     * Trims the capacity of this <tt>ArrayList</tt> instance to be the
     * list's current size.  An application can use this operation to minimize
     * the storage of an <tt>ArrayList</tt> instance.
     * This method should be invoked after numerous calls to add().
     */
    public void trim() {
        children.trimToSize();
    }

    public Iterator childrenForward() {
        return new ChildrenForwardIterator();
    }


    private class ChildrenForwardIterator extends UnmodifiableIterator {
        int index = 0;
        @Override
        public boolean hasNext() {
            return index < children.size();
        }

        @Override
        public Object next() {
            if (index < children.size()) {
                return getChildAt(index++).getChild();
            }
            throw new NoSuchElementException();
        }
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("TrieNode {").append(getValue() == null ? "NULL" : getValue().toString());
        buf.append(", children:").append(children.size());
        buf.append("}");
        return buf.toString();
    }

    public ArrayList<TrieEdge<V>> getChildren() {
        return children;
    }


    /**
     * Returns the edge whose label starts with the given character, or null if no such edge
     * @param c - the character to find
     *
     * @return TrieEdge whose label start with the given character, or null if no such edge
     */
    public TrieEdge<V> findChildWhoseLabelStartsWith(char c) {
        int index = startsWithIndexOf(c);
        return index < 0 ? null : getChildAt(index);
    }

    private int closestFirstCharIndexOf(char c) {
        return startsWithIndexOf(c, false);
    }

    private int startsWithIndexOf(char c) {
        return startsWithIndexOf(c, true);
    }


    private int startsWithIndexOf(char c, boolean exactMatch) {
        int low = 0;
        int high = children.size() - 1;

        while (low <= high) {
            int middle = (low + high) / 2;
            char midChar = getChildAt(middle).getFirstChar();
            if (midChar == c) {
                return middle;
            }

            if (midChar < c) {
                low = middle + 1;
            } else if (midChar > c) {
                high = middle - 1;
            }
        }

        if (exactMatch) {
            return -1;
        }

        return high;
    }

}
