package kr.co.datastreams.dsma.ma.tokenizer;

import kr.co.datastreams.dsma.ma.model.PlainSentence;


import java.util.List;

/**
 * 주어진 문자열을 Sentence list로 만든다
 *
 * User: shkim
 * Date: 13. 9. 12
 * Time: 오전 10:46
 *
 */
public interface SentenceSeparator {

    List<PlainSentence> separate(String source);
}
