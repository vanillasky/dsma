package kr.co.datastreams.test;

import kr.co.datastreams.dsma.dic.Dictionary;
import kr.co.datastreams.dsma.ma.model.WordEntry;
import org.junit.Before;
import org.junit.Test;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 7. 18
 * Time: 오후 4:51
 * To change this template use File | Settings | File Templates.
 */
public class DictionaryTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testLoadDictionary() throws Exception {
        WordEntry word = Dictionary.get("괴물");
        assertEquals("괴물", word.getString());
        Dictionary.printDictionary(new PrintWriter(new FileWriter("d:/temp/ka.txt")));
    }

    @Test
    public void testGetPrefixedBy() throws Exception {
        String[] expected = {"포르노", "포르르", "포르말린", "포르볼", "포르노그라피"};
        List<String> candidates = new ArrayList<String>();
        Iterator iter = Dictionary.getPrefixedBy("포르");
        assertNotNull(iter);
        while (iter.hasNext()) {
            WordEntry entry = (WordEntry)iter.next();
            candidates.add(entry.getString());
            System.out.println(entry);
        }
        System.out.println(candidates.size());
        assertArrayEquals(expected, candidates.toArray(new String[candidates.size()]));
    }

//    @Test
//    public void testSort() throws Exception {
//        List<String> sourceLines = FileUtil.readLines("dic/total.dic");
//        List<String> sortList = FileUtil.readLines("dic/total.dic");
//        Collections.sort(sortList);
//
//        int i=0;
//        for (String each : sourceLines) {
//            System.out.println("source:" + each + ", =>" + sortList.get(i++));
//        }
//    }
}
