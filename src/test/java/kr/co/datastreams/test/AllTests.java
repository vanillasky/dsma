package kr.co.datastreams.test;

import kr.co.datastreams.test.rule.ApocopeRuleTest;
import kr.co.datastreams.test.rule.StartsWithNLMBEndingTest;
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
    , ApocopeRuleTest.class
    , StartsWithNLMBEndingTest.class
})
public class AllTests {
}
