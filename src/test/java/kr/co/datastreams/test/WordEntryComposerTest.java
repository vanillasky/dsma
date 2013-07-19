package kr.co.datastreams.test;

import kr.co.datastreams.dsma.dic.DefaultWordEntryComposer;
import kr.co.datastreams.dsma.dic.WordEntryComposer;
import kr.co.datastreams.dsma.ma.WordEntry;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 7. 19
 * Time: 오전 9:36
 * To change this template use File | Settings | File Templates.
 */
public class WordEntryComposerTest {

    String[] words = {"가", "가가호호", "가각"};
    String[] basicWordEntries = {"//WORD,NVZDBIPSCC:N(명사)", "  //starts with space then...", "가,110000000X", "가가호호, 101000000X", "가각,   100000000X"};
    String[] wrongNumberOfCiphers = {"가,1100000X", "가가호호, 0101000000X", "가각,   1000000000X"}; // 자릿수 불일치 항목

    @Test
    public void testDefaultWordEntryComposer() throws Exception {
        WordEntryComposer composer = new DefaultWordEntryComposer();
        List<WordEntry> entries = composer.compose(basicWordEntries);
        assertNotNull(entries);
        int i=0;
        for (WordEntry each : entries) {
            assertEquals(words[i++], each.getWord());
        }
    }


    @Test
    public void testDefaultWordEntryComposer_wrongFeaturedWords() throws Exception {
        WordEntryComposer composer = new DefaultWordEntryComposer();
        List<WordEntry> entries = composer.compose(wrongNumberOfCiphers);
        assertEquals(0, entries.size());
        String[] failedLines = composer.parseFailedLines();

    }


}
