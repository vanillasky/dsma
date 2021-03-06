package com.datastreams.nlp.ma.test;

/**
 *
 * User: shkim
 * Date: 13. 10. 14
 * Time: 오전 10:14
 *
 */


import com.datastreams.nlp.ma.test.analyzer.NounAnalyzer_SplitJosa_Test;
import com.datastreams.nlp.ma.test.analyzer.RuleBaseTokenAnalyzerTest;
import com.datastreams.nlp.ma.test.common.MemoizerTest;
import com.datastreams.nlp.ma.test.common.TrieTest;
import com.datastreams.nlp.ma.test.common.WordCounterTest;
import com.datastreams.nlp.ma.test.dic.AnalyzedDicTest;
import com.datastreams.nlp.ma.test.dic.DictionaryTest;
import com.datastreams.nlp.ma.test.dic.SyllableDicTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    MemoizerTest.class
    , ConfigurationTest.class
    , SyllableDicTest.class
    , DictionaryTest.class
    , TrieTest.class
    , TokenTest.class
    , PosTagTest.class
    , CharTypeTokenizerTest.class
    , MorphemeTest.class
    , FilterTokenPatternTest.class
    , WordCounterTest.class
    , DictionaryTest.class
    , AnalyzedDicTest.class
    , HangulTest.class
    , MorphemeTest.class
    , NounAnalyzer_SplitJosa_Test.class
    , RuleBaseTokenAnalyzerTest.class

})

public class AllTests {
}
