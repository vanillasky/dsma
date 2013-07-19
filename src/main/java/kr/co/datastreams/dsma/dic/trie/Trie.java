package kr.co.datastreams.dsma.dic.trie;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 7. 16
 * Time: 오후 4:40
 * To change this template use File | Settings | File Templates.
 */
public class Trie<S, V> {

    private final TrieNode root = new TrieNode<V>();


    /**
     * Maps the given key to the given value.
     * Find TrieEgde which starts with character(key.charAt(pos)) from the node(starts with root).
     * If there's no TrieEdge whose label starts with the character, creates new TrieNode with given value and add to the node.
     * else adjust the position for the next step and swap working node with the child of found edge.
     *
     */
    public void add(String key, V value) {
        TrieNode<V> currentNode = root;

        for (int charPos=0; charPos < key.length();) {
            TrieEdge<V> edge = currentNode.findChildWhoseLabelStartsWith(key.charAt(charPos));

            if (edge == null) {
                currentNode.addChild(key.substring(charPos), new TrieNode(value));
                return;
            }

            String label = edge.getLabel();
            int matchResult = findOverlapedPrefix(key, edge.getLabel(), charPos, key.length());
            if (matchResult >= 0) {
                //System.out.println("overlaped prefix found:"+ key + "," + edge.getLabel() + ": " + matchResult);
                // key의 나머지 부분을 처리한다.
                // key의 일부(prefix)가 기존의 label과 매칭되는지 확인해서 prefix가 일치하는 부분이 있으면 쪼개거나 연결한다.

                TrieNode<V> child = edge.getChild();
                String prefix = label.substring(0, matchResult);
                String previousSuffix = label.substring(matchResult);
                String addedSuffix = key.substring(charPos + matchResult);

                Intermediate im = new Intermediate(label, prefix, previousSuffix, addedSuffix, child);
                im.intermediateTo(currentNode, value);
                return;
            }

            currentNode = edge.getChild();
            charPos += label.length();
        }
    }

    /**
     * Returns the value associated with the
     * @param key
     * @return
     */
    public Object get(String key) {
        TrieNode node = fetch(key);
        if (node == null)
            return null;

        return node.getValue();
    }

    /**
     * prefix와 매핑되는 항목과 그 하위 노드를 Iterator로 반환한다.
     *
     * @param prefix
     * @return
     */
    public ValueIterator getPrefixedBy(String prefix) {
        assert prefix != null;
        TrieNode node = root;
        for (int i=0, len=prefix.length(); i < len;) {
            char c = prefix.charAt(i);
            TrieEdge edge = node.findChildWhoseLabelStartsWith(c);
            if (edge == null) {
                return null;
            }

            String label = edge.getLabel();
            node = edge.getChild();
            int result = match(prefix, label, i, len);
            if (result >= 0) { // prefix and label differ
                //node = null;
                break;
            } else if (result + i == len) { // part of edge label
                break;
            }

            i += label.length();
        }

        return new ValueIterator(node);
    }

    // Returns overlaped prefix location or -1 if there's no overlap prefix
    private int findOverlapedPrefix(String key, String label, int start, int end) {
        return match(key, label, start, end);
    }

    // Returns the node associated with prefix, or null if node.
    private TrieNode<V> fetch(String prefix) {
        TrieNode<V> node = root;
        for (int i = 0; i < prefix.length();) {
            TrieEdge<V> edge = node.findChildWhoseLabelStartsWith(prefix.charAt(i));
            if (edge == null)
                return null;

            String label = edge.getLabel();
            int result = match(prefix, label, i, prefix.length());
            if (result != -1)
                return null;

            i += label.length();
            node = edge.getChild();
        }
        return node;
    }



    /**
     * Matches the pattern <tt>str2</tt> against the <tt>str1[startOffset..stopOffset-1]</tt>.<br/>
     * Examples: <br/>
     *         str1 = "abcde", str2 = "abc", startOffset = 0, stopOffset = 5 ==> returns -1<br/>
     *         str1 = "abcde", str2 = "abcd", startOffset = 1, stopOffset = 3 ==> returns 2<br/>
     *         str1 = "가게방", str2 = "가게집", startOffset = 0, stopOffSet = 3 ==> returns 2<br/>
     *
     * @param str1 - first string
     * @param str2 - string to compare
     * @param startOffset - start location
     * @param stopOffset - stop location
     *
     * @return first differed location, or -1 if differed location not found
     */
    public int match(String str1, String str2, int startOffset, int stopOffset) {
        int i = startOffset;
        for (int j=0; j < str2.length(); j++) {
            if (i >= stopOffset) {
                return j;
            }

            if (str1.charAt(i) != str2.charAt(j)) {
                return j;
            }
            i++;
        }

        return -1;
    }


    public void print(PrintWriter writer) {
        writer.println(root);
        print(writer, root, 0);
        writer.flush();
        writer.close();
    }

    private void print(PrintWriter writer, TrieNode node, int depth) {
        String space = "|";
        for (int i=0; i < depth;i++) {
            space += "-";
        }

        List<TrieEdge> children = node.getChildren();
        for (TrieEdge each : children) {
            writer.println(space + each + " " + depth);
            if (each.getChild().getChildren().size() > 0) {
                int newDepth = depth + 1;
                print(writer, each.getChild(), newDepth);

            }
        }
        writer.flush();

    }


    /**
     * 단어간에 prefix가 일치하는 경우에 노드를 쪼개거나 연결하는 역할을 한다.
     */
    final class Intermediate {
        private String label;
        private String commonPrefix;
        private String suffix;
        private String addedSuffix;
        private TrieNode<V> child;

        public Intermediate(String label, String commonPrefix, String suffix, String addedSuffix, TrieNode child) {
            this.label = label;
            this.commonPrefix = commonPrefix;
            this.suffix = suffix;
            this.addedSuffix = addedSuffix;
            this.child = child;
        }

        public void intermediateTo(TrieNode node, V value) {
            if (addedSuffix.length() > 0) {
                split(node, value);
            } else {
                splice(node, value);
            }
        }

        private void splice(TrieNode node, V value) {
            node.remove(label.charAt(0));

            TrieNode<V> intermediate = new TrieNode<V>();

            intermediate.addChild(suffix, child);
            intermediate.setValue(value);
            node.addChild(commonPrefix, intermediate);
        }

        private void split(TrieNode node, V value) {
            node.remove(label.charAt(0));

            TrieNode<V> newNode = new TrieNode<V>(value);
            TrieNode<V> intermediate = new TrieNode<V>();

            intermediate.addChild(suffix, child);
            intermediate.addChild(addedSuffix, newNode);
            node.addChild(commonPrefix, intermediate);
        }
    }
}

