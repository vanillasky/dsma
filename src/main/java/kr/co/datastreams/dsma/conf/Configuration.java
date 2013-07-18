package kr.co.datastreams.dsma.conf;

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
}
