package kr.co.datastreams.dsma.dic;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 7. 12
 * Time: 오후 6:22
 * To change this template use File | Settings | File Templates.
 */
public interface DictionaryReader {
    public String readLine() throws IOException;
    public void close();
}
