package com.datastreams.nlp.ma.tokenizer;

import com.datastreams.nlp.common.annotation.ThreadSafe;
import com.datastreams.nlp.common.conf.Configuration;
import com.datastreams.nlp.ma.conf.ConfKeys;
import com.datastreams.nlp.ma.conf.Config;
import com.datastreams.nlp.ma.constants.CharType;
import kr.co.datastreams.commons.util.FileUtil;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Loads predefined token patterns such as emoticon, number combined.
 *
 * 이모티콘, 숫자패턴 등 미리 정의된 토큰 패턴을 로드한다.
 * 토큰을 분리할 때 불필요하게 반복되는 문자나 문자+숫자 등의 패턴을 미리 처리할 수 있다.
 *
 * User: shkim
 * Date: 13. 7. 22
 * Time: 오후 2:19
 *
 */
@ThreadSafe
public class FilterTokenPattern {
    private static final FilterTokenPattern[] PREDEFINED_TOKEN_PATTERNS;
    private static final Configuration conf = Config.get();

    private final Pattern pattern;
    private final CharType charType;

    static {
        PREDEFINED_TOKEN_PATTERNS = loadFromFile(conf.get(ConfKeys.EMOTICON_PATTERNS));
    }

    private static FilterTokenPattern[] loadFromFile(String fileName) {
        List<FilterTokenPattern> patterns = new ArrayList<FilterTokenPattern>();
        List<String> lines = FileUtil.readLines(fileName);
        for (String line : lines) {
            if (line.trim().isEmpty()) continue;

            patterns.add(asTokenPattern(line));
        }

        return patterns.toArray(new FilterTokenPattern[patterns.size()]);
    }

    private static FilterTokenPattern asTokenPattern(String line) {
        int keyIndex = line.indexOf("=");
        String key = line.substring(0, keyIndex);
        String pattern = line.substring(keyIndex+1, line.length());

        String[] keyTokens = key.split("\\.");
        String type = keyTokens[0].toUpperCase();

        return new FilterTokenPattern(pattern, CharType.valueOf(type));
    }


    private FilterTokenPattern(String pattern, CharType charType) {
        this.pattern = Pattern.compile(pattern);
        this.charType = charType;
    }

    public static FilterTokenPattern[] getPredefinedPatterns() {
        return PREDEFINED_TOKEN_PATTERNS;
    }

    public static FilterTokenPattern[] getPredefinedPatterns(String fileName) {
        return loadFromFile(fileName);
    }

    public Pattern getPattern() {
        return pattern;
    }

    public CharType getCharType() {
        return charType;
    }

    public String toString() {
        return "("+ pattern.toString() + ", " + charType + ")";
    }
}
