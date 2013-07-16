package kr.co.datastreams.dsma.dic.trie;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 7. 15
 * Time: 오후 4:39
 * To change this template use File | Settings | File Templates.
 */
public final class TrieNode<V> {

    private V value = null;
    private ArrayList<TrieEdge<V>> children = new ArrayList<TrieEdge<V>>(0);

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

    private final int search(char c, boolean exact) {
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

        if (exact) {
            return -1;
        }
        return high; // return closest lower or equal match
    }



    public void addChild(String label, TrieNode<V> nodeForEgde) {
        int index = closestFirstCharIndexOf(label.charAt(0));
        children.add(index + 1, new TrieEdge<V>(label, nodeForEgde));
    }

    public boolean remove(char startsWith) {
        int index = search(startsWith, true);
        if (index < 0) {
            return false;
        }
        children.remove(index);
        return true;
    }

    public void trim() {
        children.trimToSize();
    }

    public Iterator childrentForward() {
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

    private class LabelForwardIterator extends UnmodifiableIterator {

        int index = 0;
        @Override
        public boolean hasNext() {
            return index < children.size();
        }

        @Override
        public Object next() {
            if (index < children.size()) {
                return getChildAt(index++).getLabel();
            }
            throw new NoSuchElementException();
        }
    }

    public Iterator labelsForward() {
        return new LabelForwardIterator();
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
     * @return
     */
    public TrieEdge<V> findChildWhoseLabelStartsWith(char c) {
        int index = firstCharIndexOf(c);
        return index < 0 ? null : getChildAt(index);
    }


    public final int firstCharIndexOf(char c) {
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

        return -1;
    }

    public final int closestFirstCharIndexOf(char c) {
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

        return high;
    }
}
