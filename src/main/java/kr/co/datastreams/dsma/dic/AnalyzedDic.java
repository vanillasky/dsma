package kr.co.datastreams.dsma.dic;

import kr.co.datastreams.commons.util.FileUtil;
import kr.co.datastreams.commons.util.StopWatch;
import kr.co.datastreams.dsma.conf.ConfKeys;
import kr.co.datastreams.dsma.conf.Configuration;
import kr.co.datastreams.dsma.conf.ConfigurationFactory;
import kr.co.datastreams.dsma.ma.model.AnalysisResult;
import kr.co.datastreams.dsma.ma.model.Word;

import java.util.HashMap;
import java.util.List;
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

                Word word = Word.createAnalyzedHangul(line.split("\\s")[0].trim());
                word.addResult(AnalysisResult.buildResult(word.getString(), parseMorphemes(line)));
                analyzedWords.put(line.split("\\s")[0], word);
            }

            watch.end();
            watch.println("dictionary "+ fileName + "loaded, ");
        }
    }



    // 같았다  <같, VV>  + <었, EP> + <다, EM> -> [0]=<같, VV>, [1]=<었, EP>, [2]=<다, EM>
    private String[] parseMorphemes(String line) {
        String analyzedInfoPart = line.substring(line.indexOf("<"), line.length());
        return analyzedInfoPart.split("\\+");
    }

    private Word get(String word) {
        return analyzedWords.get(word);
    }

    public static Word find(String word) {
        return instance.get(word);
    }
}
