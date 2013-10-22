package com.datastreams.nlp.ma.test.common;

import com.datastreams.nlp.common.cache.Memoizer;
import com.datastreams.nlp.common.cache.Computable;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Memoizer tests
 *
 * User: shkim
 * Date: 13. 10. 14
 * Time: 오전 10:15
 *
 */
public class MemoizerTest {

    final static String echoString = " from cache";
    private static class Dummy {
        public String echoWith(String word) {
            return word + echoString;
        }
    }

    Dummy dummy = new Dummy();
    Computable<String, String> computable = new Computable<String, String>() {
        @Override
        public String compute(String arg) throws InterruptedException {
            return dummy.echoWith(arg);
        }
    };

    Memoizer<String, String> cache = new Memoizer<String, String>(computable);

    @Test
    public void testCompute() throws Exception {
        String text = "감기는";
        assertNull(cache.get(text));
        String result = cache.compute(text);
        assertEquals(text + echoString, result);
        assertEquals(text + echoString, cache.get(text).get());
    }

}
