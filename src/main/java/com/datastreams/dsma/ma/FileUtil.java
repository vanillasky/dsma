package com.datastreams.dsma.ma;

import com.datastreams.dsma.dic.DicEnv;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 6. 24
 * Time: 오후 4:10
 * To change this template use File | Settings | File Templates.
 */
public class FileUtil {



    public static List readLines(String fName, String encoding) throws Exception {
        InputStream in = null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(FileUtil.class.getResourceAsStream(fName), DicEnv.ENCODING));;
            List list = new ArrayList();
            String line;
            System.out.println("read file:" + fName);
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
               list.add(line);
            }

//            File file = getClassLoaderFile(fName);
//            if(file!=null&&file.exists()) {
//                in = openInputStream(file);
//            } else {
//                in = new ByteArrayInputStream(readByteFromCurrentJar(fName));
//            }

//            return readLines(in, encoding);
            reader.close();
            return list;

        } finally {
            closeQuietly(in);
        }
    }

    public static File getClassLoaderFile(String filename) throws Exception  {
        // note that this method is used when initializing logging, so it must
        // not attempt to log anything.
        File file = null;
        ClassLoader loader = FileUtil.class.getClassLoader();
        URL url = loader.getResource(filename);
        if (url == null) {
            url = ClassLoader.getSystemResource(filename);
            if (url == null) {
                throw new Exception("Unable to find " + filename);
            }
            file = toFile(url);
        } else {
            file = toFile(url);
        }
        if (file==null||!file.exists()) {
            return null;
        }
        return file;
    }

    public static FileInputStream openInputStream(File file) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }
            if (file.canRead() == false) {
                throw new IOException("File '" + file + "' cannot be read");
            }
        } else {
            throw new FileNotFoundException("File '" + file + "' does not exist");
        }
        return new FileInputStream(file);
    }

    public static File toFile(URL url) {
        if (url == null || !url.getProtocol().equals("file")) {
            return null;
        } else {
            String filename = url.getFile().replace('/', File.separatorChar);
            int pos =0;
            while ((pos = filename.indexOf('%', pos)) >= 0) {
                if (pos + 2 < filename.length()) {
                    String hexStr = filename.substring(pos + 1, pos + 3);
                    char ch = (char) Integer.parseInt(hexStr, 16);
                    filename = filename.substring(0, pos) + ch + filename.substring(pos + 3);
                }
            }
            return new File(filename);
        }
    }

    public static void closeQuietly(InputStream input) {
        try {
            if (input != null) {
                input.close();
            }
        } catch (IOException ioe) {
            // ignore
        }
    }


    public static byte[] readByteFromCurrentJar(String resource) throws Exception {

        String	jarPath = FileUtil.class.getProtectionDomain().getCodeSource().getLocation().getPath();

        JarResources jar = new JarResources(jarPath);
        try {
            return jar.getResource(resource);
        } catch (Exception e) {
            throw new Exception(e.getMessage(),e);
        }
    }

    public static List readLines(InputStream input, String encoding) throws IOException {
        if (encoding == null) {
            return readLines(input);
        } else {
            InputStreamReader reader = new InputStreamReader(input, encoding);
            return readLines(reader);
        }
    }


    public static List readLines(InputStream input) throws IOException {
        InputStreamReader reader = new InputStreamReader(input);
        return readLines(reader);
    }

    public static List readLines(Reader input) throws IOException {
        BufferedReader reader = new BufferedReader(input);
        List list = new ArrayList();
        String line = reader.readLine();
        while (line != null) {
            list.add(line);
            line = reader.readLine();
        }
        return list;
    }

}
