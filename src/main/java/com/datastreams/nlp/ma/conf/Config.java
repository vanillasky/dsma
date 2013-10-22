package com.datastreams.nlp.ma.conf;


import com.datastreams.nlp.common.conf.Configuration;
import com.datastreams.nlp.common.conf.ConfigurationFactory;
import com.datastreams.nlp.common.conf.FileConfigurationFactory;

/**
 * User: shkim
 * Date: 13. 7. 3
 * Time: 오전 8:24
 *
 */
public class Config {

    private static final String SYSTEM_PROPERTIES_FILE = "/conf/dsma.properties";
    private static final Config INSTANCE = new Config();

    private final Configuration sysConf;

    private Config() {
        ConfigurationFactory factory = new FileConfigurationFactory();
        factory.setConfigurationURL(Config.class.getResource(SYSTEM_PROPERTIES_FILE));
        sysConf = factory.getConfiguration();
    }

    public static Configuration get() {
        return INSTANCE.sysConf;
    }
}
