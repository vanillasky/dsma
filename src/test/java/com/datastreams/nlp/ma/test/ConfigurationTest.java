package com.datastreams.nlp.ma.test;

import com.datastreams.nlp.common.conf.Configuration;
import com.datastreams.nlp.common.conf.ConfigurationFactory;
import com.datastreams.nlp.common.conf.FileConfigurationFactory;
import com.datastreams.nlp.ma.conf.Config;
import org.junit.Test;

import java.net.URL;

import static org.junit.Assert.assertEquals;

/**
 *
 * User: shkim
 * Date: 13. 10. 14
 * Time: 오전 11:17
 *
 */
public class ConfigurationTest {

    @Test
    public void testConfiguration() throws Exception {
        ConfigurationFactory factory = new FileConfigurationFactory();
        URL configURL = getClass().getResource("/conf/dsma.properties");
        factory.setConfigurationURL(configURL);
        Configuration conf = factory.getConfiguration();
        assertEquals("dic/base.dic", conf.get("dictionary.base"));
    }


    @Test
    public void testConfig() throws Exception {
        Configuration config = Config.get();
        assertEquals("dic/base.dic", config.get("dictionary.base"));
    }
}
