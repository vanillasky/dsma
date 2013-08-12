package kr.co.datastreams.dsma.dic;

import kr.co.datastreams.dsma.ma.model.WordEntry;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 7. 19
 * Time: 오전 10:41
 * To change this template use File | Settings | File Templates.
 */
public interface WordEntryComposer {
    WordEntry compose(String lines);
    String[] parseFailedLines();
}
