package kr.co.datastreams.test;


import kr.co.datastreams.dsma.ma.Token;
import kr.co.datastreams.dsma.ma.Tokenizer;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 7. 22
 * Time: 오후 1:26
 * To change this template use File | Settings | File Templates.
 */
public class TokenizerTest {

    @Test
    public void testEmoticonContains() throws Exception {
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize("ㅜㅜ; 비가 많이 와서 옷이 다 졌었다.... ㅜ.ㅜ 축축해 ㅠㅠ");
        String[] expecteds = {"ㅜㅜ;", " ", "비가", " ", "많이", " ", "와서", " ", "옷이", " ", "다", " ", "졌었다", "....", " ", "ㅜ.ㅜ", " ", "축축해", " ", "ㅠㅠ"};

        int i=0;
        for (Token each : tokens) {
            System.out.println("["+(i+1)+"]"+each);
            assertEquals("["+(i+1)+"]", expecteds[i++], each.getString());
        }

    }
}
