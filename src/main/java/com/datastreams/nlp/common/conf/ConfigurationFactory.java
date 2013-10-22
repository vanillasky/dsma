package com.datastreams.nlp.common.conf;

import java.net.URL;

/**
 *
 * User: shkim
 * Date: 13. 10. 14
 * Time: 오전 11:22
 *
 */
public interface ConfigurationFactory {
   public Configuration getConfiguration() throws ConfigurationException;
   public void setConfigurationURL(URL configurationURL);
}
