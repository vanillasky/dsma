package com.datastreams.nlp.common.conf;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 6. 26
 * Time: 오후 3:58
 * To change this template use File | Settings | File Templates.
 */
public class ConfigurationException extends RuntimeException {
    private static final long serialVersionUID = -4248824445997234417L;

    public ConfigurationException(String message) {
        super(message);
    }

    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigurationException(Throwable cause) {
        super(cause);
    }
}
