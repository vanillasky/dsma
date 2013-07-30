package kr.co.datastreams.dsma.dic;

import kr.co.datastreams.commons.util.StringUtil;
import kr.co.datastreams.dsma.ma.model.WordEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * 10자리 feature로 구성된 단어정보를 읽어서 WordEntry 객체의 List로 만들어 반환한다.<br/>
 * Example:<br/>
 *   "가,1100000X" -> new WordEntry("가", 1100000X); <br/>
 *
 * 각 입력 라인을 구분자로 분리하여 단어와 해당 단어의 정보로 WordEntry 객체를 만든다.
 * //로 시작되는 줄은 주석으로 인식하여 처리하지 않는다.
 * 라인이 구분자로 분리되지 않거나 단어정의 길이가 FALG_COUNT와 일치하지 않으면 파싱실패 목록에 보관한다.
 *
 * User: shkim
 * Date: 13. 7. 19
 * Time: 오전 10:43
 *
 */
public class DefaultWordEntryComposer implements WordEntryComposer {
    private static final String COMMENT_IDENTIFIER = "//";
    private static final String SEPARATOR = ",";
    private static final int FLAG_COUNT = 10;

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

            String[] wordData = line.split(SEPARATOR);
            if (wordData.length < 2) {
                wrongFeaturedWords.add(line);
                continue;
            }


            wordData[1] = wordData[1].trim();
            if (wordData[1].length() - FLAG_COUNT != 0) {
                wrongFeaturedWords.add(line);
            } else {
                WordEntry entry = new WordEntry(wordData[0].trim(), wordData[1].toCharArray());
                entries.add(entry);
            }
        }

        return entries;
    }

    @Override
    public String[] parseFailedLines() {
        return wrongFeaturedWords.toArray(new String[wrongFeaturedWords.size()]);
    }
}
