package kr.co.datastreams.test;

import kr.co.datastreams.commons.util.FileUtil;
import kr.co.datastreams.commons.util.StringUtil;
import kr.co.datastreams.dsma.util.WordCounter;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 9. 26
 * Time: 오후 1:23
 * To change this template use File | Settings | File Templates.
 */
public class WordCounterTest {



    @Test
    public void testCountWord() throws Exception {
        List<String> lines = FileUtil.readLines(AnalysisJobTest.class.getClassLoader(), "n1.txt");
        String source = StringUtil.join(lines.toArray(new String[lines.size()]), "\n");
        String word = "최경주는";
        int numOfThread = 4;

        final String[] words = source.split(" ");
        int expectedWordCount = count(words, word) * numOfThread;

//        final String[] words = {"최경주는", "최경주는", "최경주는"};
        Thread[] threads = new Thread[numOfThread];
        final WordCounter counter = new WordCounter();

        for (int i=0; i < threads.length; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (String word : words) {
                        counter.count(word);
                    }
                }
            });

        }

        for (int i=0; i < threads.length; i++) {
            threads[i].start();
            TimeUnit.MILLISECONDS.sleep(100);
        }


        System.out.println("word count:"+ counter.gerFrequencyOfUse(word));
        assertEquals(expectedWordCount, (int)counter.gerFrequencyOfUse(word));
    }

    private int count(String[] words, String word) {
        int result = 0;
        for (String each : words) {
            if (each.equals(word)) {
                result++;
            }
        }
        return result;
    }
}
