package com.datastreams.dsma.ma;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 6. 24
 * Time: 오후 3:47
 * To change this template use File | Settings | File Templates.
 */
public class MorphemeAnalyzer {

    public void analyze(String text) throws Exception {

        List<Candidates> candidates = new ArrayList<Candidates>();
        analyzeByRule(text, candidates);
    }

    private void analyzeByRule(String text, List<Candidates> candidates) throws Exception {
         analyzeWithEomi(text, "", candidates);
    }

    private void analyzeWithEomi(String stem, String end, List<Candidates> candidates) throws Exception {
        String[] morphemes = EomiUtil.splitEomi(stem, end);

        String[] temp = new String[2];
        temp[0] = morphemes[0];
        AnalysisResult o = new AnalysisResult(temp[0], null, morphemes[1], 11);
        o.setPomi(temp[1]);


    }

    public static void main(String[] args) {
        String text = "가방은";
        MorphemeAnalyzer an = new MorphemeAnalyzer();
        try {
            an.analyze(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
