package com.datastreams.nlp.internal;

import kr.co.datastreams.commons.util.FileUtil;

import java.io.*;
import java.util.*;

/**
 * resources/dic_source 디렉토리의 사전 소스 파일을 읽어서 사전을 구성한다.
 *
 * 품사별로 나누어진 사전을 통한한다.
 * 중복되는 단어는 태그를 추가하는 방식으로 처리한다.
 * 예를 들어 '탈락'이라는 단어가 명사, 부사 사전에 각각 등록되어 있으면 "탙락/NNG/MAG"로 통합해서 수록한다.
 *
 * 1) 명사(noud.dic), 감탄사(ic.dic), 부사(ma.dic), 관형사(mm.dic), 인명(person.dic) 파일의 어휘는
 *    base.dic 한 개로 통합한다.
 * 2) 동사, 형용사, 보조동사 등의 용언이 수록된 verb.dic은 그대로 한개의 파일로 이용한다.
 *
 *
 * User: shkim
 * Date: 13. 10. 16
 * Time: 오후 1:53
 *
 */
public class DicComposer {

    public static final String DIC_SOURCE = "dic_source/";
    public static final String DIC_PATH = "src/main/resources/dic";

    public static String[] wordFiles = {
        "noun.dic",  // 명사
        "ic.dic",    // 감탄사
        "ma.dic",    // 부사
        "mm.dic",   // 관형사
        "person.dic" // 인명
    };

    public static String[] verbFiles = {
        "verb.dic"  // 용언
    };

    public static String[] suffixFiles = {
        "suffix.dic"
    };

    public static String[] josaFiles = {
        "josa.dic"
    };

    public static void main(String[] args) {
        DicComposer composer = new DicComposer();


        //composer.buildDicTo("base.dic", wordFiles, new HashMap<String, StringBuilder>());
        //composer.buildDicTo("verb.dic", verbFiles, new HashMap<String, StringBuilder>());
        //composer.buildDicTo("suffix.dic", suffixFiles, new HashMap<String, StringBuilder>());
        composer.buildDicTo("josa.dic", josaFiles, new HashMap<String, StringBuilder>());

    }


    private void buildDicTo(String fileName, String[] files, Map<String, StringBuilder> map)  {
        for (int i=0; i < files.length; i++) {
            List<String> lines = FileUtil.readLines(DIC_SOURCE + files[i]);
            System.out.println("Read source dictionary:" + files[i] + ", " + lines.size() + " rows");
            mergePosTag(lines, map);
        }

        write(fileName, map);
    }



    private void write(String fileName, Map<String, StringBuilder> map) {
        Iterator<String> keys = sortKeys(map.keySet().iterator());
        File file = new File(DIC_PATH, fileName);
        BufferedWriter writer = null;

        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
            while (keys.hasNext()) {
                String word = keys.next();
                String tag = map.get(word).toString();
                writer.write(word + "/" + tag + "\n");
            }

            writer.flush();
            System.out.println("file has been written:" + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Iterator<String> sortKeys(Iterator<String> iterator) {
        List<String> keys = new ArrayList<String>();
        while (iterator.hasNext()) {
            String key = iterator.next();
            //System.out.println(key);
            keys.add(key);
        }

        Collections.sort(keys);
        for (String each : keys) {
            System.out.println(each);
        }
        return keys.iterator();
    }


    private void mergePosTag(List<String> lines, Map<String, StringBuilder> map) {

        for (String eachWord : lines) {

            if (eachWord.length() == 0 || eachWord.trim().startsWith("//")) continue;

            String[] parts = eachWord.split("/");
            if (map.containsKey(parts[0])) {
                StringBuilder registredWord = map.get(parts[0]);
                appendTagsTo(registredWord, parts);

            } else {
                StringBuilder sb = new StringBuilder();
                appendTagsTo(sb, parts);
                map.put(parts[0], sb);
            }
        }
    }

    private void appendTagsTo(StringBuilder tagBuffer, String[] tags) {
        String compoundNoun = null;
        for (int i=1; i < tags.length; i++) {
            if (tagBuffer.indexOf(tags[i]) > 0) {
                System.out.println("Duplicated Tag: " + tags[i] + "=>" + tagBuffer.toString());
                continue;
            }

            if (tags[i].startsWith("C(")) {
                compoundNoun = tags[i];
            } else {
               if (tagBuffer.length() == 0) {
                    tagBuffer.append(tags[i]);
                } else {
                    tagBuffer.append("/").append(tags[i]);
                }
            }
        }

        if (compoundNoun != null) {
            tagBuffer.append("/").append(compoundNoun);
        }
    }

}
