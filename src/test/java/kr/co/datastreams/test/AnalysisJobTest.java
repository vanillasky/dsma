package kr.co.datastreams.test;

import kr.co.datastreams.commons.util.FileUtil;
import kr.co.datastreams.commons.util.StopWatch;
import kr.co.datastreams.commons.util.StringUtil;
import kr.co.datastreams.dsma.ma.tokenizer.CharTypeTokenizer;
import kr.co.datastreams.dsma.ma.tokenizer.NewLineSeparator;
import kr.co.datastreams.dsma.ma.AnalysisJob;
import org.junit.Test;

import java.util.List;
import static org.junit.Assert.*;

/**
 *
 * User: shkim
 * Date: 13. 9. 11
 * Time: 오후 3:43
 *
 */
public class AnalysisJobTest {


    public String loadText() throws Exception {
        List<String> lines = FileUtil.readLines(AnalysisJobTest.class.getClassLoader(), "n1.txt");
        String source = StringUtil.join(lines.toArray(new String[lines.size()]), "\n");

        return source;
    }

    @Test
    public void testJob_with_wordCounting() throws Exception {
        int numOfThreads = Runtime.getRuntime().availableProcessors() == 1 ? 2 : Runtime.getRuntime().availableProcessors();
        String source = loadText();

        AnalysisJob job = AnalysisJob.create(source, new NewLineSeparator(), new CharTypeTokenizer(), numOfThreads, true, true);
        StopWatch watch = StopWatch.create();
        watch.start();
        job.start();

        watch.end();
        System.out.println("threads:" + numOfThreads + ", " + job.getPlainSentences().size() + " sentences analyzed in " + watch.elapsedInSecondes());

        int count = job.getFrequency("최경주는");
        System.out.println(count);
       // assertEquals(5, count);

    }


    @Test
    public void testJob_without_wordCountingAndCache() throws Exception {

        String source = loadText();
        int numOfThreads = Runtime.getRuntime().availableProcessors() == 1 ? 2 : Runtime.getRuntime().availableProcessors();

        AnalysisJob job = AnalysisJob.create(source, new NewLineSeparator(), new CharTypeTokenizer(), numOfThreads, false, false);
        StopWatch watch = StopWatch.create();
        watch.start();
        job.start();
        watch.end();
        System.out.println("threads:" + numOfThreads + ", " +job.getPlainSentences().size() + " sentences analyzed in " + watch.elapsedInSecondes());

        Integer count = job.getFrequency("최경주는");
        assertNull(count);

    }

    @Test
    public void testJob_SingleThread() throws Exception {
        String source = loadText();

        AnalysisJob job = AnalysisJob.create(source, new NewLineSeparator(), new CharTypeTokenizer(), 1, false, true);
        StopWatch watch = StopWatch.create();
        watch.start();
        job.start();
        watch.end();
        System.out.println("threads:" + 1 + ", " +job.getPlainSentences().size() + " sentences analyzed in " + watch.elapsedInSecondes());

        Integer count = job.getFrequency("최경주는");
        assertNotNull(count);
        System.out.println(count);

    }
}
