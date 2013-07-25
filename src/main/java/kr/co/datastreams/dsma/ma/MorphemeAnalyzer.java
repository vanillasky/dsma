package kr.co.datastreams.dsma.ma;

import kr.co.datastreams.commons.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 7. 23
 * Time: 오전 10:50
 * To change this template use File | Settings | File Templates.
 */
public class MorphemeAnalyzer {
    private static final int POS_START = 1;
    private static final int POS_MID = 2;
    private static final int POS_END = 3;

    private final Tokenizer tokenizer = new Tokenizer();

    public List analyze(String inputString) {
        if (StringUtil.nvl(inputString).length() == 0) {
            return Collections.EMPTY_LIST;
        }

        List results = new ArrayList();
        inputString = inputString.trim();
        List<Token> tokens = tokenizer.tokenize(inputString);
        for (Iterator<Token> iter=tokens.iterator(); iter.hasNext();) {
            Token token = iter.next();
            if (!token.is(CharType.SPACE)) {
                List result = analyze(token);
                results.add(result);
            }
        }

        return results;
    }


    private List analyze(Token token) {
        List<AnalysisResult> candidates = new ArrayList<AnalysisResult>();
        analyzeByRule(candidates, token);

        return null;
    }

    private void analyzeByRule(List<AnalysisResult> candidates, Token token) {
        if (token.charType == CharType.HANGUL) {
            analyzeWithEomi(candidates, token, "");

//            for (int i=token.getString().length()-1; i > 0; i--) {
//
//            }
        }
    }

    private void analyzeWithEomi(List<AnalysisResult> candidates, Token token, String end) {
        Variant morpheme = EndingProcessor.splitEnding(token.getString(), end);
        if (morpheme.isEmpty()) return;

        //String[] pomis = EndingProcessor.splitPrefinalEnding(morphemes[0]);
    }
}
