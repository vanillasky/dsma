package kr.co.datastreams.dsma.dic;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 7. 12
 * Time: 오후 6:24
 * To change this template use File | Settings | File Templates.
 */
public class FileDicReader implements DictionaryReader {

    BufferedReader reader = null;

    public FileDicReader(String fileName) throws UnsupportedEncodingException, FileNotFoundException {
        reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName)), "UTF-8"));
    }

    @Override
    public String readLine() throws IOException {
        return reader.readLine();
    }

    @Override
    public void close() {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }
}
