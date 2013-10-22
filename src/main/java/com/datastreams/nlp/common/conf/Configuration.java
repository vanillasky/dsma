package com.datastreams.nlp.common.conf;

/**
 *
 * User: shkim
 * Date: 13. 7. 1
 * Time: 오후 5:48
 *
 */
public interface Configuration {
    public String get(String key);
    public int getInt(String key);
    public boolean getBoolean(String key);
}
