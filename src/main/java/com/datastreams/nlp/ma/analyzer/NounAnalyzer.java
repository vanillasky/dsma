package com.datastreams.nlp.ma.analyzer;

import com.datastreams.nlp.ma.constants.ConclusionPoint;
import com.datastreams.nlp.ma.constants.PosTag;
import com.datastreams.nlp.ma.constants.Score;
import com.datastreams.nlp.ma.constants.WordPattern;
import com.datastreams.nlp.ma.dic.Dictionary;
import com.datastreams.nlp.ma.dic.SyllableDic;
import com.datastreams.nlp.ma.dic.WordEntry;
import com.datastreams.nlp.ma.model.Morpheme;
import com.datastreams.nlp.ma.model.MorphemeList;
import com.datastreams.nlp.ma.model.Token;
import com.datastreams.nlp.ma.util.HangulUtil;
import kr.co.datastreams.dsma.annotation.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 조사를 분리해서 체언을 분석한다.
 *
 * 어휘 형태소 사전에서 어근부를, 조사 사전에서 조사부를 검색한다.
 * 어근부가 체언이 아니면 접미사를 분리하고 다시 분석을 시도한다.
 * 명사형 전성어미를 발견하면 용언 분석을 시도해야 한다.
 * 분석된 점수를 기준으로 내림차순으로 정렬한다. 점수가 같다면 조사의 길이가 긴 것 순으로 정렬한다.
 *
 * User: shkim
 * Date: 13. 10. 23
 * Time: 오후 7:36
 *
 */

@ThreadSafe
public class NounAnalyzer {

    private static final Logger logger = LoggerFactory.getLogger(NounAnalyzer.class);
    private static final boolean USE_HEURISTIC_SCAN = false;


    // 점수가 같으면 최장 문법 형태소 순으로 정렬
    private final Comparator<MorphemeList> comparator  = new Comparator<MorphemeList>() {
        @Override
        public int compare(MorphemeList o1, MorphemeList o2) {
            if (o2.getScore().intValue() == o1.getScore().intValue()) {
                return o2.getLast().length() - o1.getLast().length();
            } else {
                return o2.getScore().intValue() - o1.getScore().intValue();
            }
        }
    };

    public NounAnalyzer() {

    }

    /**
     * 조사를 분리해서 체언을 분석한다.
     *
     * 어휘 형태소 사전에서 어근부를, 조사 사전에서 조사부를 검색한다.
     * 어근부가 체언이 아니면 접미사를 분리하고 다시 분석을 시도한다.
     * 명사형 전성어미를 발견하면 용언 분석을 시도해야 한다.
     * 분석된 점수를 기준으로 내림차순으로 정렬한다. 점수가 같다면 조사의 길이가 긴 것 순으로 정렬한다.
     *
     * @param token
     * @return
     */
    public List<MorphemeList> execute(Token token) {
        List<MorphemeList> candidates = generateCandidates(token);

        applyNounAnalysisPatterns(candidates);

        return candidates;
    }

    // 생성된 '체언+조사' 후보들에게 체언분석 규칙을 적용해본다.
    private void applyNounAnalysisPatterns(List<MorphemeList> candidates) {

        for (MorphemeList eachCandidate : candidates) {
            if (eachCandidate.getConclusion() == ConclusionPoint.NA) {
                applyForMGiPattern(candidates, eachCandidate);
            } else {
                if (logger.isDebugEnabled()) logger.debug("더이상 분석 안함 \"{}\": {}", eachCandidate, eachCandidate.getConclusion().description());
            }
        }
    }

    // 어근부가 'ㅁ/기'로 끝나는 경우 용언 분석
    private void applyForMGiPattern(List<MorphemeList> candidates, MorphemeList applicant) {
        String headPart = applicant.getFirst().getSource();

        if (HangulUtil.endsWithNounChangingEomi(headPart)) {
            if(logger.isDebugEnabled()) logger.debug("ㅁ/기 패턴 발견 용언 분석: {}", applicant);
            Token tempToken = Token.korean(headPart);
            String[] headAndTail = tempToken.split(-1);

            // 'ㅁ/기'로 끝나는 경우지만 '음'으로 끝나는 경우에만 분석후보를 분리한다.
            // e.g.) '오기+를' -> '오'+'기' 로 분리하지만
            //       '배움+에는' -> '배움'그대로 분석
            String tailPart = headAndTail[1].equals("음") ? headAndTail[1] : null;
            headPart = tailPart == null ? headPart : headAndTail[0];
            Token token = Token.korean(headPart);

//            List<MorphemeList> verbCandidates = verbAnalyzer.execute(token);
//            if (verbCandidates.size() > 0) {
//                if (applicant.getScore().intValue() <= Score.Estimation.intValue()) {
//                    candidates.remove(applicant);
//                }
//                candidates.addAll(verbCandidates);
//
//            }
        }
    }



    // 조사가 분리되는지 확인해서 후보를 만든다.
    private List<MorphemeList> generateCandidates(Token token) {
        List<MorphemeList> candidates;

        if (USE_HEURISTIC_SCAN) {
            candidates = scanHeuristics(token);

            if (!candidates.isEmpty()) {
                Collections.sort(candidates, comparator);
                if (candidates.get(0).getScore().intValue() >= Score.Analysis.intValue()) {
                    return candidates;
                }
            } else {
                if (logger.isDebugEnabled()) logger.debug("Heuristic 조사 추정 실패 {}", token.getString());
            }
        }

        candidates = scanLeftToRight(token);
        return candidates;
    }


    private List<MorphemeList> scanHeuristics(Token token) {
        if (logger.isDebugEnabled()) logger.debug("{} => Heuristic 조사 추정 시작", token.getString());

        String head, tail;
        List<MorphemeList> candidates = new ArrayList<MorphemeList>();
        int[] josaEstimateLocation = new int[]{2,3,4};

        for (int i=0; i < josaEstimateLocation.length; i++) {
            String[] wordChunk = token.split(josaEstimateLocation[i]);
            if (wordChunk != null) {
                head = wordChunk[0];
                tail = wordChunk[1];

                MorphemeList nounCandidate = splitJosa(head, tail);
                if (!nounCandidate.isEmpty()) {
                    candidates.add(nounCandidate);
                    if (i == 0) { // 3번째 위치에서 조사를 발견한 경우 앞의 한 음절을 더 처리한다.
                        addCandidateIfNounEndsWithFirstJosaSyllable(candidates, head, tail);
                    }
                    if (logger.isDebugEnabled()) logger.debug("Heuristic 조사 추정 성공 {}", candidates);
                    break;
                }

            }
        }

        return candidates;
    }

    /**
     * 왼쪽에서 오른쪽으로 음절을 탐색하면서 조사를 분리한다.
     *
     * @return
     */
    private List<MorphemeList> scanLeftToRight(Token token) {

        List<MorphemeList> candidates = new ArrayList<MorphemeList>();
        String[] chunk;

        for (int i=1; i < token.length(); i++) {
            chunk = token.split(i);
            MorphemeList nounCandidate = splitJosa(chunk[0], chunk[1]);
            if (!nounCandidate.isEmpty()) {
                candidates.add(nounCandidate);

                // '에'로 시작되는 경우에는 최장형태소 - 1인 후보를 하나 더 만든다.
                // e.g) '멍에는' -> '멍'+'에는', '멍에'+'는' 두 개를 생성
                addCandidateIfJosaStartsWith_E(candidates, nounCandidate);
                break; // 최장 조사를 찾았으므로 중지한다.
            }
        }


        Collections.sort(candidates, comparator);
        return candidates;
    }

    /**
     * 어근부가 조사로 분리되는지 확인하여 가능하면 MorphemeList를 만들어 돌려준다.
     *
     * 1) 사전을 탐색하기 전에 tailPart의 첫번째 음절이 조사의 첫음절로 사용될 수 있는지 음절정보를 검사한다.
     * 2) 어근부의 마지막 음절이 조사후보와 결합이 가능한지 음절정보를 확인한다.
     * 3) tailPart가 조사 사전에 등록되어 있는지 확인한다. 없으면 종료.
     * 4) headPart를 어휘 형태소 사전에서 찾아본다.
     *   4.1) headPart가 어휘 사전에 없으면 접미사가 분리되는지 확인한다.
     *   4.2) 접미사가 분리되면 접미사를 제외한 단어를 다시 어휘사전에서 찾아 어근부를 이 단어로 대체한다.
     * 5) headPart가 사전에 있는 단어이면 명사/부사 여부를 확인하여 분석후보를 만들고
     *    없으면 명사 추정범주로 설정한다.
     *
     * @param headPart - 어근부
     * @param tailPart - 조사후보
     * @return
     */
    public MorphemeList splitJosa(String headPart, String tailPart) {

        if (!validate(headPart, tailPart)) {
            return MorphemeList.EMPTY;
        }

        WordEntry tailEntry = Dictionary.searchJosa(tailPart);
        if (tailEntry == null) {
            return MorphemeList.EMPTY;
        }

        assert PosTag.isTagOf(tailEntry.tag(), PosTag.J);

        WordEntry headEntry = Dictionary.searchFixedWord(headPart);
        WordEntry suffixEntry = null;

        if (headEntry == null) {
            suffixEntry = splitSuffix(headPart);
            if (logger.isDebugEnabled()) logger.debug("접미사 분리 시도 결과: {} -> {}", headPart, suffixEntry );
            if (suffixEntry != null) {
                headEntry = Dictionary.searchFixedWord(headPart.substring(0, headPart.indexOf(suffixEntry.getString())));
            }
        }

        if (headEntry == null) {
            return createEstimation(headPart, tailEntry);
        }
        else {
            return applyNJ(headEntry, suffixEntry, tailEntry);
        }
    }

    // 추정레벨의 형태소 분석 결과를 만들어서 반환한다.
    private MorphemeList createEstimation(String headPart, WordEntry tailEntry) {

        Morpheme head = new Morpheme(headPart, PosTag.NF);
        Morpheme tail = new Morpheme(tailEntry);
        Score score = Score.Estimation;
        WordPattern pattern = WordPattern.NJ;

        return MorphemeList.create(score, pattern, head, tail);
    }

    // 체언 + 조사 분석이 성공한 경우 결과를 만들어서 반환한다.
    private MorphemeList applyNJ(WordEntry headEntry, WordEntry suffixEntry, WordEntry tailEntry) {
        Morpheme head;
        Morpheme tail;
        Morpheme suffix = suffixEntry != null ? new Morpheme(suffixEntry) : null;
        WordPattern pattern;

        if (headEntry != null) {

            // 명사 또는 부사 태그 추출, 그외의 경우는 사전수록 태그 사용
            long tagNum = PosTag.extractNounTag(headEntry.tag()) == 0 ? PosTag.extractAdverbTag(headEntry.tag()) : PosTag.extractNounTag(headEntry.tag());
            tagNum =  tagNum == 0 ? headEntry.tag() : tagNum;

            head = new Morpheme(headEntry.getString(), tagNum);
            tail = new Morpheme(tailEntry);
            if (suffix != null) {
                head = head.merge(suffix);
            }

            pattern = PosTag.isTagOf(PosTag.N, tagNum) ? WordPattern.NJ : WordPattern.ADVJ;
            ConclusionPoint point = tailEntry.getString().length() > 1 ? ConclusionPoint.MoreThan2SyllableGMFound : ConclusionPoint.NA;
            return MorphemeList.create(Score.Success, pattern, point, head, tail);
        }

        return MorphemeList.EMPTY;
    }


    /**
     * 접미사를 분리한다.
     *
     * @param word
     * @return
     */
    private WordEntry splitSuffix(String word) {
        WordEntry entry = null;

        for (int i=1; i < word.length(); i++) {
            String tail = word.substring(i);
            entry = Dictionary.searchSuffix(tail);
            if (entry != null) {
                break;
            }
        }

        return entry;
    }


    public boolean validate(String head, String tail) {
        return appendableJosa(head, tail) && validateSyllableConstraint(tail);
    }

    /**
     * 조사의 첫음절로 사용될 수 있는지 확인한다.
     * @param tail
     * @return
     */
    public boolean validateSyllableConstraint(String tail) {
        boolean result = SyllableDic.isFirstJosaSyllable(tail.charAt(0));

        if (logger.isDebugEnabled()) {
            if (result == false) logger.debug("조사의 첫음절로 사용될 수 없는 음절 때문에 실패 :{} ", tail);
        }

        return result;
    }

    /**
     * head의 마지막 음절이 tail과 결합 가능한지 확인한다.
     *
     * @param head
     * @param tail
     * @return
     */
    public boolean appendableJosa(String head, String tail) {
        boolean result = HangulUtil.isJosaAppendable(head.charAt(head.length() - 1), tail);
        if (logger.isDebugEnabled()) {
            if (result == false) logger.debug("마지막 음절 + 조사가 결합 불능:{},{}", head, tail);
        }
        return result;
    }

    /**
     * Heuristic을 사용할 때 2,3,4 순으로 탐색하기 때문에 '멍에는'의 경우 '멍에'+'는'만 생성하는 것을 보완하기 위함
     * 2
     * Left -> Right 로 탐색을하면 Heuristic 탐색을 쓰는 것이 비용대비 효과가 별로일 듯..
     *
     * e.g.)멍에는 -> 멍 + 에는, 멍에 + 는
     * @param candidates
     * @param head
     * @param tail
     */
    public void addCandidateIfNounEndsWithFirstJosaSyllable(List<MorphemeList> candidates, String head, String tail) {
        if (SyllableDic.isFirstJosaSyllable(head.charAt(head.length()-1))) {

            String noundCandidate = head.substring(0, head.length()-1);
            String josaCandidate =  head.substring(head.length()-1) + tail;

            MorphemeList secondCandidate = searchFixedAndJosa(noundCandidate, josaCandidate);
            if (logger.isDebugEnabled()) logger.debug("명사,조사 한 개 더 확인: " + noundCandidate + "+" + josaCandidate + " =>"  + (secondCandidate.isEmpty() ? "" : secondCandidate));
            if (!secondCandidate.isEmpty() && validate(noundCandidate, josaCandidate)) {
                candidates.add(secondCandidate);
            }
        }

    }

    /**
     * '에'로 시작되는 조사는 자주 사용되면서 분석 후보의 과생성을 많이 발생하는 문법형태소 이다.
     * 명사 중에서 '에'로 끝나는 것은 드물기 때문에 '에'앞의 음절이 '누/멍/성/기/보/니/리'인 경우멘
     * 형태소 분리 후보를 2가지로 생성하고 그외의 경우에는 최장 문법형태소를 분리한 후보 하나만 생성한다.
     *
     * e.g.)멍에는 -> 멍 + 에는, 멍에 + 는
     * @param candidates - 체언 후보들
     * @param nounCandidate - '체언 + 조사'로 분석되었고 '에'로 시작되는 조사가 있는 분석 후보
     */
    private void addCandidateIfJosaStartsWith_E(List<MorphemeList> candidates, MorphemeList nounCandidate) {
        String tail = nounCandidate.getLast().getSource();
        String head = nounCandidate.getFirst().getSource();

        if (tail.startsWith("에") && HangulUtil.nounCanEndsWith_E(head.charAt(head.length() - 1)))  {
            String noundCandidate = head + "에";
            String josaCandidate = tail.substring(1);

            MorphemeList secondCandidate = searchFixedAndJosa(noundCandidate, josaCandidate);

            if (logger.isDebugEnabled()) logger.debug("'에'로 시작되는 조사 확인: " + noundCandidate + "+" + josaCandidate + " =>"  + (secondCandidate.isEmpty() ? "실패" : secondCandidate));
            if (!secondCandidate.isEmpty() && validate(noundCandidate, josaCandidate)) {
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
    public MorphemeList searchFixedAndJosa(String head, String josa) {
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
            WordPattern pattern = PosTag.isTagOf(PosTag.N, headEntry.tag()) ? WordPattern.NJ : WordPattern.ADVJ;
            return MorphemeList.create(Score.Success, pattern, n, j);
        }

        return MorphemeList.EMPTY;
    }
}
