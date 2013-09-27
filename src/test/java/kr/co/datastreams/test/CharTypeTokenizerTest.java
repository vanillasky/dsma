package kr.co.datastreams.test;


import kr.co.datastreams.dsma.ma.tokenizer.CharTypeTokenizer;
import kr.co.datastreams.dsma.ma.model.Token;
import kr.co.datastreams.dsma.ma.tokenizer.Tokenizer;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * User: shkim
 * Date: 13. 7. 22
 * Time: 오후 1:26
 *
 */
public class CharTypeTokenizerTest {

    Tokenizer tokenizer;

    @Before
    public void setUp() throws Exception {
        tokenizer = new CharTypeTokenizer();
    }

    @Test
    public void testEmoticonContains() throws Exception {
        List<Token> tokens = tokenizer.tokenize("ㅜㅜ;비가 많이 와서 옷이 다 졌었다.... ㅜ.ㅜ 축축해 ㅠㅠ");
        String[] expecteds = {"ㅜㅜ;", "비가", " ", "많이", " ", "와서", " ", "옷이", " ", "다", " ", "졌었다", "....", " ", "ㅜ.ㅜ", " ", "축축해", " ", "ㅠㅠ"};

        int i=0;
        for (Token each : tokens) {
//            System.out.println(each);
            assertEquals("["+(i+1)+"]", expecteds[i++], each.getString());
        }

    }

    @Test
    public void testPlainSentence() throws Exception {
        String text = "부동산거래 활성화 효과 하나 없는데 찌라시들 엉터리 보도에 내놓은 코미디 정책.";
        List<Token> tokens = tokenizer.tokenize(text);

        String[] temp = text.split(" ");
        List<String> expectedTokens = new ArrayList<String>();
        for (String each : temp) {
            if(each.endsWith(".")) {
                expectedTokens.add(each.substring(0, each.lastIndexOf(".")));
                expectedTokens.add(".");
            } else {
                expectedTokens.add(each);
                expectedTokens.add(" ");
            }
        }

        int i=0;
        for (Token each : tokens) {
//            System.out.println(each);
            assertEquals("["+(i+1)+"]", expectedTokens.get(i++), each.getString());
        }

    }

    @Test
    public void testPlainSentence_스페이스2개이상() throws Exception {
        String text = "코미디   정책.";
        List<Token> tokens = tokenizer.tokenize(text);
        String[] expectedTokens = {"코미디", "   ", "정책", "."};

        int i=0;
        for (Token each : tokens) {
            assertEquals("["+(i+1)+"]", expectedTokens[i++], each.getString());
        }

    }

    @Test
    public void test_containsNumber() throws Exception {
        String text = "815코미디  정책.";
        List<Token> tokens = tokenizer.tokenize(text);
        String[] expectedTokens = {"815", "코미디", "  ", "정책", "."};

        int i=0;
        for (Token each : tokens) {
            assertEquals("["+(i+1)+"]", expectedTokens[i++], each.getString());
        }

    }

    @Test
    public void test_containsDateFormat() throws Exception {
        String text = "8.15코미디  정책.";
        List<Token> tokens = tokenizer.tokenize(text);
        String[] expectedTokens = {"8.15", "코미디", "  ", "정책", "."};

        int i=0;
        for (Token each : tokens) {
//            System.out.println(each);
            assertEquals("["+(i+1)+"]", expectedTokens[i++], each.getString());
        }

    }
}
