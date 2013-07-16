package kr.co.datastreams.dsma.dic.trie;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 7. 15
 * Time: 오후 4:36
 * To change this template use File | Settings | File Templates.
 */
public final class TrieEdge<V> {

    private String label;
    private TrieNode<V> child;

    public TrieEdge(String label, TrieNode<V> child) {
        this.label = label;
        this.child = child;
    }

    public String getLabel() {
        return label;
    }

    public TrieNode<V> getChild() {
        return child;
    }



    /**
     * Returns the first character of the label
     * @return
     */
    public char getFirstChar() {
        return label.charAt(0);
    }

    public String toString() {
        return new StringBuilder("TrieEdge { label:").append(label).append(", child: ").append(child).append("}").toString();
    }

}
