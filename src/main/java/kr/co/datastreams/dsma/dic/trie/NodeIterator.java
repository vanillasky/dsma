package kr.co.datastreams.dsma.dic.trie;

import kr.co.datastreams.commons.util.UnmodifiableIterator;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 7. 18
 * Time: 오전 11:10
 * To change this template use File | Settings | File Templates.
 */
public class NodeIterator extends UnmodifiableIterator {

    private final ArrayList stack = new ArrayList();
//    private final ArrayList<TrieNode> stack = new ArrayList<TrieNode>();


    public NodeIterator(TrieNode node) {
        scan(node);
    }


    public boolean hasNext() {
        return !stack.isEmpty();
    }


    public Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        TrieNode node = (TrieNode)stack.remove(0);
        scan(node);
        return node;
    }


    private void scan(TrieNode node) {
        List<TrieEdge> edges = node.getChildren();

        for (TrieEdge each : edges) {
            TrieNode cnode = each.getChild();
            stack.add(cnode);
        }
    }

}
