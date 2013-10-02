package kr.co.datastreams.dsma.analyzer;


import kr.co.datastreams.commons.util.StringUtil;
import kr.co.datastreams.dsma.dic.Dictionary;
import kr.co.datastreams.dsma.dic.JosaDic;
import kr.co.datastreams.dsma.dic.SyllableDic;
import kr.co.datastreams.dsma.model.*;
import kr.co.datastreams.dsma.util.Hangul;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * User: shkim
 * Date: 13. 10. 2
 * Time: 오후 3:26
 *
 */
public class RuleBaseWordAnalyzer implements WordAnalyzer {

    @Override
    public Eojeol analyzeWord(Token token) {
        boolean josaFlag = true;
        boolean eomiFlag = true;

        List<MorphemeList> candidates = new ArrayList<MorphemeList>();

        String inputString = token.getString();

        for (int i=inputString.length()-1; i > 0; i--) {
            String head = inputString.substring(0, i);
            String tail = inputString.substring(i);
            char firstCharOfTail = tail.charAt(0);

            if (josaFlag && SyllableDic.isFirstJosaSyllable(firstCharOfTail)) {
                analyzeNoun(candidates, head, tail);
            }

//            if (eomiFlag) {
//                verbAnalyzer.analyze(candidates, word, i);
//            }

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

        if (candidates.size() > 0)
            return new Eojeol(token, Score.Success, candidates);
        else
            return new Eojeol(token, Score.Failure, candidates);
    }


    private void analyzeNoun(List<MorphemeList> candidates, String head, String tail) {
        if (StringUtil.nvl(head).length() == 0 || StringUtil.nvl(tail).length() == 0) return;
        //System.out.println("head:"+ head + ", tail:" + tail);

//        if (tail.startsWith("로") && head.endsWith("으")) {
//            int index = head.length()-2;
//            if (index >= 0) {
//                Hangul h = Hangul.split(head.charAt(head.length()-2));
//                if (h.hasJong()) {
//                    tail = head.substring(index) + tail;
//                    head = head.substring(0, index);
//
//                    System.out.println("자음으로 끝나는 단어 + 로 -> 조음소 '으' 처리: "+ head + " 조사후보:"+ tail);
//                }
//            }
////            Hangul lastCharOfHead = Hangul.split(head.charAt(head.length()-1));
////            if (lastCharOfHead.endsWith('ㄹ') || !lastCharOfHead.hasJong()) {
////                WordEntry entry = Dictionary.getNoun(head);
////                System.out.println(entry);
////                System.out.println("모음이나 'ㄹ'로 끝나는 체언하고만 연결이 가능하다 <=" + entry);
////                if (entry == null)
////                    return;
////            }
////            }
//        }


        WordEntry nounWord = Dictionary.getNoun(head);
        if (nounWord == null) {
            return;
        }

        WordEntry josa = JosaDic.search(tail);
        Hangul chr = Hangul.split(head.charAt(head.length()-1));
        if (josa == null || !chr.isAppendableJosa(tail.substring(0, 1))) {
            return;
        }

        Morpheme h = new Morpheme(head, PosTag.decodeNoun(nounWord.tag()));
        Morpheme t = new Morpheme(tail, josa.tag());
        MorphemeList list = new MorphemeList();
        list.add(h);
        list.add(t);
        candidates.add(list);
    }

}
