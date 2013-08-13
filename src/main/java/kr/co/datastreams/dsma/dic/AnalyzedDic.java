package kr.co.datastreams.dsma.dic;

import kr.co.datastreams.commons.util.FileUtil;
import kr.co.datastreams.commons.util.StopWatch;
import kr.co.datastreams.dsma.conf.ConfKeys;
import kr.co.datastreams.dsma.conf.Configuration;
import kr.co.datastreams.dsma.conf.ConfigurationFactory;
import kr.co.datastreams.dsma.ma.PosTag;
import kr.co.datastreams.dsma.ma.model.AnalysisResult;
import kr.co.datastreams.dsma.ma.model.CharType;
import kr.co.datastreams.dsma.ma.model.Word;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 8. 8
 * Time: 오후 3:56
 * To change this template use File | Settings | File Templates.
 */
public class AnalyzedDic implements ConfKeys {

    private final static Pattern ANAL_RESULT_PATTERN = Pattern.compile("\\s*<([가-힣]++)\\s*,\\s*(\\w++)"); // <같, VV>

    private static final AnalyzedDic instance = new AnalyzedDic();
    private final HashMap<String, Word> analyzedWords = new HashMap<String, Word>();


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

                analyzedWords.put(line.split("\\s")[0], createAnalyzedWord(line));
            }

            watch.end();
            watch.println("dictionary "+ fileName + "loaded, ");
        }
    }

    private Word createAnalyzedWord(String line) {
        Word word = createWordFrom(line);
        String[] morphemes = parseMorphemes(line);

        AnalysisResult result = new AnalysisResult();
        result.setScore(AnalysisResult.SCORE_ANALYZED_DIC);

        for (String each : morphemes) {
            Matcher matcher = ANAL_RESULT_PATTERN.matcher(each);
            String pos, morpheme;
            while (matcher.find()) {
                morpheme = matcher.group(1);
                pos = matcher.group(2);

                long tagNum = PosTag.getTagNum(pos);
                if (tagNum == 0) {
                    throw new IllegalArgumentException("Tag not defined:" + pos);
                }

                if (tagNum == PosTag.JO) {
                    result.setJosa(morpheme);
                } else if (tagNum == PosTag.EM) {
                    result.setEnding(morpheme);
                } else if (tagNum == PosTag.EP) {
                    result.setPrefinalEnding(morpheme);
                } else if (PosTag.isKindOf(tagNum, PosTag.N)) {
                    result.setStem(morpheme);
                    result.setPos(PosTag.getTag(tagNum));
                    result.setSimpleStemPos(PosTag.getTag(PosTag.N));
                } else if (PosTag.isKindOf(tagNum, PosTag.V)) {
                    result.setStem(morpheme);
                    result.setPos(PosTag.getTag(tagNum));
                    result.setSimpleStemPos(PosTag.getTag(PosTag.V));
                } else if (tagNum == PosTag.AD) {
                    result.setStem(morpheme);
                    result.setPos(PosTag.getTag(tagNum));
                    result.setSimpleStemPos(PosTag.getTag(PosTag.AD));
                }

            }
        }
        System.out.println(result);


        return word;
    }

    // 같았다  <같, VV>  + <었, EP> + <다, EM> -> [0]=<같, VV>, [1]=<었, EP>, [2]=<다, EM>
    private String[] parseMorphemes(String line) {
        String analyzedInfoPart = line.substring(line.indexOf("<"), line.length());
        return analyzedInfoPart.split("\\+");
    }

    private Word createWordFrom(String line) {
        String analyzedWord = line.split("\\s")[0].trim();
        return new Word(analyzedWord, CharType.HANGUL);
    }


    private Word get(String word) {
        return analyzedWords.get(word);
    }

    public static Word find(String word) {
        return instance.get(word);
    }


}
