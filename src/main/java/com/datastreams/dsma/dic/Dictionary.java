package com.datastreams.dsma.dic;

import com.datastreams.util.StopWatch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 6. 24
 * Time: 오후 2:15
 * To change this template use File | Settings | File Templates.
 */
public class Dictionary {

    private static Dictionary dictionary = null;
    private static boolean isLoading;


    public static final synchronized Dictionary getInstance() {
        if (!isLoading && dictionary == null) {
            isLoading = true;
            try {
                dictionary = new Dictionary();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                isLoading = false;
            }
        }
        return dictionary;
    }

    private Dictionary() throws IOException {
        StopWatch timer = new StopWatch();
        timer.start();
        loadDictionaries();
        timer.stop();
        timer.showMessage("Dictionary loading elapsed");
    }

    private void loadDictionaries() throws IOException {
        load(DicEnv.EOMI_FILE);
    }

    private void load(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(filename), DicEnv.ENCODING));;
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }

        if (reader != null) {
            reader.close();
        }
    }

}
