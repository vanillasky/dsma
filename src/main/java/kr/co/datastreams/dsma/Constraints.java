package kr.co.datastreams.dsma;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 10. 1
 * Time: 오후 3:53
 * To change this template use File | Settings | File Templates.
 */
public class Constraints {

    // 종성이 있는 음절과 연결될 수 없는 조사
    private static final Map<String, Boolean> JOSA_NOT_APPENDABLE_TO_JONGSUNG;

    // 종성이 없는 음절과 연결될 수 없는 조사
    private static final Map<String, Boolean> JOSA_NOT_APPENDABLE_WITHOUT_JONGSUNG;

    static {
        JOSA_NOT_APPENDABLE_TO_JONGSUNG = new HashMap<String, Boolean>();
        JOSA_NOT_APPENDABLE_WITHOUT_JONGSUNG = new HashMap<String, Boolean>();

        JOSA_NOT_APPENDABLE_TO_JONGSUNG.put("가", false);
        JOSA_NOT_APPENDABLE_TO_JONGSUNG.put("는", false);
        JOSA_NOT_APPENDABLE_TO_JONGSUNG.put("다", false);
        JOSA_NOT_APPENDABLE_TO_JONGSUNG.put("나", false);
        JOSA_NOT_APPENDABLE_TO_JONGSUNG.put("니", false);
        JOSA_NOT_APPENDABLE_TO_JONGSUNG.put("고", false);
        JOSA_NOT_APPENDABLE_TO_JONGSUNG.put("라", false);
        JOSA_NOT_APPENDABLE_TO_JONGSUNG.put("와", false);
        JOSA_NOT_APPENDABLE_TO_JONGSUNG.put("랑", false);
        JOSA_NOT_APPENDABLE_TO_JONGSUNG.put("를", false);
        JOSA_NOT_APPENDABLE_TO_JONGSUNG.put("며", false);
        JOSA_NOT_APPENDABLE_TO_JONGSUNG.put("든", false);
        JOSA_NOT_APPENDABLE_TO_JONGSUNG.put("야", false);
        JOSA_NOT_APPENDABLE_TO_JONGSUNG.put("여", false);

        JOSA_NOT_APPENDABLE_WITHOUT_JONGSUNG.put("과", false);
        JOSA_NOT_APPENDABLE_WITHOUT_JONGSUNG.put("은", false);
        JOSA_NOT_APPENDABLE_WITHOUT_JONGSUNG.put("아", false);
        JOSA_NOT_APPENDABLE_WITHOUT_JONGSUNG.put("으", false);
        JOSA_NOT_APPENDABLE_WITHOUT_JONGSUNG.put("은", false);
        JOSA_NOT_APPENDABLE_WITHOUT_JONGSUNG.put("을", false);

    }

    public static boolean isAppendableJosaToJongSung(String josa) {
        return JOSA_NOT_APPENDABLE_TO_JONGSUNG.get(josa) == null ? true : false;
    }

    public static boolean isAppendableJosaWithoutJongsung(String josa) {
        return JOSA_NOT_APPENDABLE_WITHOUT_JONGSUNG.get(josa) == null ? true : false;
    }
}
