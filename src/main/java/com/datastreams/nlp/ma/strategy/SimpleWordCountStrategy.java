package com.datastreams.nlp.ma.strategy;


import com.datastreams.nlp.common.util.WordCounter;

import java.util.List;


/**
 *
 * User: shkim
 * Date: 13. 10. 4
 * Time: 오후 2:08
 *
 */
public class SimpleWordCountStrategy implements WordCountStrategy {

    private WordCounter counter = new WordCounter();


    @Override
    public Integer count(String word) {
        return counter.count(word);
    }

    @Override
    public Integer frequencyOfUse(String word) {
        return counter.gerFrequencyOfUse(word);
    }

    @Override
    public List<String> words() {
        return counter.words();
    }
}
