package kr.co.datastreams.dsma.ma.analyzer;

import kr.co.datastreams.dsma.dic.Dictionary;
import kr.co.datastreams.dsma.dic.EomiDic;
import kr.co.datastreams.dsma.ma.WordPattern;
import kr.co.datastreams.dsma.ma.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * User: shkim
 * Date: 13. 9. 27
 * Time: 오후 3:39
 *
 */
public class HeuristicSearcher {

    public Eojeol search(Token token) {
        Eojeol result = analyzeVerb(token);
        if (result != null) {
            return result;
        }

        //result = analyzeNoun(word);
        return result;
    }

    //두 음절 이상의 단어는 처음 세,네, 두음절 순으로 용언을, 나머지를 어미로 가정
    public Eojeol analyzeVerb(Token token) {
        int[] tailLocations = {3,4,2};
        Eojeol candidate = null;
        String word = token.getString();

        if (word.length() > 3) {
            for (int i=0; i < tailLocations.length; i++) {
                candidate = confirmVerb(token, tailLocations[i]);
                if (candidate != null) {
                    break;
                }
            }
        }

        return candidate;
    }

    private Eojeol confirmVerb(Token token, int start) {
        String word = token.getString();
        if (word.length() <= start) {
            return null;
        }

        Eojeol candidate = null;
        String stemPart = word.substring(0, start);
        String endingPart = word.substring(start);

        WordEntry entry = Dictionary.getVerb(stemPart);
        WordEntry ending = Dictionary.findEnding(endingPart);

        if (entry != null && EomiDic.exists(endingPart)) {
            List<MorphemeList> morphemes = new ArrayList<MorphemeList>(1);

            Morpheme v = new Morpheme(stemPart, entry.tag());
            Morpheme m = new Morpheme(endingPart, ending.tag());
            MorphemeList morphemeList = new MorphemeList();
            morphemeList.add(v);
            morphemeList.add(m);
            morphemes.add(morphemeList);

            candidate = new Eojeol(word, token.getCharType(), token.getIndex(), Eojeol.ResultCode.ASSUMPTION, morphemes);
        }

        return candidate;
    }
}
