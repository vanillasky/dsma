package com.datastreams.nlp.ma.dic;

import com.datastreams.nlp.ma.constants.PosTag;
import kr.co.datastreams.dsma.util.Hangul;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 10. 17
 * Time: 오후 5:42
 * To change this template use File | Settings | File Templates.
 */
public class VerbDicExpander {


    public static List<WordEntry> expand(WordEntry wordEntry) {
        List<WordEntry> entries = new ArrayList<WordEntry>();

        String stem = wordEntry.getString();
        int stemLength = stem.length();
        String preStem = stem.substring(0, stemLength-1);

        char lastCh = stem.charAt(stemLength-1);
        char preLastCh;
        Hangul lastSyllable = Hangul.split(lastCh);
        Hangul preLastSyllable = null;


        WordEntry entry;
        if (stemLength > 1) {
            preLastCh = stem.charAt(stemLength-2);
            preLastSyllable = Hangul.split(preLastCh);
        } else {
            preLastCh = 0;
        }

        // 1음절 용언 && 종성 없음 && 초성이 'ㅎ' 아닌 단어 ex)'가'
        if (stem.length() == 1 && !lastSyllable.hasJong() && lastSyllable.cho != 'ㅎ') {
            if (lastSyllable.jung == 'ㅏ') {
                entry = wordEntry.add("아", PosTag.EC);
                entries.add(entry);
            } else if (lastSyllable.jung == 'ㅓ') {
                entry = wordEntry.add("어", PosTag.EC);
                entries.add(entry);
            }
        }



        return entries;
    }
}
