package kr.co.datastreams.dsma.conf;

/**
 * User: shkim
 * Date: 13. 7. 3
 * Time: 오전 8:24
 *
 * Configuration 객체를 만들어 반환한다.
 * conf/logcube.properties 파일을 읽어 사용할 프로퍼티의 종류를 파악하여(DB 또는 FILE)
 * FILE인 경우 FileConfiguration을, DB이면 DBConfiguration 객체를 반환한다.
 *
 *
 */
public class ConfigurationFactory {

    private static final String PROPERTIES_FILE = "/conf/dsma.properties";
    private static final String DEFAULT_PROPERTY = "dsma.configuration";

    private static final Configuration sysConf = ClassPathConfiguration.newInstance(PROPERTIES_FILE);

    private ConfigurationFactory() {

    }

    public static Configuration getConfiguration() {
        return sysConf;
    }

    public static Configuration getConfiguration(String key) {
        return resovleConfiguration(key);
    }

    private static Configuration resovleConfiguration(String key) {
        String confSource = sysConf.get(DEFAULT_PROPERTY);

        if (confSource.equalsIgnoreCase("FILE")) {
            return FileConfiguration.newInstance(key);
        }
        return null;
    }

}
