package kr.co.datastreams.dsma.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 *
 * User: shkim
 * Date: 13. 7. 2
 * Time: 오전 11:25
 *
 * 파일로부터 Configuration 객체를 생성한다.
 * 파일이름을 key로 가지는 HashMap에 저장하고 있다가 같은 파일명으로 요청이 들어오면 반환한다.
 */
public class FileConfiguration implements Configuration {

    private static final Logger logger = LoggerFactory.getLogger(FileConfiguration.class);
    private static final Map<String, FileConfiguration> PROPS_CACHE = Collections.synchronizedMap(new HashMap<String, FileConfiguration>());

    private final String filename;
    private final Properties properties;

    private FileConfiguration(String filename) {
        this.filename = filename;
        this.properties = new Properties();
        load();
    }


    public static FileConfiguration newInstance(String filename) {
        if (PROPS_CACHE.containsKey(filename)) {
            logger.debug("Return cached properties for " + filename);
            return PROPS_CACHE.get(filename);
        }

        FileConfiguration props = new FileConfiguration(filename);
        PROPS_CACHE.put(filename, props);
        return props;
    }

    protected void load() {
        InputStreamReader reader = null;
        try {
            InputStream is = new FileInputStream(new File(filename));
            reader = new InputStreamReader(is);
            properties.load(reader);

        } catch (Exception e) {
            logger.error("Configuration file loading failed", e);
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
        String value = (String)properties.get(key);
        return value == null ? -1 : Integer.parseInt(value) ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FileConfiguration other = (FileConfiguration)o;
        if (!filename.equals(other.filename)) return false;
        if (properties != other.properties) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 31 * filename.hashCode();
        result = 31 * result + (properties != null ? properties.hashCode() : 0);
        return result;
    }
}
