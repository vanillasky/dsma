package kr.co.datastreams.test;

import kr.co.datastreams.commons.util.FileUtil;
import kr.co.datastreams.dsma.dic.trie.SampleTrie;
import kr.co.datastreams.dsma.dic.trie.TrieNode;
import kr.co.datastreams.dsma.ma.Morpheme;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 7. 15
 * Time: 오후 1:42
 * To change this template use File | Settings | File Templates.
 */
public class FileUtilTest {

    String fileName = "dic/eomi.dic";
    SampleTrie<String, Morpheme> dic = new SampleTrie<String, Morpheme>(true);

    @Test
    public void test() throws Exception {
        List<String> lines = FileUtil.readLines(fileName, "UTF-8");
        int i = 0;
        for (String each : lines) {
            String[] wordData = each.split(",");
            Morpheme morpheme = new Morpheme(wordData[0]);
            dic.add(morpheme.getWord(), morpheme);
        }

        printLabels(dic.getRoot(), 0);
    }

    private void printLabels(TrieNode node, int depth) {
        String space = "";
        for (int i=0; i < depth;i++) {
            space += "-";
        }


        int i=0;
        for (Iterator iter=node.childrentForward(); iter.hasNext();) {
            TrieNode cnode = (TrieNode)iter.next();
            if (cnode.getValue() == null) depth = 0;
            System.out.println(space + "" +cnode);
            if (cnode.getChildren().size() > 0) {
                printLabels(cnode, ++depth);
            }
            i++;
        }
    }



}
