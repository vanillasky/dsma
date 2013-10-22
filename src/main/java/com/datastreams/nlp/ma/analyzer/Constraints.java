package com.datastreams.nlp.ma.analyzer;

import com.datastreams.nlp.ma.constants.PosTag;
import com.datastreams.nlp.ma.constants.Score;
import com.datastreams.nlp.ma.dic.Dictionary;
import com.datastreams.nlp.ma.dic.SyllableDic;
import com.datastreams.nlp.ma.dic.WordEntry;
import com.datastreams.nlp.ma.model.Morpheme;
import com.datastreams.nlp.ma.model.MorphemeList;
import com.datastreams.nlp.ma.model.Token;
import com.datastreams.nlp.ma.util.HangulUtil;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 형태소 처리관련 제약
 *
 * User: shkim
 * Date: 13. 10. 22
 * Time: 오후 5:33
 *
 */
public class Constraints {

    // [형태소분리제약1]에 의해 2음절 이상의 조사 또는 어미가 분리된 어절은 다른 가능성을 고려하지 않는다.
    // resources/conf/constraints 참조
    public static boolean filterWithLengthOfTail(List<MorphemeList> candidates, List<MorphemeList> mediateCandidates) {
        if (!mediateCandidates.isEmpty()) {
            Collections.sort(mediateCandidates, new Comparator<MorphemeList>() {
                @Override
                public int compare(MorphemeList o1, MorphemeList o2) {
                    return o2.getLast().length() - o1.getLast().length();
                }
            });

            MorphemeList longestTail = mediateCandidates.get(0);
            if (longestTail.getLast().length() > 2) {
                candidates.clear();
                candidates.add(longestTail);
                return true;
            } else {
                candidates.addAll(mediateCandidates);
            }
        }

        return false;
    }


    /**
     * 조사의 첫음절로 사용될 수 있는지, head의 마지막 음절이 tail과 결합 가능한지 확인한다.
     * @param head
     * @param tail
     * @return
     */
    public static boolean validateJosa(String head, String tail) {
        char firstCharOfTail = tail.charAt(0);
        return (SyllableDic.isFirstJosaSyllable(firstCharOfTail) && HangulUtil.isJosaAppendable(head.charAt(head.length() - 1), tail));
    }

    /**
     * '에'로 시작되는 조사는 자주 사용되면서 분석 후보의 과생성을 많이 발생하는 문법형태소 이다.
     * 명사 중에서 '에'로 끝나는 것은 드물기 때문에 '에'앞의 음절이 '누/멍/성/기/보/니/리'인 경우멘
     * 형태소 분리 후보를 2가지로 생성하고 그외의 경우에는 최장 문법형태소를 분리한 후보 하나만 생성한다.
     *
     * @param candidates - 문법 형태소 분리 후보들
     * @param token - 입력 어절
     * @param noun - 체언으로 분류된 형태소
     * @param josa - 조사로 분류된 형태소
     */
    public static void addCandidateIfJosaStartsWith_E(List<MorphemeList> candidates, Token token, Morpheme noun, Morpheme josa) {
        if (josa.startsWith("에") && HangulUtil.nounCanEndsWith_E(noun.charAt(noun.length() - 1)))  {
            int index = token.indexOf("에");

            String josaCandidate = token.substring(index+1);
            String noundCandidate = token.substring(0, index+1);

            //if (logger.isDebugEnabled() && VERBOSE) logger.debug("'에'로 시작되는 조사의 앞음절 확인: " + noundCandidate + ", " + josaCandidate);
            MorphemeList secondCandidate = searchFixedAndJosa(noundCandidate, josaCandidate);
            if (!secondCandidate.isEmpty() && Constraints.validateJosa(secondCandidate.getFirst().getSource(), secondCandidate.getLast().getSource())) {
                candidates.add(secondCandidate);
            }
        }

    }

    /**
     * head와 josa를 각각 어휘 사전과 조사 사전에서 찾아서
     * 둘 다 사전에 수록되어 있는 경우에만 MorphemeList를 만들어 반한다.
     *
     * @param head - 체언, 부사 후본
     * @param josa - 조사
     * @return MorphemeList made up with noun + josa or MorphemeList.EMPTY if noun or josa not exists.
     */
    public static MorphemeList searchFixedAndJosa(String head, String josa) {
        WordEntry headEntry = null;
        WordEntry josaEntry = Dictionary.searchJosa(josa);

        if (josaEntry != null) {
            headEntry = Dictionary.searchFixedWord(head);
        }

        if (headEntry != null) {
            String tag = PosTag.isTagOf(headEntry.tag(), PosTag.N) ? PosTag.decodeNoun(headEntry.tag()) :
                    PosTag.isTagOf(headEntry.tag(), PosTag.AD) ? PosTag.decodeAdverb(headEntry.tag()) : PosTag.getTag(headEntry.tag());

            Morpheme n = new Morpheme(headEntry.getString(), tag);
            Morpheme j = new Morpheme(josaEntry.getString(), josaEntry.tag());
            return MorphemeList.create(Score.Success, n, j);
        }

        return MorphemeList.EMPTY;
    }
}
