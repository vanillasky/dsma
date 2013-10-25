package com.datastreams.nlp.ma.test;


import com.datastreams.nlp.ma.constants.CharType;
import com.datastreams.nlp.ma.model.Token;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 *
 * User: shkim
 * Date: 13. 9. 16
 * Time: 오후 3:12
 *
 */
public class TokenTest {

    @Test
    public void testEquals() throws Exception {
        Token token = new Token("첫번째", CharType.HANGUL, 0);
        assertTrue(token.equalsString("첫번째"));
    }

    @Test
    public void testCopy() throws Exception {
        Token token = new Token("첫번째", CharType.HANGUL, 0);
        Token copy = token.copy();
        assertTrue(token != copy);
    }

    @Test
    public void testCompareTo() throws Exception {
        Token token = new Token("첫번째", CharType.HANGUL, 0);
        Token token2 = new Token("두번째", CharType.HANGUL, 1);
        List<Token> tokens = new ArrayList<Token>();
        tokens.add(token2);
        tokens.add(token);
        Collections.sort(tokens);
        assertEquals(token, tokens.get(0));
    }

    @Test
    public void testSplit() throws Exception {
        Token token = new Token("첫번째", CharType.HANGUL, 0);
        String[] chunk = token.split(0);
        assertEquals("첫번째", chunk[1]);

        chunk = token.split(1);
        assertEquals("첫", chunk[0]);
        assertEquals("번째", chunk[1]);

        chunk = token.split(2);
        assertEquals("첫번", chunk[0]);
        assertEquals("째", chunk[1]);

        Token token2 = Token.korean("학교에서만은");
        chunk = token2.split(-1);
        assertEquals("학교에서만", chunk[0]);
        assertEquals("은", chunk[1]);

    }

    @Test
    public void testScan() throws Exception {
        Token token = Token.korean("학교에서만은");
        String[] expectedHeads = new String[]{"학교", "학교에", "학교에서"};
        String[] expectedTails = new String[]{"에서만은", "서만은", "만은"};

        List<String[]> chunks = token.split(2,3,4);
        int i =0;
        for (String[] each : chunks) {
            assertEquals(expectedHeads[i], each[0]);
            assertEquals(expectedTails[i], each[1]);
            i++;
        }
    }


    @Test
    public void testScan_인덱스초과() throws Exception {
        Token token = Token.korean("학교에서만은");
        String[] expectedHeads = new String[]{"학교", "학교에"};
        String[] expectedTails = new String[]{"에서만은", "서만은"};

        List<String[]> chunks = token.split(2,3,6);
        int i =0;
        for (String[] each : chunks) {
            assertEquals(expectedHeads[i], each[0]);
            assertEquals(expectedTails[i], each[1]);
            i++;
        }
    }




}
