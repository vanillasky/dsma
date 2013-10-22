package com.datastreams.nlp.common.util;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 *
 * User: shkim
 * Date: 13. 9. 26
 * Time: 오전 9:24
 *
 */
public class WordCounter {

    private final Map<String, Integer> counter = new ConcurrentHashMap<String, Integer>();


    public WordCounter() { }


    public synchronized Integer count(String word) {
        Integer count = counter.get(word);
        if (count == null) {
            counter.put(word, 1);
        } else {
            count = count + 1;
            counter.put(word, count);
        }
        return count;
    }

    public Integer gerFrequencyOfUse(String arg) {
        return counter.get(arg);
    }


    public List<String> words() {
        List<WordRank> wordRanks = new ArrayList<WordRank>(counter.size());
        Iterator<String> keys = counter.keySet().iterator();
        while (keys.hasNext()) {
            String key = keys.next();
            wordRanks.add(new WordRank(key, counter.get(key)));
        }

        Collections.sort(wordRanks);
        List<String> result = new ArrayList<String>(wordRanks.size());
        for (WordRank each : wordRanks) {
            result.add(each.toString());
        }
        wordRanks.clear();

        return Collections.unmodifiableList(result);
    }

    class WordRank implements Comparable<WordRank>{
        public String word;
        public int frequency;

        public WordRank(String word, int frequency) {
            this.word = word;
            this.frequency = frequency;
        }

        public String toString() {
            return word + "=" + frequency;
        }


        @Override
        public int compareTo(WordRank o) {
            return o.frequency - this.frequency;
        }
    }
}
