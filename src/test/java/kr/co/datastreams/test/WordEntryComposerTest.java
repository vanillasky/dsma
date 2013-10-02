package kr.co.datastreams.test;

import kr.co.datastreams.dsma.dic.PosTagComposer;
import kr.co.datastreams.dsma.dic.WordEntryComposer;
import kr.co.datastreams.dsma.model.PosTag;
import kr.co.datastreams.dsma.model.WordEntry;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 7. 19
 * Time: 오전 9:36
 * To change this template use File | Settings | File Templates.
 */
public class WordEntryComposerTest {

    String[] posTagWordEntries = {"가가호호 N:AD", "걸 N:V:AD"};

    @Test
    public void testPosTagWordEntryComposer_isTagOf() throws Exception {

        WordEntryComposer composer = new PosTagComposer();
        List<WordEntry> entries = new ArrayList<WordEntry>();
        for (String each : posTagWordEntries) {
            WordEntry entry = composer.compose(each);
            if (entry != null) {
                entries.add(entry);
            }
        }

        assertEquals(2, entries.size());

        assertTrue(entries.get(0).isTagOf(PosTag.N));
        assertTrue(entries.get(0).isTagOf(PosTag.NNG));
        assertTrue(entries.get(0).isTagOf(PosTag.AD));

        assertTrue(entries.get(1).isTagOf(PosTag.AD));
        assertTrue(entries.get(1).isTagOf(PosTag.V));
        assertTrue(entries.get(1).isTagOf(PosTag.N));
    }


    @Test
    public void testPosTagWordEntryComposer_logging() throws Exception {
        String[] dicEntries = {"가가호호 N:AD", "걸"};
        WordEntryComposer composer = new PosTagComposer();
        List<WordEntry> entries = new ArrayList<WordEntry>();

        for (String each : dicEntries) {
            WordEntry entry = composer.compose(each);
            if (entry != null && entry.tag() > 0) {
                entries.add(entry);
            }
        }

        assertEquals(1, entries.size());
    }

    @Test
    public void testPosTagWordEntryComposer_undefinedTag() throws Exception {
        String dicEntry = "가가호호 NQ:AX";
        WordEntryComposer composer = new PosTagComposer();
        WordEntry entry = composer.compose(dicEntry);
        assertEquals(0, entry.tag());
    }

    @Test
    public void testPosTagWordEntryComposer_irregularType() throws Exception {
        String dicEntry = "충고듣 VV:IRRB";
        WordEntryComposer composer = new PosTagComposer();
        WordEntry entry = composer.compose(dicEntry);
        assertEquals(PosTag.IrregularType.IRRB, entry.getIrregularType());
        assertTrue(entry.isIrregular());
    }
}
