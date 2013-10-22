package com.datastreams.nlp.ma.strategy;

import java.util.List;

/**
 *
 * User: shkim
 * Date: 13. 10. 4
 * Time: 오후 2:07
 *
 */
public class NullWordCountStrategy implements WordCountStrategy {

    @Override
    public Integer count(String word) {
        return null;
    }

    @Override
    public Integer frequencyOfUse(String word) {
        return null;
    }

    @Override
    public List<String> words() {
        return null;
    }
}
