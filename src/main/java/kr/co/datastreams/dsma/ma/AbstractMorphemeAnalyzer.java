package kr.co.datastreams.dsma.ma;

import kr.co.datastreams.commons.util.StringUtil;
import kr.co.datastreams.dsma.ma.api.MorphemeAnalyzer;
import kr.co.datastreams.dsma.ma.model.Sentence;

/**
 *
 * User: shkim
 * Date: 13. 7. 30
 * Time: 오후 3:19
 *
 */
public abstract class AbstractMorphemeAnalyzer implements MorphemeAnalyzer {

    @Override
    public Sentence analyze(String inputString) {
        if (StringUtil.nvl(inputString).trim().length() == 0) {
            return Sentence.emptySentence();
        }

        Sentence sentence = Sentence.create(inputString);

        preProcess(sentence);
        guessTail(sentence);
        confirmHead(sentence);
        postProcess(sentence);

        return sentence;
    }

    protected abstract void preProcess(Sentence inputString);
    protected abstract void guessTail(Sentence sentence);
    protected abstract void confirmHead(Sentence sentence);
    protected abstract void postProcess(Sentence sentence);

}
