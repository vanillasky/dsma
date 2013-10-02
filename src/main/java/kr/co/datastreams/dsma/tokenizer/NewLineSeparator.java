package kr.co.datastreams.dsma.tokenizer;

import kr.co.datastreams.dsma.model.PlainSentence;

import java.util.ArrayList;
import java.util.List;

/**
 * 개행문자(\n)를 구분자로 각 Sentence를 만들고 index를 부여한다.
 *
 * User: shkim
 * Date: 13. 9. 12
 * Time: 오전 10:47
 *
 */
public class NewLineSeparator implements SentenceSeparator {


    @Override
    public List<PlainSentence> separate(String source) {
        String[] lines = source.split("\\n");
        List<PlainSentence> sentences = new ArrayList<PlainSentence>(lines.length);
        int index = 0;
        for (String each : lines) {
            sentences.add(new PlainSentence(index++, each));
        }

        return sentences;
    }
}
