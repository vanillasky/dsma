package com.datastreams.nlp.ma.analyzer;

import com.datastreams.nlp.ma.constants.PosTag;
import com.datastreams.nlp.ma.model.Morpheme;
import com.datastreams.nlp.ma.model.MorphemeList;
import com.datastreams.nlp.ma.model.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * User: shkim
 * Date: 13. 10. 22
 * Time: 오후 5:24
 *
 */
public class Estimator {
    private static final Logger logger = LoggerFactory.getLogger(Estimator.class);
    private static final boolean VERBOSE = true;

    public List<MorphemeList> estimate(Token token) {
        List<MorphemeList> candidates = new ArrayList<MorphemeList>(4);

        List<MorphemeList> mediateCandidates = estimateJosa(token);

        if (Constraints.filterWithLengthOfTail(candidates, mediateCandidates)) {
            return candidates;
        }

        mediateCandidates = esmimateEomi(token);
        if (Constraints.filterWithLengthOfTail(candidates, mediateCandidates)) {
            return candidates;
        }

        return candidates;
    }


    /**
     * 어미를 추정하여 후보를 생성한다.
     *
     * @param token
     * @return
     */
    private List<MorphemeList> esmimateEomi(Token token) {
        if (logger.isDebugEnabled() && VERBOSE) logger.debug("{} => Heuristic 어미 추정 시작", token.getString());
        List<MorphemeList> candidates = new ArrayList<MorphemeList>();

        MorphemeList candidate = HeuristicSearcher.estimateEomi(token);
        if (!candidate.isEmpty()) {
            if (logger.isDebugEnabled() && VERBOSE) logger.debug("{} => Heuristic 어미 추정 성공", token.getString());
            Morpheme tail = candidate.getLast();
            assert PosTag.isTagOf(tail.getTagNum(), PosTag.E);

            candidates.add(candidate);
        }

        return candidates;
    }

    /**
     * 한국어 음절 특성상 2,3,4음절의 체언의 90%정도를 차지하므로
     * 조사의 위치를 각각 3,4,5번째 음절로 가정하여 조사 사전을 검색하여
     * 등록된 조사가 있고, 체언후보의 마지막 음절이 조사가 붙을 수 있는 음절이면
     * 후보를 생성하여 후보 리스트에 추가 한다.
     *
     * @param token
     * @return
     */
    private List<MorphemeList> estimateJosa(Token token) {
        if (logger.isDebugEnabled() && VERBOSE) logger.debug("{} => Heuristic 조사 추정 시작", token.getString());
        List<MorphemeList> candidates = new ArrayList<MorphemeList>();

        MorphemeList candidate = HeuristicSearcher.estimateJosa(token);

        if (!candidate.isEmpty()) {
            if (logger.isDebugEnabled() && VERBOSE) logger.debug("{} => Heuristic 조사 추정 성공", token.getString());

            Morpheme josa = candidate.getLast();
            Morpheme head = candidate.getFirst();
            assert PosTag.isTagOf(josa.getTagNum(), PosTag.J);

            if (Constraints.validateJosa(head.getSource(), josa.getSource())) {
                candidates.add(candidate);
            } else {
                if (logger.isDebugEnabled() && VERBOSE) logger.debug("{} => Heuristic, 조사 음절 및 위치정보 검사 실패", token.getString());
            }

            Constraints.addCandidateIfJosaStartsWith_E(candidates, token, head, josa);
        }

        return candidates;
    }

}
