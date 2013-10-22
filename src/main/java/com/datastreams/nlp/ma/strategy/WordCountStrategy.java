package com.datastreams.nlp.ma.strategy;

import java.util.List;

/**
 *
 * User: shkim
 * Date: 13. 10. 4
 * Time: 오후 2:02
 *
 */
public interface WordCountStrategy {
    public Integer count(String word);
    public Integer frequencyOfUse(String word);
    public List<String> words();
}
