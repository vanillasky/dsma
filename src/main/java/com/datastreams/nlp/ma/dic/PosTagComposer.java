package com.datastreams.nlp.ma.dic;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 단어<공백>TAG:TAG 형식의 라인에서 WordEntry 객체를 만들어 반환한다.
 * //로 시작되는 라인은 처리하지 않는다.
 * e.g)개시	NNG:MAG:DOVI:DOVT
 *
 * User: shkim
 * Date: 13. 8. 12
 * Time: 오후 2:35
 *
 */
public class PosTagComposer implements WordEntryComposer {
    private static final Logger logger = LoggerFactory.getLogger("Dictionary");
    private static final String COMMENT_IDENTIFIER = "//";
    private static final String SEPARATOR = "/";
    private static final String TAG_SEPARATOR = "/";
//    private static final String SEPARATOR = "\\s++";
//    private static final String TAG_SEPARATOR = ":";

    @Override
    public WordEntry compose(String line) {
        if (line.trim().length() == 0 || line.trim().startsWith(COMMENT_IDENTIFIER)) {
            return null;
        }

        String[] wordData = line.split(SEPARATOR);
        if (wordData.length < 2) {
            //logger.warn("invalid word info.: {}", line);
            return WordEntry.create(wordData[0]);
        }

        String word = wordData[0].trim();
        String[] tags = new String[(wordData.length-1)];
        for (int i=1, j=0; i < wordData.length; i++, j++) {
            tags[j] = wordData[i];
        }

        return WordEntry.createWithTags(word, tags);

    }

}
