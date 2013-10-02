package kr.co.datastreams.dsma.dic;

import kr.co.datastreams.commons.util.FileUtil;
import kr.co.datastreams.commons.util.StopWatch;
import kr.co.datastreams.dsma.conf.ConfKeys;
import kr.co.datastreams.dsma.conf.Configuration;
import kr.co.datastreams.dsma.conf.ConfigurationFactory;
import kr.co.datastreams.dsma.model.Eojeol;
import kr.co.datastreams.dsma.model.Morpheme;
import kr.co.datastreams.dsma.model.MorphemeList;
import kr.co.datastreams.dsma.model.Score;

import java.util.*;
import java.util.regex.Pattern;

/**
 * 기분석 사전
 *
 * User: shkim
 * Date: 13. 8. 8
 * Time: 오후 3:56
 *
 */
public class AnalyzedDic implements ConfKeys {

    public final static Pattern ANAL_RESULT_PATTERN = Pattern.compile("\\s*<([가-힣ㄱ-ㅎㅏ-ㅣ]++)\\s*,\\s*(\\w++)"); // <같, VV>
    private final static String MORPHEMES_SEPARATOR = "\\|";
    private final static AnalyzedDic instance = new AnalyzedDic();

    private final Map<String, Eojeol> analyzedWords = Collections.synchronizedMap(new HashMap<String, Eojeol>());

    private AnalyzedDic() {
        Configuration conf = ConfigurationFactory.getConfiguration();
        load(conf.get(ANALYZED_DIC));
    }

    private void load(String fileName) {
        synchronized (analyzedWords) {
            StopWatch watch = StopWatch.create();
            watch.start();

            List<String> lines = FileUtil.readLines(fileName);
            for (String line : lines) {
                if (line.trim().length() == 0 || line.trim().startsWith("//")) {
                    continue;
                }

                String headword = line.split("\\s")[0].trim();
                List<String[]> morphemesGroups = parseMorphemes(line);
                List<MorphemeList> candidates = asMorphemeLists(morphemesGroups);

                Eojeol eojeol = Eojeol.createAnalyzed(headword, candidates);
                analyzedWords.put(headword, eojeol);
            }

            watch.end();
            watch.println("dictionary "+ fileName + "loaded, ");
        }
    }

    /**
     * 형태소 정보를 배열 형태로 가지고 있는 List로부터 MorphemeList의 List를 만들어 반환한다.
     *
     * @param morphemesGroups
     * @return
     */
    private List<MorphemeList> asMorphemeLists(List<String[]> morphemesGroups) {
        List<MorphemeList> candidates = new ArrayList<MorphemeList>(morphemesGroups.size());

        for (String[] eachGroup : morphemesGroups) {
            MorphemeList morphemeList = new MorphemeList(Score.AnalyzedDic);
            for (String each : eachGroup) {
                String temp = each.replaceAll("(<|>|\\s)", "");
                Morpheme m = new Morpheme(temp.split(",")[0].trim(), temp.split(",")[1].trim());
                morphemeList.add(m);
            }
            candidates.add(morphemeList);
        }
        return candidates;
    }


    /**
     * 기분석 사전의 라인을 파싱해서 형태소 정보를 반환한다.
     * e.g.)
     *   line: 같았다  <같, VV>  + <었, EP> + <다, EM>
     *   result: List<String[1]> -> List(0):String[]{<같, VV>, <었, EP>, <다, EM>}
     * @param line
     * @return
     */
    private List<String[]> parseMorphemes(String line) {
        line = line.replaceAll("\\s++<", "<").replaceAll(">\\s++", ">");

        String morphemePart = line.substring(line.indexOf("<"), line.length());

        String[] morphemesGroup = morphemePart.split(MORPHEMES_SEPARATOR);
        List<String[]> result = new ArrayList<String[]>(morphemesGroup.length);

        for (String eachMorphemes : morphemesGroup) {
            result.add(eachMorphemes.split("\\+"));
        }

        return result;
    }

    private Eojeol get(String word) {
        return analyzedWords.get(word);
    }

    public static Eojeol find(String word) {
        return instance.get(word);
    }
}
