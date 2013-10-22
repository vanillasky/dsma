package com.datastreams.nlp.ma.analyzer;



import com.datastreams.nlp.ma.constants.PosTag;
import com.datastreams.nlp.ma.constants.WordPattern;
import com.datastreams.nlp.ma.dic.WordEntry;
import com.datastreams.nlp.ma.model.Eojeol;
import com.datastreams.nlp.ma.model.Morpheme;
import com.datastreams.nlp.ma.model.MorphemeList;
import com.datastreams.nlp.ma.model.Token;
import com.datastreams.nlp.ma.dic.Dictionary;

import com.datastreams.nlp.ma.constants.Score;


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


//    public static Eojeol search(Token token) {
//        MorphemeList verb = estimateVerb(token);
//        MorphemeList noun = estimateNoun(token);
//        List<MorphemeList> candidates = new ArrayList<MorphemeList>();
//
//        if (verb != null) {
//            candidates.add(verb);
//        }
//
//        if (noun != null) {
//            candidates.add(noun);
//        }
//
//        if (candidates.size() > 0) {
//            return Eojeol.createHeuristic(token, candidates);
//        }
//
//        return Eojeol.EMPTY;
//    }

//    //두 음절 이상의 단어는 처음 세,네, 두음절 순으로 용언을, 나머지를 어미로 가정
//    public static MorphemeList estimateVerb(Token token) {
//        int[] tailLocations = {3,4,2};
//        MorphemeList candidate = null;
//        String word = token.getString();
//
//        if (word.length() > 2) {
//            for (int i=0; i < tailLocations.length; i++) {
//                candidate = verifyVerb(token, tailLocations[i]);
//                if (candidate != null) {
//                    break;
//                }
//            }
//        }
//
//        return candidate == null ? MorphemeList.EMPTY : candidate;
//    }
//
//    //두 음절 이상의 단어는 처음 두,세,네음절 순으로 체언을, 나머지를 조사로 가정
//    public static MorphemeList estimateNoun(Token token) {
//        int[] josaLocations = {2,3,4};
//        MorphemeList candidate = null;
//        String word = token.getString();
//
//        if (word.length() > 2) {
//            for (int i=0; i < josaLocations.length; i++) {
//                candidate = verifyNoun(token, josaLocations[i]);
//                if (candidate != null) {
//                    break;
//                }
//            }
//        }
//
//        return candidate == null ? MorphemeList.EMPTY : candidate;
//    }

    /**
     * 두 음절 이상의 단어는 처음 두,세,네음절 순으로 체언을, 나머지를 조사로 가정하여 조사 사전을 검색한다.
     * 조사가 발견되면 체언/독립언/수식언 어휘형태소를 찾아본다.
     * 어휘 형태소와 조사가 사전에 없으면 MorphemeList.EMPTY를 리턴한다.
     *
     * @param token
     * @return 어휘 + 문법형태소 구조체 or MorphemeList.EMPTY if josa doesn't registered.
     */
    public static MorphemeList estimateJosa(Token token) {
        int[] josaLocations = {2,3,4};
        MorphemeList candidate = null;
        String word = token.getString();

        if (word.length() > 2) {
            for (int i=0; i < josaLocations.length; i++) {
                candidate = verifyJosa(token, josaLocations[i]);
                if (!candidate.isEmpty()) {
                    break;
                }
            }
        }

        return candidate == null ? MorphemeList.EMPTY : candidate;
    }


    public static MorphemeList estimateEomi(Token token) {
        int[] tailLocations = {3, 4, 2};
        MorphemeList candidate = null;
        String word = token.getString();

        if (word.length() > 2) {
            for (int i=0; i < tailLocations.length; i++) {
                candidate = verifyEomi(token, tailLocations[i]);
                if (!candidate.isEmpty()) {
                    break;
                }
            }
        }

        return candidate == null ? MorphemeList.EMPTY : candidate;
    }




//    private static MorphemeList verifyVerb(Token token, int start) {
//        return verify(token, start, true);
//    }
//
//
//    private static MorphemeList verifyNoun(Token token, int start) {
//        return verify(token, start, false);
//    }


//    private static MorphemeList verify(Token token, int start, boolean checkVerb) {
//        String word = token.getString();
//        if (word.length() <= start) {
//            return null;
//        }
//
//        String headPart = word.substring(0, start);
//        String tailPart = word.substring(start);
//
//        WordEntry head = checkVerb ? Dictionary.searchVerb(headPart) : Dictionary.searchNoun(headPart);
//        WordEntry tail = checkVerb ? Dictionary.searchEomi(tailPart) : Dictionary.searchJosa(tailPart);
//
//        if (head != null && tail != null) {
//            Morpheme h = new Morpheme(headPart, checkVerb ? PosTag.decodeVerb(head.tag()) : PosTag.decodeNoun(head.tag()));
//            Morpheme t = new Morpheme(tailPart, tail.tag());
//
//            MorphemeList result = MorphemeList.create(Score.Analysis, h, t);
//            return result;
//        }
//
//        return MorphemeList.EMPTY;
//    }


    private static MorphemeList verifyJosa(Token token, int start) {
        return verify(token, start, false, false);
    }


    private static MorphemeList verifyEomi(Token token, int start) {
        return verify(token, start, true, false);
    }

    public static MorphemeList verify(Token token, int start, boolean checkVerb, boolean verbose) {

        String word = token.getString();
        if (word.length() <= start) {
            return MorphemeList.EMPTY;
        }

        String headPart = word.substring(0, start);
        String tailPart = word.substring(start);

        WordEntry tail = checkVerb ? Dictionary.searchEomi(tailPart) : Dictionary.searchJosa(tailPart);

        if (tail != null) {
            Morpheme h;
            Morpheme t = new Morpheme(tailPart, tail.tag());

            WordEntry head = checkVerb ? Dictionary.searchVerb(headPart) : Dictionary.searchFixedWord(headPart);
            Score score = Score.Candidate;
            WordPattern pattern = WordPattern.NJ;

            if (head != null) {
                String tag = checkVerb ? PosTag.decodeVerb(tail.tag()) : PosTag.decodeNoun(head.tag()) == null ? PosTag.decodeAdverb(head.tag()) : PosTag.decodeNoun(head.tag());
                pattern = PosTag.isTagOf(head.tag(), PosTag.AD) ? WordPattern.ADVJ : pattern;

                h = new Morpheme(headPart, tag);
                score = Score.Analysis;
            }
            else if (verbose) {
//                score = tail.getString().length() > 2 ? Score.Analysis : score;
                head = WordEntry.createWithTag(headPart, checkVerb ? PosTag.NV : PosTag.NF);
                h = new Morpheme(headPart, head.tag());
            }
            else {
                return MorphemeList.EMPTY;
            }

            MorphemeList result = MorphemeList.create(score, pattern, h, t);
            return result;

        }

        return MorphemeList.EMPTY;
    }
}
