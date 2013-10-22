package com.datastreams.nlp.ma.util;

import com.datastreams.nlp.ma.model.Eojeol;
import com.datastreams.nlp.ma.model.Morpheme;
import com.datastreams.nlp.ma.model.MorphemeList;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 *
 * User: shkim
 * Date: 13. 10. 18
 * Time: 오전 11:00
 *
 */
public class SimpleScoreFormatter implements EojeolFormatter  {

    @Override
    public String format(Eojeol eojeol) {
        StringBuilder sb  = new StringBuilder();
        sb.append(eojeol.getSource()).append(eojeol.getScore()).append("\t\t=> ");

        List<MorphemeList> candidates = eojeol.getMorphemes();
        Collections.sort(candidates);

        int len = sb.length();
        int i=0;
        for (MorphemeList each : candidates) {
            if (i == 0) {
                sb.append(each);
            } else {
                sb.append("\n");
                sb.append(lpad(' ', len+5)).append(each);
            }

            i++;
        }
//        sb.append("\n");

        return sb.toString();
    }

    private String lpad(char padChar, int len) {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i < len; i++) {
            sb.append(padChar);
        }

        return sb.toString();
    }
}
