package kr.co.datastreams.dsma.dic;

import kr.co.datastreams.commons.util.StringUtil;
import kr.co.datastreams.dsma.dic.WordEntryComposer;
import kr.co.datastreams.dsma.ma.PosTag;
import kr.co.datastreams.dsma.ma.model.WordEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 8. 12
 * Time: 오후 2:35
 * To change this template use File | Settings | File Templates.
 */
public class PosTagComposer implements WordEntryComposer {

    private static final String COMMENT_IDENTIFIER = "//";
    private static final String SEPARATOR = ",";

    private final List<String> wrongFeaturedWords = new ArrayList<String>();

    @Override
    public WordEntry compose(String line) {
        if (line.trim().length() == 0 || line.trim().startsWith(COMMENT_IDENTIFIER)) {
            return null;
        }

        String[] wordData = line.split(SEPARATOR);
        if (wordData.length < 3) {
            wrongFeaturedWords.add(line);
            return null;
        }

        wordData[2] = wordData[2].trim();
        if (wordData[2].length() ==  0) {
            wrongFeaturedWords.add(line);
        } else {
            return WordEntry.createWithPosTags(wordData[0].trim(), wordData[2].trim().split(":"));
        }

        return null;
    }

    @Override
    public String[] parseFailedLines() {
        return wrongFeaturedWords.toArray(new String[wrongFeaturedWords.size()]);
    }
}
