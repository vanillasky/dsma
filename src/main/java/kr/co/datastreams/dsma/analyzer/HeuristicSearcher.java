package kr.co.datastreams.dsma.analyzer;

import kr.co.datastreams.dsma.dic.Dictionary;
import kr.co.datastreams.dsma.dic.EomiDic;
import kr.co.datastreams.dsma.dic.JosaDic;
import kr.co.datastreams.dsma.model.PosTag;
import kr.co.datastreams.dsma.model.*;

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


    public static Eojeol search(Token token) {
        MorphemeList verb = analyzeVerb(token);
        MorphemeList noun = analyzeNoun(token);
        List<MorphemeList> morphemeLists = new ArrayList<MorphemeList>();

        if (verb != null) {
            morphemeLists.add(verb);
        }

        if (noun != null) {
            morphemeLists.add(noun);
        }

        if (morphemeLists.size() > 0) {
            return Eojeol.createHeuristic(token, morphemeLists);
        }

        return null;
    }

    //두 음절 이상의 단어는 처음 세,네, 두음절 순으로 용언을, 나머지를 어미로 가정
    public static MorphemeList analyzeVerb(Token token) {
        int[] tailLocations = {3,4,2};
        MorphemeList candidate = null;
        String word = token.getString();

        if (word.length() > 3) {
            for (int i=0; i < tailLocations.length; i++) {
                candidate = verifyVerb(token, tailLocations[i]);
                if (candidate != null) {
                    break;
                }
            }
        }

        return candidate;
    }

    //두 음절 이상의 단어는 처음 두,세,네, 두음절 순으로 체언을, 나머지를 조사로 가정
    public static MorphemeList analyzeNoun(Token token) {
        int[] tailLocations = {2,3,4};
        MorphemeList candidate = null;
        String word = token.getString();

        if (word.length() > 2) {
            for (int i=0; i < tailLocations.length; i++) {
                candidate = verifyNoun(token, tailLocations[i]);
                if (candidate != null) {
                    break;
                }
            }
        }

        return candidate;
    }

    private static MorphemeList verifyVerb(Token token, int start) {
        return verify(token, start, true);
    }


    private static MorphemeList verifyNoun(Token token, int start) {
        return verify(token, start, false);
    }

    private static MorphemeList verify(Token token, int start, boolean checkVerb) {
        String word = token.getString();
        if (word.length() <= start) {
            return null;
        }

        String headPart = word.substring(0, start);
        String tailPart = word.substring(start);

        WordEntry head = checkVerb ? Dictionary.getVerb(headPart) : Dictionary.getNoun(headPart);
        WordEntry tail = checkVerb ? EomiDic.search(tailPart) : JosaDic.search(tailPart);

        if (head != null && tail != null) {
            Morpheme h = new Morpheme(headPart, checkVerb ? PosTag.decodeVerb(head.tag()) : PosTag.decodeNoun(head.tag()));
            Morpheme t = new Morpheme(tailPart, tail.tag());

            MorphemeList result = new MorphemeList(Score.Heuristic);
            result.add(h);
            result.add(t);
            return result;
        }

        return null;
    }

}
