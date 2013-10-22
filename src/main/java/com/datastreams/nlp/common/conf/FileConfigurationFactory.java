package com.datastreams.nlp.common.conf;



import java.io.*;
import java.net.URL;

/**
 *
 * User: shkim
 * Date: 13. 10. 14
 * Time: 오전 11:23
 *
 */
public class FileConfigurationFactory implements ConfigurationFactory {

    private URL configURL;

    public FileConfigurationFactory() {

    }

    @Override
    public Configuration getConfiguration() throws ConfigurationException {
        if (configURL == null) {
            throw new ConfigurationException("Configuration URL not specified.");
        }

        File configFile = new File(configURL.getFile());
        return new FileConfiguration(configFile);
    }

    @Override
    public void setConfigurationURL(URL configurationURL) {
        configURL = configurationURL;
    }
}
