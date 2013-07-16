package kr.co.datastreams.dsma.dic.trie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 7. 15
 * Time: 오후 5:10
 * To change this template use File | Settings | File Templates.
 */
public class SampleTrie<S, V> {
    private static final Logger logger = LoggerFactory.getLogger(SampleTrie.class);
    private TrieNode<V> root;
    private boolean ignoreCase;
    private final static Iterator EMPTY_ITER =  new EmptyIterator();

    public SampleTrie(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
        clear();
    }

    public void clear() {
        this.root = new TrieNode<V>();
    }

    public String canonicalCase(final String str) {
        if (!ignoreCase)
            return str;
        return str.toUpperCase(Locale.US).toLowerCase(Locale.US);
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

    /**
     * Returns the node associated with prefix, or null if none. (internal)
     */
    private TrieNode<V> fetch(String prefix) {
        // This private method uses prefixes already in canonical form.
        TrieNode<V> node = root;
        for (int i = 0; i < prefix.length();) {
            // Find the edge whose label starts with prefix[i].
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

    public V add(String key, V value) {
        key = canonicalCase(key);
        TrieNode<V> currentNode = root;
//        System.out.println("1) start add method with (" + key + ", " + value +")");

        int i=0;
        while (i < key.length()) {
//            System.out.println("  Current node is " + node + ", location is " + i);
            // Find the edge whose label starts with the key[i].
            TrieEdge<V> edge = currentNode.findChildWhoseLabelStartsWith(key.charAt(i));

            if (edge == null) {
//                System.out.println("1-2) Edge starts with '"+key.charAt(i) + "' not found, create new TrieNode with "+ value);
                TrieNode<V> newNode = new TrieNode<V>(value);
                currentNode.addChild(key.substring(i), newNode);
//                logger.debug("1-3)Put edge key, value: {}, {} ", key.substring(i), newNode + " then return null");
                return null;
            }

            // Check that rest of label matches
            String label = edge.getLabel();
//            System.out.println("1-2) Edge found: "+ edge);
            int j = match(key, i, key.length(), label);
//            if (j >= 0) System.out.println("!!!=====> match result:"+ j + ", key="+ key + ",label="+label);
            if (j >= 0) {
                // 2) Prefix overlaps perfectly with just part of edge label
                // Do split insert as follows...
                //
                // node node ab = label
                // ab | ==> a | a = label[0...j - 1] (inclusive)
                // child intermediate b = label[j...] (inclusive)
                // b / \ c c = key[i + j...] (inclusive)
                // child newNode
                //
                // ...unless c = "", in which case you just do a "splice
                // insert" by ommiting newNew and setting intermediate's value.
                TrieNode<V> child = edge.getChild();
                TrieNode<V> intermediate = new TrieNode<V>();
                String a = label.substring(0, j);
                String b = label.substring(j);
                String c = key.substring(i + j);
//                logger.debug("\t a=label.substring(0, j)=> {}. b=label.substring(j)=> {}, c=key.substring(i + j)=> {}", a,b,c);
                if (c.length() > 0) {
                    //Split
                    TrieNode<V> newNode = new TrieNode<V>(value);
                    currentNode.remove(label.charAt(0));
                    currentNode.addChild(a, intermediate);
                    intermediate.addChild(b, child);
                    intermediate.addChild(c, newNode);
                } else {
                    // Splice.
                    currentNode.remove(label.charAt(0));
                    currentNode.addChild(a, intermediate);
                    intermediate.addChild(b, child);
                    intermediate.setValue(value);
                }

                return null;
            }

            // Prefix overlaps perfectly with all of edge label.
            // Keep searching.
            currentNode = edge.getChild();
            i += label.length();

        }

        // 3) Relabel insert. Prefix already in this, though not necessarily
        // associated with a value.
        V ret = currentNode.getValue();
        currentNode.setValue(value);
//        System.out.println("Return V is " + ret + ", invoke node.setValue(" + value + ")");
        return ret;
    }

    public Object get(String key) {
        // early conversion of search key
        key = canonicalCase(key);
        // search the node associated with key, if it exists
        TrieNode node = fetch(key);
        if (node == null)
            return null;
        // key exists, return the value

        return node.getValue();
    }

    public TrieNode<V> getRoot() {
        return root;
    }

    public void printNodes() {
        print(root, 0);
    }

    private void print(TrieNode<V> node, int depth) {
        String space = "";
        for (int i=0; i < depth;i++) {
            space += "  ";
        }

        //System.out.println(space + "" + node);
        for (Iterator iter=node.childrentForward(); iter.hasNext();) {
            TrieNode cnode = (TrieNode)iter.next();
            System.out.println(space + "" +cnode);

            if (iter.hasNext()) {
                if (cnode.getValue() == null) {
                    depth = 0;
                    System.out.println("==================================\n");
                }
                if (cnode.getChildren().size() > 0) {
                    print(cnode, ++depth);
                }

            }
        }
    }
}
