package kr.co.datastreams.dsma.dic;

import kr.co.datastreams.commons.util.StringUtil;
import kr.co.datastreams.dsma.ma.WordEntry;

import java.util.ArrayList;
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
    private static final char[] FLAG_PATTERN = "20000000X".toCharArray();

    private final List<String> wrongFeaturedWords = new ArrayList<String>();

    @Override
    public List<WordEntry> compose(String[] lines) {
        List<WordEntry> entries = new ArrayList<WordEntry>();
        String trimedLine;

        for (String line : lines) {
            trimedLine = line.trim();
            if (trimedLine.startsWith(COMMENT_IDENTIFIER) || StringUtil.nvl(trimedLine).length() == 0) {
                continue;
            }

            String[] tokens = line.split(TOKEN_SEPARATOR);
            if (tokens.length < 2) {
                wrongFeaturedWords.add(line);
                continue;
            }

            WordEntry entry = new WordEntry(tokens[0].trim(), FLAG_PATTERN, compseCompoundEntry(tokens[1]));
            entries.add(entry);

        }

        return entries;
    }

    private List<CompoundWordEntry> compseCompoundEntry(String token) {

    }

    @Override
    public String[] parseFailedLines() {
        return wrongFeaturedWords.toArray(new String[wrongFeaturedWords.size()]);
    }
}
