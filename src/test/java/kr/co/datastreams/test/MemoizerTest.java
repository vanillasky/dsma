package kr.co.datastreams.test;

import kr.co.datastreams.dsma.dic.AnalyzedDic;
import kr.co.datastreams.dsma.model.Eojeol;
import kr.co.datastreams.dsma.util.cache.Computable;
import kr.co.datastreams.dsma.util.cache.Memoizer;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 *
 * User: shkim
 * Date: 13. 9. 23
 * Time: 오후 5:28
 *
 */
public class MemoizerTest {

    private Dummy dummyAnalyzer = new Dummy();

    private static class Dummy {
        public Eojeol get(String word) {
            return AnalyzedDic.find(word);
        }
    }

    Computable<String, Eojeol> computable = new Computable<String, Eojeol>() {
        @Override
        public Eojeol compute(String arg) throws InterruptedException {
            return dummyAnalyzer.get(arg);
        }
    };

    Memoizer<String, Eojeol> cache = new Memoizer<String, Eojeol>(computable);

    @Test
    public void testCompute() throws Exception {
        String expected = "감기는\t<AnalyzedDic>\n\t<감기,NNG>+<는,JX>\n\t<감기,VV>+<는,ETM>\n";
        Eojeol result = cache.compute("감기는");
        assertEquals(expected, result.asMorphemeString());
    }

    @Test
    public void testUsingThread() throws Exception {
        int threadCount = 5;
        final String word = "감기는";

        Thread[] threads = new Thread[threadCount];
        for (int i=0; i < threads.length; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Eojeol e = cache.compute(word);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            });
        }

        for (int i=0; i < threads.length; i++) {
            threads[i].start();
            TimeUnit.MILLISECONDS.sleep(100);
        }

    }
}
