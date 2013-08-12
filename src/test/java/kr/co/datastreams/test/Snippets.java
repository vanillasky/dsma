package kr.co.datastreams.test;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 7. 30
 * Time: 오전 9:59
 * To change this template use File | Settings | File Templates.
 */
public class Snippets {

    @Test
    public void testByteArray() throws Exception {
        String str = "1110010110";
        byte[] bytes = new byte[str.length()];

        for (int i=0;i < bytes.length; i++) {
            bytes[i] = (byte)Integer.parseInt(str.charAt(i)+"");
            System.out.println(bytes[i]);
        }

        System.out.println(bytes[0] == 1);
        System.out.println(bytes[3] == 0);
    }


    @Test
    public void testLong() throws Exception {
        System.out.println(Long.bitCount(Long.MIN_VALUE));
        System.out.println(Long.bitCount(Long.MAX_VALUE));
    }
}
