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
}
