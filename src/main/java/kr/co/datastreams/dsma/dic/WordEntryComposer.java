package kr.co.datastreams.dsma.dic;

import kr.co.datastreams.dsma.model.WordEntry;

/**
 *
 * User: shkim
 * Date: 13. 7. 19
 * Time: 오전 10:41
 *
 */
public interface WordEntryComposer {
    WordEntry compose(String text);
}
