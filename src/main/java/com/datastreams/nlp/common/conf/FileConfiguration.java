package com.datastreams.nlp.common.conf;

import java.io.*;
import java.util.Properties;

/**
 *
 * User: shkim
 * Date: 13. 10. 14
 * Time: 오후 1:33
 *
 */
public class FileConfiguration implements Configuration {

    private Properties properties;

    public FileConfiguration(File configFile) throws ConfigurationException {
        properties = new Properties();

        InputStream stream = null;
        try {
            stream = new FileInputStream(configFile);
            properties.load(stream);
        } catch (IOException e) {
            throw new ConfigurationException(e.getMessage(), e);
        } finally {
            if (stream != null) {
                try { stream.close();} catch (IOException e) {}
            }
        }

    }

    @Override
    public String get(String key) {
        return (String)properties.get(key);
    }

    @Override
    public int getInt(String key) {
        return Integer.parseInt(get(key));
    }

    @Override
    public boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }
}
