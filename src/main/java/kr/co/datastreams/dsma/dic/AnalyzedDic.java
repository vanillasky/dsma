package kr.co.datastreams.dsma.dic;

import kr.co.datastreams.commons.util.FileUtil;
import kr.co.datastreams.commons.util.StopWatch;
import kr.co.datastreams.dsma.conf.ConfKeys;
import kr.co.datastreams.dsma.conf.Configuration;
import kr.co.datastreams.dsma.conf.ConfigurationFactory;
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

                analyzedWords.put(line.split("\\s")[0], makeWord(line));
            }

            watch.end();
            watch.println("dictionary "+ fileName + "loaded, ");
        }
    }

    private Word makeWord(String line) {
        int wordIndex = line.indexOf("<");
        String str = line.split("\\s")[0].trim();
        String wordInfo = line.substring(wordIndex, line.length());
        String[] tokens =  wordInfo.split("\\+");
        Pattern pattern = Pattern.compile("\\s*<([가-힣]++)\\s*,\\s*(\\w++)");

        Word word = new Word(str, CharType.HANGUL);
        AnalysisResult analysisResult = new AnalysisResult();
        analysisResult.setSource(str);
        analysisResult.setScore(AnalysisResult.SCORE_CORRECT);
        for (String each : tokens) {
            Matcher matcher = pattern.matcher(each);
            String posTag = null;

            if (matcher.find()) {
                posTag = matcher.group(2);
                if (posTag.equals("j")) {
                    analysisResult.setJosa(matcher.group(1));
                } else if (posTag.equals("e")) {
                    analysisResult.setEnding(matcher.group(1));
                } else if (posTag.equals("f")) {
                    analysisResult.setPrefinalEnding(matcher.group(1));
                } else if (posTag.equals("c")) {

                } else {
                    analysisResult.setStem(matcher.group(1));
                }
            }
        }
        System.out.println(analysisResult);


        return word;
    }

    private Word get(String word) {
        return analyzedWords.get(word);
    }

    public static Word find(String word) {
        return instance.get(word);
    }


}
