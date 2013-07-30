package kr.co.datastreams.dsma.dic;

import kr.co.datastreams.commons.util.FileUtil;
import kr.co.datastreams.dsma.conf.ConfKeys;
import kr.co.datastreams.dsma.conf.Configuration;
import kr.co.datastreams.dsma.conf.ConfigurationFactory;
import kr.co.datastreams.dsma.ma.model.CharType;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 7. 22
 * Time: 오후 2:19
 * To change this template use File | Settings | File Templates.
 */
public class TokenPattern {
    private static final TokenPattern[] PREDEFINED_TOKEN_PATTERNS;
    private static final Configuration conf = ConfigurationFactory.getConfiguration();

    private Pattern pattern;
    private CharType charType;

    static {
        PREDEFINED_TOKEN_PATTERNS = loadFromFile(conf.get(ConfKeys.EMOTICON_PATTERNS));
    }

    private static TokenPattern[] loadFromFile(String fileName) {
        List<TokenPattern> patterns = new ArrayList<TokenPattern>();
        List<String> lines = FileUtil.readLines(conf.get(ConfKeys.EMOTICON_PATTERNS));
        for (String line : lines) {
            if (line.trim().isEmpty()) continue;

            patterns.add(asTokenPattern(line));
        }

        return patterns.toArray(new TokenPattern[patterns.size()]);
    }

    // EMOTICON.NEGATIVE.10=ㅜㅜ
    private static TokenPattern asTokenPattern(String line) {
        int keyIndex = line.indexOf("=");
        String key = line.substring(0, keyIndex);
        String pattern = line.substring(keyIndex+1, line.length());
        String type = key.substring(0, key.indexOf(".")).toUpperCase();

        return new TokenPattern(pattern, CharType.valueOf(type));
    }


    public TokenPattern(String pattern, CharType charType) {
        this.pattern = Pattern.compile(pattern);
        this.charType = charType;
    }

    public static TokenPattern[] getPredefinedPatterns() {
        return PREDEFINED_TOKEN_PATTERNS;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public CharType getCharType() {
        return charType;
    }
}
