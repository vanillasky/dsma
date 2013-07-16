package kr.co.datastreams.dsma.dic.trie;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 7. 15
 * Time: 오후 5:13
 * To change this template use File | Settings | File Templates.
 */
public class NodeIterator extends UnmodifiableIterator {

    private ArrayList stack = new ArrayList();
    private boolean withNulls;

    public NodeIterator(TrieNode startNode, boolean withNulls) {
        this.withNulls = withNulls;
        if (withNulls || startNode.getValue() != null) {
            stack.add(startNode);
        } else {
            advance(startNode);
        }
    }


    @Override
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    @Override
    public Object next() {
        int size;
        if ((size = stack.size()) == 0) {
            throw new NoSuchElementException();
        }

        TrieNode node = (TrieNode)stack.remove(size-1);
        advance(node);
        return node;
    }

    private void advance(TrieNode node) {
        Iterator<TrieNode> children = node.childrentForward();
        while (true) {
            int size;
            if (children.hasNext()) {
                node = children.next();
                if (children.hasNext()) {
                    stack.add(children);
                }

                if (withNulls || node.getValue() == null) {
                    children = node.childrentForward();
                } else {
                    stack.add(node);
                    return;
                }
            } else if ((size = stack.size()) == 0) {
                return;
            }
            else {
                children = (Iterator)stack.remove(size -1);
            }
        }
    }
}
