package kr.co.datastreams.dsma.dic.trie;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 7. 16
 * Time: 오후 4:40
 * To change this template use File | Settings | File Templates.
 */
public class Trie<S, V> {


    private TrieNode root = new TrieNode<V>();

    public V add(String key, V value) {
        TrieNode<V> currentNode = root;

        for (int charPos=0; charPos < key.length();) {
            TrieEdge<V> edge = currentNode.findChildWhoseLabelStartsWith(key.charAt(charPos));

            if (edge == null) {
                TrieNode<V> newNode = new TrieNode<V>(value);
                currentNode.addChild(key.substring(charPos), newNode);
                return null;
            } else {
                String label = edge.getLabel();
                currentNode = edge.getChild();
                charPos += label.length();
            }
        }

//        V result = currentNode.getValue();
//        currentNode.setValue(value);
//        return result;
        return null;
    }


    public TrieNode<V> get(String key) {
        TrieNode node = fetch(key);
        if (node == null)
            return null;

        return node;
    }

    private TrieNode<V> fetch(String prefix) {
        TrieNode<V> node = root;
        for (int i = 0; i < prefix.length();) {
            TrieEdge<V> edge = node.findChildWhoseLabelStartsWith(prefix.charAt(i));
            if (edge == null)
                return null;

            // Now check that rest of label matches.
            String label = edge.getLabel();
            int j = match(prefix, i, prefix.length(), label);
            if (j != -1)
                return null;

            i += label.length();
            node = edge.getChild();
        }
        return node;
    }

    public int match(String a, int startOffset, int stopOffset, String b) {
        // j is an index into b
        // i is a parallel index into a
        int i = startOffset;
        for (int j=0; j < b.length(); j++) {
            if (i >= stopOffset) {
                return j;
            }

            if (a.charAt(i) != b.charAt(j)) {
                return j;
            }
            i++;
        }

        return -1;
    }
}
