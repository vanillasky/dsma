package com.datastreams.nlp.ma.analyzer;

import com.datastreams.nlp.ma.model.MorphemeList;

import java.util.List;

/**
 * 문법 형태소 분리를 위한 인터페이스
 *
 * User: shkim
 * Date: 13. 10. 23
 * Time: 오후 5:01
 *
 */
public interface MorphemeSeparator {

    public List<MorphemeList> generateCandidates();
}
