package kr.co.datastreams.dsma.util;

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


//    public List<String> words() {
//        List<WordRank> result = new ArrayList<WordRank>(counter.size());
//        Iterator<String> keys = counter.keySet().iterator();
//        while (keys.hasNext()) {
//            String key = keys.next();
//            result.add(new WordRank(key, counter.get(key)));
//        }
//
//        Collections.sort(result);
//        for (WordRank each : result) {
//            System.out.println(each);
//        }
//
//        return null;
//    }

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
