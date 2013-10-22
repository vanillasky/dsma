package com.datastreams.nlp.ma.tokenizer;



import com.datastreams.nlp.ma.model.Token;

import java.util.List;

/**
 *
 * Tokenizer break up text into individual Tokens.
 *
 * User: shkim
 * Date: 13. 9. 16
 * Time: 오후 3:39
 *
 */
public interface Tokenizer {

    /**
     * Break up the text into individual Tokens.
     *
     * @param text - string to tokenize
     * @return tokens
     */
    List<Token> tokenize(String text);
}
