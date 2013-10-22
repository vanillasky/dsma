package com.datastreams.nlp.ma.analyzer;


import com.datastreams.nlp.common.annotation.ThreadSafe;
import com.datastreams.nlp.common.cache.Computable;
import com.datastreams.nlp.common.cache.Memoizer;
import com.datastreams.nlp.ma.model.Eojeol;
import com.datastreams.nlp.ma.model.Token;
import com.datastreams.nlp.ma.strategy.WordCountStrategy;
import com.datastreams.nlp.ma.tokenizer.Tokenizer;
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
            return tokenAnalyzer.execute(arg);
        }
    };

    public CachableMorphemeAnalyzer(Tokenizer tokenizer, TokenAnalyzer tokenAnalyzer, WordCountStrategy wordCountStrategy) {
        super(tokenizer, tokenAnalyzer, wordCountStrategy);
        cache = new Memoizer(computable);
        logger.debug("CachableMorphemeAnalyzer created.");
    }

    @Override
    public Eojeol analyzeEojeol(Token token) {
        Eojeol result = searchAnalyzedDic(token);
        if (result != null) {
            return result;
        }

        try {
            result = cache.compute(token);
        } catch (InterruptedException e) {
            e.printStackTrace();
            logger.error("Eojeol analysis failed.", e);
        }

        return result;
    }

}
