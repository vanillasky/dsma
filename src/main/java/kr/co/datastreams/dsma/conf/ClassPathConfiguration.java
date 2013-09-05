package kr.co.datastreams.dsma.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 *
 * User: shkim
 * Date: 13. 7. 5
 * Time: 오후 1:14
 *
 * Classpath 를 기반으로 파일의 위치를 찾아서 Configuration 객체를 초기화 한다.
 * 한 번 로드한 파일에 대한 설정은 HashMap에 보관한다.
 *
 */
public class ClassPathConfiguration implements Configuration {

    private static final Logger logger = LoggerFactory.getLogger(FileConfiguration.class);
    private static final Map<String, ClassPathConfiguration> PROPS_CACHE = new HashMap<String, ClassPathConfiguration>();

    private final String filename;
    private final Properties properties;

    private ClassPathConfiguration(String filename) {
        this.filename = filename;
        this.properties = new Properties();
        load();
    }


    public static ClassPathConfiguration newInstance(String filename) {
        if (PROPS_CACHE.containsKey(filename)) {
            logger.debug("Return cached properties for " + filename);
            return PROPS_CACHE.get(filename);
        }

        ClassPathConfiguration props = new ClassPathConfiguration(filename);
        PROPS_CACHE.put(filename, props);
        return props;
    }

    protected void load() {
        InputStreamReader reader = null;
        try {
            InputStream is = getClass().getResourceAsStream(filename);
            if (is == null)
                throw new ConfigurationException("Cannot load configuration file" + filename);

            reader = new InputStreamReader(is);
            properties.load(reader);

        } catch (Exception e) {
            logger.error("Cannot load configuration file", e);
            throw new ConfigurationException(e.getMessage());
        } finally {
            if (reader != null) try {
                reader.close();
            } catch (IOException e) {
                logger.warn(e.getMessage());
            }
        }
    }

    @Override
    public String get(String key) {
        return (String)properties.get(key);
    }

    @Override
    public int getInt(String key) {
        return Integer.parseInt((String) properties.get(key));
    }
}
