package com.datastreams.nlp.ma.analyzer;

import com.datastreams.nlp.ma.constants.PosTag;
import com.datastreams.nlp.ma.constants.Score;
import com.datastreams.nlp.ma.constants.WordPattern;
import com.datastreams.nlp.ma.dic.Dictionary;
import com.datastreams.nlp.ma.dic.SyllableDic;
import com.datastreams.nlp.ma.dic.WordEntry;
import com.datastreams.nlp.ma.model.Eojeol;
import com.datastreams.nlp.ma.model.Morpheme;
import com.datastreams.nlp.ma.model.MorphemeList;
import com.datastreams.nlp.ma.model.Token;
import com.datastreams.nlp.ma.util.HangulUtil;
import kr.co.datastreams.commons.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 규칙기반 어절 형태소 분석기
 *
 * 1) 입력 어절에 대해 조사 위치를 추정한다.
 *
 * 1.1) 한국어 음절 특성상 2,3,4음절의 체언의 90%정도를 차지하므로
 *      조사의 위치를 각각 3,4,5번째 음절로 가정하여 조사 사전을 검색하여
 *      등록된 조사가 있고, 체언후보의 마지막 음절이 조사가 붙을 수 있는 음절이면
 *      후보를 생성하여 후보 리스트에 추가 한다.
 *
 * 1.2) 1.1에서 조사 추정이 실패하면 어절을 오른쪽에서 왼쪽으로 탐색하면서
 *      각 음절이 조사의 첫번째 음절로 사용될 수 있는지 확인하여 가능한 음절이고,
 *      체언 후보의 마지막 음절이 조사가 붙을 수 있는 음절이면 후보를 생성한다.
 *
 * 1.3) '에'로 시작되는 조사는 자주 사용되면서 분석 후보의 과생성을 많이 발생하는 문법형태소 이다.
 *       명사 중에서 '에'로 끝나는 것은 드물기 때문에 '에'앞의 음절이 '누/멍/성/기/보/니/리'인 경우멘
 *       형태소 분리 후보를 2가지로 생성하고 그외의 경우에는 최장 문법형태소를 분리한 후보 하나만 생성한다.
 *
 *
 * User: shkim
 * Date: 13. 10. 18
 * Time: 오후 3:24
 *
 */
public class RuleBaseTokenAnalyzer implements TokenAnalyzer {

    private static final Logger logger = LoggerFactory.getLogger(RuleBaseTokenAnalyzer.class);
    private static final boolean VERBOSE = true;
    private final Estimator estimator = new Estimator();

    @Override
    public Eojeol execute(Token token) {

        List<MorphemeList> candidates = estimator.estimate(token);
        if (candidates.size() > 0) {
            System.out.println("Heuristic search 성공");
        } else {
            candidates = scanRightToLeft(token);
        }


        Eojeol result = new Eojeol(token, Score.Analysis, candidates);
        //System.out.println(result.asMorphemeString());

        return result;
    }

    /**
     *
     * @param token
     * @return
     */
    private List<MorphemeList> scanRightToLeft(Token token) {
        boolean josaFlag = true;
        boolean eomiFlag = true;
        List<MorphemeList> candidates = new ArrayList<MorphemeList>();

        String inputString = token.getString();

        for (int i=inputString.length()-1; i > 0; i--) {
            String head = inputString.substring(0, i);
            String tail = inputString.substring(i);
            char firstCharOfTail = tail.charAt(0);

            if (josaFlag && SyllableDic.isFirstJosaSyllable(firstCharOfTail)) {
                List<MorphemeList> josaCandidates = analyzeWithJosa(head, tail);
                if (Constraints.filterWithLengthOfTail(candidates, josaCandidates)) {
                    break;
                }
            }

            if (eomiFlag) {
                List<MorphemeList> eomiCandidates = analyzeWithEomi(head, tail);
                if (Constraints.filterWithLengthOfTail(candidates, eomiCandidates)) {
                    break;
                }
            }

            // 조사의 두 번째 이상의 음절로 사용될 수 있는지 확인
            if (!SyllableDic.isSecondJosaSyllable(firstCharOfTail)) {
                josaFlag = false;
            }

            // 어미의 두 번째 이상의 음절로 사용될 수 있는지 확인
            if (!SyllableDic.isSecondEndingSyllable(firstCharOfTail)) {
                eomiFlag = false;
            }

            if (josaFlag == false && eomiFlag == false) {
                break;
            }
        }

        // 최장 문법 형태소 순으로 정렬
        Collections.sort(candidates, new Comparator<MorphemeList>() {
            @Override
            public int compare(MorphemeList o1, MorphemeList o2) {
            return o2.getLast().length() - o1.getLast().length();
            }
        });

        return candidates;
    }

    private List<MorphemeList> analyzeWithEomi(String head, String tail) {
        if (StringUtil.nvl(head).length() == 0 || StringUtil.nvl(tail).length() == 0) return Collections.EMPTY_LIST;


        return Collections.EMPTY_LIST;
    }

    private List<MorphemeList> analyzeWithJosa(String head, String tail) {
        if (StringUtil.nvl(tail).length() == 0) return Collections.EMPTY_LIST;
        char lastCharOfHead = head.charAt(head.length() - 1);

        if (!HangulUtil.isJosaAppendable(lastCharOfHead, tail)) {
            return Collections.EMPTY_LIST;
        }

        List<MorphemeList> candidates = new ArrayList<MorphemeList>();

        MorphemeList candidate = applyNJPattern(head, tail);
        if (!candidate.isEmpty()) {
            candidates.add(candidate.copy());
        }


        return candidates;
    }



    private MorphemeList applyNJPattern(String head, String tail) {

        WordEntry josaEntry =  Dictionary.searchJosa(tail);
        if (josaEntry == null) {
            return MorphemeList.EMPTY;
        }

        MorphemeList morphemeList = confirmNounOrAdverb(head, tail, josaEntry);
        if (!morphemeList.isEmpty()) {
            return morphemeList;
        }

        return MorphemeList.EMPTY;
    }


    /**
     * 명사+조사 또는 부사+조사 인지 확정한다.
     * head 부분을 사전에서 찾을 수 없을 때에는 조사가 2음절 이상인 경우에는 명사추정 범주로,
     * 2음절 보다 작은 경우에는 MorphemeList.EMPTY를 반환한다.
     *
     * @param head
     * @param tail
     * @param josaEntry
     * @return
     */
    private MorphemeList confirmNounOrAdverb(String head, String tail, WordEntry josaEntry) {
        Morpheme h;
        Morpheme t = new Morpheme(tail, PosTag.decodeJosa(josaEntry.tag()));

        WordEntry headEntry = Dictionary.searchNounOrAbVerb(head);
        WordPattern pattern = WordPattern.NJ;

        if (headEntry != null) {
            if (PosTag.decodeNoun(headEntry.tag()) == null) { // 부사 + 조사
                h = new Morpheme(head, PosTag.decodeAdverb(headEntry.tag()));
                pattern = WordPattern.ADVJ;
            } else {
                h = new Morpheme(head, PosTag.decodeNoun(headEntry.tag())); // 명사 + 조사
            }

            return MorphemeList.create(Score.Success, pattern, h, t);
        }


        if (josaEntry.getString().length() > 1) {
            h = new Morpheme(head, PosTag.NF);
            return MorphemeList.create(Score.Candidate, pattern, h, t);
        }

        return MorphemeList.EMPTY;
    }

    /**
     * 단일어 처리
     * @param token
     * @param candidates
     */
    public void buildSingleWord(Token token, List<MorphemeList> candidates) {
        WordEntry entry = Dictionary.searchFixedWord(token.getString());

//        if (entry != null) {
//            MorphemeList morphemeList = new MorphemeList(Score.Success);
//
//            long tagNum = PosTag.NNG;
//            if (entry.isTagOf(PosTag.N)) {
//                tagNum = PosTag.extractNounTag(entry.tag());
//                morphemeList.setWordPattern(WordPattern.N);
//            }
//            else if (entry.isTagOf(PosTag.AD)) {
//                tagNum = PosTag.extractAdverbTag(entry.tag());
//                morphemeList.setWordPattern(WordPattern.AID);
//            }
//            else if(entry.isTagOf(PosTag.IC)) {
//                tagNum = entry.tag();
//                morphemeList.setWordPattern(WordPattern.AID);
//            }
//
//            Morpheme m = new Morpheme(token.getString(), tagNum);
//            morphemeList.add(m);
//
//            candidates.add(morphemeList);
//        } else {
//            //TODO: 접미사가 분리되는 경우에는 접미사 분리 후에 어휘 사전 확인
//            String word = token.getString();
//            for (int i=word.length()-1, j=0; i > 0; i--,j++) {
//                if (j > 1) break;
//                String suffixCandidate = word.substring(i, word.length());
//
//                WordEntry suffix = SuffixDic.find(suffixCandidate);
//                if (suffix != null) {
//                    System.out.println("접미사 찾기("+word+"):" + suffix);
//                }
//            }
//        }
    }
}
