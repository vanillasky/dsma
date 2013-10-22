package com.datastreams.nlp.ma.analyzer;

import com.datastreams.nlp.ma.model.Eojeol;
import com.datastreams.nlp.ma.model.MorphemeList;
import com.datastreams.nlp.ma.model.Token;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 10. 18
 * Time: 오후 3:19
 * To change this template use File | Settings | File Templates.
 */
public interface TokenAnalyzer {
    public Eojeol execute(Token token);

}
