package kr.co.datastreams.dsma;

import kr.co.datastreams.dsma.ma.DefaultMorphemeAnalyzer;
import kr.co.datastreams.dsma.ma.api.MorphemeAnalyzer;
import kr.co.datastreams.dsma.ma.model.AnalysisResult;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 7. 30
 * Time: 오후 3:16
 * To change this template use File | Settings | File Templates.
 */
public class Analyzer {

    private MorphemeAnalyzer morphemeAnalyzer = new DefaultMorphemeAnalyzer();

    public List<AnalysisResult> start(String inputString) {
        return morphemeAnalyzer.analyze(inputString);
    }
}
