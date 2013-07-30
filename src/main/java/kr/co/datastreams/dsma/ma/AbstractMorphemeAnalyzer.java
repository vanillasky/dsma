package kr.co.datastreams.dsma.ma;

import kr.co.datastreams.dsma.ma.api.MorphemeAnalyzer;
import kr.co.datastreams.dsma.ma.model.AnalysisResult;
import kr.co.datastreams.dsma.ma.model.Token;

import java.util.List;

/**
 *
 * User: shkim
 * Date: 13. 7. 30
 * Time: 오후 3:19
 *
 */
public abstract class AbstractMorphemeAnalyzer implements MorphemeAnalyzer {


    @Override
    public abstract List<AnalysisResult> analyze(String inputString);


    protected List<Token> tokenizeFrom(String inputString, Tokenizer tokenizer) {
        List<Token> tokens = tokenizer.tokenize(inputString.trim());
        return tokens;
    }

//    protected List<Token> tokenizeFrom(File file, Tokenizer tokenizer) {
//        List<Token> tokens = new ArrayList<Token>();
//        List<String> lines = FileUtil.readLines(file, "UTF-8");
//        for (String line : lines) {
//            tokens.addAll(tokenizer.tokenize(line.trim()));
//        }
//
//        return tokens;
//    }
}
