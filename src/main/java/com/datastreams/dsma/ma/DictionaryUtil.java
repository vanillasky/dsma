package com.datastreams.dsma.ma;

import com.datastreams.dsma.dic.DicEnv;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 6. 24
 * Time: 오후 4:18
 * To change this template use File | Settings | File Templates.
 */
public class DictionaryUtil {

    private static HashMap eomis;

    public static String combineAndEomiCheck(char s, String eomi) throws Exception {

        if(eomi==null) eomi="";

        if(s=='ㄴ') eomi = "은"+eomi;
        else if(s=='ㄹ') eomi = "을"+eomi;
        else if(s=='ㅁ') eomi = "음"+eomi;
        else if(s=='ㅂ') eomi = "습"+eomi;
        else eomi = s+eomi;

        if(existEomi(eomi)) return eomi;

        return null;

    }

    public static boolean existEomi(String str)  throws Exception {
        if(eomis==null) {
            eomis = new HashMap();
            readFile(eomis, DicEnv.EOMI_FILE);
        }

        if(eomis.get(str)==null) return false;
        else return true;
    }

    private static synchronized void readFile(HashMap map, String dic) throws Exception {

        String path = DicEnv.EOMI_FILE;

        try{
            List<String> line = FileUtil.readLines(path,"UTF-8");
            System.out.println("어미사전:"+line.size());
            for(int i=1;i<line.size();i++) {

                map.put(line.get(i).trim(), line.get(i));
            }
        }catch(IOException e) {
            throw new Exception(e.getMessage(),e);
        } catch (Exception e) {
            throw new Exception(e.getMessage(),e);
        }
    }
}
