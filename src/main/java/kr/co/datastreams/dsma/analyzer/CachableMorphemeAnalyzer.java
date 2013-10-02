package kr.co.datastreams.dsma.analyzer;

import kr.co.datastreams.dsma.annotation.ThreadSafe;
import kr.co.datastreams.dsma.model.Eojeol;
import kr.co.datastreams.dsma.model.Token;
import kr.co.datastreams.dsma.tokenizer.Tokenizer;
import kr.co.datastreams.dsma.util.WordCounter;
import kr.co.datastreams.dsma.util.cache.Computable;
import kr.co.datastreams.dsma.util.cache.Memoizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * User: shkim
 * Date: 13. 9. 26
 * Time: 오후 3:43
 *
 */
@ThreadSafe
public class CachableMorphemeAnalyzer extends MorphemeAnalyzer {

    private final static Logger logger = LoggerFactory.getLogger(CachableMorphemeAnalyzer.class);
    private final Memoizer<Token, Eojeol> cache;

    private final Computable<Token, Eojeol> computable = new Computable<Token, Eojeol>() {
        @Override
        public Eojeol compute(Token arg) throws InterruptedException {
            return analyzeToken(arg);
        }
    };

    public CachableMorphemeAnalyzer(Tokenizer tokenizer, WordCounter wordCounter, TokenAnalysisStrategy strategy) {
        super(tokenizer, wordCounter, strategy);
        cache = new Memoizer(computable);
        logger.debug("CachableMorphemeAnalyzer created.");
    }

    @Override
    public Eojeol analyzeEojeol(Token token) {
        Eojeol result = null;

        try {
            countWord(token.getString());
            result = cache.compute(token);
        } catch (InterruptedException e) {
            e.printStackTrace();
            logger.error("Eojeol analysis failed.", e);
        }

        return result;
    }

}
