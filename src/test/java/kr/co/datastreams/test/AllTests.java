package kr.co.datastreams.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 7. 22
 * Time: 오전 11:10
 * To change this template use File | Settings | File Templates.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses( {
    TrieTest.class
    , DictionaryTest.class
    , WordEntryComposerTest.class
    , RuleBaseVerbAnalyzerTest.class
    , HangulTest.class
    , SyllableDicTest.class
    , TokenizerTest.class
})
public class AllTests {
}
