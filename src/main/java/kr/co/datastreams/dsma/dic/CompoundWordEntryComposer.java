package kr.co.datastreams.dsma.dic;

import kr.co.datastreams.commons.util.StringUtil;
import kr.co.datastreams.dsma.ma.model.CompoundWordEntry;
import kr.co.datastreams.dsma.ma.model.WordEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 복합명사 사전 처리
 *
 * User: shkim
 * Date: 13. 7. 19
 * Time: 오후 5:50
 *
 */
public class CompoundWordEntryComposer implements WordEntryComposer {
    private static final String COMMENT_IDENTIFIER = "//";
    private static final String WORD_SEPARATOR = ",";
    private static final String TOKEN_SEPARATOR = ":";
    private static final char[] FEATURES = "20000000X".toCharArray();

    private final List<String> wrongFeaturedWords = new ArrayList<String>();

    @Override
    public WordEntry compose(String line) {
        if (line.trim().length() == 0 || line.trim().startsWith(COMMENT_IDENTIFIER)) {
            return null;
        }

        String[] tokens = line.trim().split(TOKEN_SEPARATOR);
        if (tokens.length < 2) {
            wrongFeaturedWords.add(line);
            return null;
        }

        return null; //new WordEntry(tokens[0].trim(), FEATURES, asCompoundList(tokens[1]));
    }


    private List<CompoundWordEntry> asCompoundList(String token) {
        String[] components = token.split(WORD_SEPARATOR);
        if (components.length < 2) {
            return Collections.EMPTY_LIST;
        }

        List<CompoundWordEntry> list = new ArrayList<CompoundWordEntry>();
        for (String each : components) {
            CompoundWordEntry entry = new CompoundWordEntry(each, FEATURES, token.indexOf(each));
            list.add(entry);
        }
        return list;
    }


    @Override
    public String[] parseFailedLines() {
        return wrongFeaturedWords.toArray(new String[wrongFeaturedWords.size()]);
    }
}
