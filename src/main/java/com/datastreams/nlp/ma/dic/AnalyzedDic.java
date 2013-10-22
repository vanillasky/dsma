package com.datastreams.nlp.ma.dic;

import com.datastreams.nlp.common.conf.Configuration;
import com.datastreams.nlp.ma.conf.ConfKeys;
import com.datastreams.nlp.ma.conf.Config;
import com.datastreams.nlp.ma.model.Eojeol;
import com.datastreams.nlp.ma.model.Morpheme;
import com.datastreams.nlp.ma.model.MorphemeList;
import kr.co.datastreams.commons.util.FileUtil;
import kr.co.datastreams.commons.util.StopWatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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
    private final static String MORPHEMES_SEPARATOR = "\\|";  // 기분석 결과가 2개 이상인 경우는 '|'를 구분자로 사용한다.
    private final static AnalyzedDic instance = new AnalyzedDic();
//    private final static Logger logger = LoggerFactory.getLogger(AnalyzedDic.class);

    private final Map<String, Eojeol> analyzedWords = Collections.synchronizedMap(new HashMap<String, Eojeol>());

    private AnalyzedDic() {
        Configuration conf = Config.get();
        load(conf.get(KOREAN_ANALYZED_DIC));
    }

    private void load(String fileName) {
        StopWatch watch = null;
        watch = StopWatch.create();
        watch.start();

        synchronized (analyzedWords) {
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
        }

        watch.end();
        watch.println("dictionary "+ fileName + " loaded, ");


    }

    /**
     * 연속된 형태소의 구조체인 MorphemeList 리스트를 만들어 반환한다.
     *
     * @param morphemesGroups
     * @return
     */
    private List<MorphemeList> asMorphemeLists(List<String[]> morphemesGroups) {
        List<MorphemeList> candidates = new ArrayList<MorphemeList>(morphemesGroups.size());
        List<Morpheme> morphemes;

        for (String[] eachGroup : morphemesGroups) {
            morphemes = new ArrayList<Morpheme>();
            for (String each : eachGroup) {
                String temp = each.replaceAll("(<|>|\\s)", "");
                Morpheme m = new Morpheme(temp.split(",")[0].trim(), temp.split(",")[1].trim());
                morphemes.add(m);
            }

            // 필요하면 MorphemeList를 Immutable로 만들 생각이므로 add() 메소드를 사용하지 않았음
            candidates.add(MorphemeList.create(morphemes.toArray(new Morpheme[morphemes.size()])));
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

    public static Eojeol search(String word) {
        return instance.get(word);
    }
}
