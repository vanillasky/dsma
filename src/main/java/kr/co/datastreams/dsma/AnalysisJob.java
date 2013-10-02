package kr.co.datastreams.dsma;

import kr.co.datastreams.dsma.analyzer.*;
import kr.co.datastreams.dsma.model.Eojeol;
import kr.co.datastreams.dsma.model.JobStatus;
import kr.co.datastreams.dsma.model.PlainSentence;
import kr.co.datastreams.dsma.model.Sentence;
import kr.co.datastreams.dsma.tokenizer.CharTypeTokenizer;
import kr.co.datastreams.dsma.tokenizer.NewLineSeparator;
import kr.co.datastreams.dsma.tokenizer.SentenceSeparator;
import kr.co.datastreams.dsma.tokenizer.Tokenizer;
import kr.co.datastreams.dsma.util.WordCounter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;

/**
 *
 * User: shkim
 * Date: 13. 9. 12
 * Time: 오전 10:41
 *
 */
public class AnalysisJob {

    private final List<PlainSentence> plainSentences;
    private final ExecutorService executor;
    private final IMorphemeAnalyzer analyzer;
    private final int numOfThreads;
    private final CompletionService<Sentence> completionService;
    private final boolean useCache;
    private final boolean useWrodCounting;
    private final WordCounter wordCounter;

    private List<Sentence> analzyedSentences;
    private JobStatus status;


    /**
     *
     * @param text
     * @return
     */
    public static AnalysisJob create(String text) {
        return new AnalysisJob(text, new NewLineSeparator(), new CharTypeTokenizer(), 1, false, false);
    }

    /**
     *
     * @param text
     * @param separator
     * @param tokenizer
     * @param numOfThreads
     * @return
     */
    public static AnalysisJob create(String text, NewLineSeparator separator, Tokenizer tokenizer, int numOfThreads) {
        return new AnalysisJob(text, separator, tokenizer, numOfThreads, false, false);
    }

    /**
     *
     * @param text
     * @param separator
     * @param tokenizer
     * @param numOfThreads
     * @param useCache
     * @param useWordCounting
     * @return
     */
    public static AnalysisJob create(String text, SentenceSeparator separator, Tokenizer tokenizer, int numOfThreads, boolean useCache, boolean useWordCounting) {
        return new AnalysisJob(text, separator, tokenizer, numOfThreads, useCache, useWordCounting);
    }

    private AnalysisJob(String document, SentenceSeparator separator, Tokenizer tokenizer, int numOfThreads, boolean useCache, boolean useWordCounting) {
        this.wordCounter = useWordCounting ? new WordCounter() : null;
        TokenAnalysisStrategy strategy = new HeuristicStrategy();
        this.analyzer = useCache ? new CachableMorphemeAnalyzer(tokenizer, wordCounter, strategy) : new MorphemeAnalyzer(tokenizer, wordCounter, strategy);
        this.executor = Executors.newFixedThreadPool(numOfThreads);
        this.plainSentences = separator.separate(document);
        this.analzyedSentences = new ArrayList(plainSentences.size());
        this.numOfThreads = numOfThreads;
        this.completionService = new ExecutorCompletionService<Sentence>(executor);
        this.useCache = useCache;
        this.useWrodCounting = useWordCounting;
        this.status = JobStatus.READY;

    }


    public int size() {
        return plainSentences.size();
    }

    public List<Sentence> getAnalzyedSentences() {
        return analzyedSentences;
    }

    public List<PlainSentence> getPlainSentences() {
        return plainSentences;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Iterator<Sentence> it=analzyedSentences.iterator(); it.hasNext();) {
            Sentence sentence = it.next();
            sb.append(sentence).append("\n");
            List<Eojeol> eojeols = sentence.getEojeols();
            for (Eojeol each : eojeols) {
                sb.append(each.asMorphemeString());
            }
        }
        return sb.toString();
    }

    public void addResult(Sentence sentence) {
        analzyedSentences.add(sentence);
    }


    public String getResultAsString() {
        return toString();
    }

    public List<Sentence> getResult() {
        return analzyedSentences;
    }


    public void done() {
        Collections.sort(analzyedSentences);
        status = JobStatus.FINISHED;
    }

    public void start() {
        //startMonitor();
        submitSentences();
        processResult();
    }

    private void startMonitor() {
        Runnable monitor = new Runnable() {

            @Override
            public void run() {
                while (true) {
                    if (status == JobStatus.FINISHED) break;
                    System.out.println("running: " + getResult().size());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        Thread t = new Thread(monitor);
        t.setDaemon(true);
        t.start();
    }

    // 분석할 문장을 제출한다.
    private void submitSentences() {
        List<PlainSentence> inputSentences = getPlainSentences();
        for (final PlainSentence input : inputSentences) {
            completionService.submit(new Callable<Sentence>() {
                @Override
                public Sentence call() throws Exception {
                    TimeUnit.MILLISECONDS.sleep(100);
                    return analyzer.analyze(input);
                }
            });
        }
    }


    // CompletionService를 통해 실행된 형태소 분석 결과를 처리한다.
    private void processResult() {
        try {
            for (int n=0; n < size(); n++) {
                Future<Sentence> f = completionService.take();
                Sentence sentence = f.get();
                addResult(sentence);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        done();
    }

    public Integer getFrequency(String word) {
        return wordCounter == null ? null : wordCounter.gerFrequencyOfUse(word);
    }


}
