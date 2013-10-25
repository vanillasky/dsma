package com.datastreams.nlp.ma.analyzer;

import com.datastreams.nlp.ma.model.Eojeol;
import com.datastreams.nlp.ma.model.MorphemeList;
import com.datastreams.nlp.ma.model.Token;

import java.util.List;

/**
 *
 * 어절단위 형태소 분석기
 *
 * 입력 어절(Token)을 받아서 형태소 분석결괴인 Eojeol 객체를 돌려준다.
 *
 *
 * User: shkim
 * Date: 13. 10. 18
 * Time: 오후 3:19
 *
 */
public interface TokenAnalyzer {
    public Eojeol execute(Token token);

}
