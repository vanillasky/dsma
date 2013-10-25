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
    private final NounAnalyzer nounAnalyzer = new NounAnalyzer();

    @Override
    public Eojeol execute(Token token) {

        List<MorphemeList> candidates = nounAnalyzer.execute(token);

        // [형태소 분리 제약1] 2음절 이상의 조사가 분리된 어절은 다른 가능성을 고려할 필요가 없다.
        if (candidates.size() > 0) {
            MorphemeList candidate = candidates.get(0);
            if (candidate.getLast().length() > 2 && PosTag.isTagOf(PosTag.J, candidate.getLast().getTagNum())) {
                if (logger.isDebugEnabled()) logger.debug("2음절 이상의 조사 발견, 분석 종료");
                return new Eojeol(token, candidate.getScore(), candidates);
            }
        }

        //MorphemeSeparator verbProcessor = new VerbProcessor(token);
        //List<MorphemeList> verbCandidates = verbProcessor.generateCandidates();


        Eojeol result = new Eojeol(token, Score.Analysis, candidates);
        //System.out.println(result.asMorphemeString());

        return result;
    }




}
