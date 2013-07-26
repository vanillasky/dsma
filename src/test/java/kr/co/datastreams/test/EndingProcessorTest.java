package kr.co.datastreams.test;

import kr.co.datastreams.dsma.ma.EndingProcessor;
import kr.co.datastreams.dsma.ma.Variant;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 7. 24
 * Time: 오후 1:18
 * To change this template use File | Settings | File Templates.
 */
public class EndingProcessorTest {

    @Test
    public void testSplitEomi_ㄹ어미() throws Exception {
        String text = "용기를";
        Variant result = EndingProcessor.splitEnding(text, "");
        assertEquals("용기르", result.getStem());
        assertEquals("ㄹ", result.getEnding());
    }


    @Test
    // 마지막 글자의 종성이 없고, 'ㅘ'로 끝나면서 '어'와 결합될 수 있는 어미
    public void testSplitEomi_종성X_ㅘ_어() throws Exception {
        String text = "봐";
        Variant result = EndingProcessor.splitEnding(text, "");
        assertEquals("보", result.getStem());
        assertEquals("아", result.getEnding());
    }

    @Test
    // 마지막 글자의 종성이 없고, 'ㅕ'로 끝나면서 '어'와 결합될 수 있는 어미
    public void testSplitEomi_종성X_ㅕ() throws Exception {
        String text = "보여";
        Variant result = EndingProcessor.splitEnding(text, "");
        assertEquals("보이", result.getStem());
        assertEquals("어", result.getEnding());
    }

    @Test
    // 마지막 글자의 종성이 없고, 'ㅝ'로 끝나면서 '어'와 결합될 수 있는 어미
    public void testSplitEomi_종성X_ㅝ() throws Exception {
        String text = "꿔";
        Variant result = EndingProcessor.splitEnding(text, "");
        assertEquals("꾸", result.getStem());
        assertEquals("어", result.getEnding());
    }

    @Test
    // 마지막 글자의 종성이 없고, 'ㅙ'로 끝나면서 '어'와 결합될 수 있는 어미
    public void testSplitEomi_종성X_ㅙ() throws Exception {
        String text = "선봬";
        Variant result = EndingProcessor.splitEnding(text, "");
        assertEquals("선뵈", result.getStem());
        assertEquals("어", result.getEnding());

        //TODO Check '외','어'로 분리되는 것이 맞는지???
        text = "왜";
        result = EndingProcessor.splitEnding(text, "");
        assertEquals("외", result.getStem());
        assertEquals("어", result.getEnding());

    }


    @Test
    // 마지막 글자의 종성이 없고, 'ㅐ'로 끝나면서 '어'와 결합될 수 있는 어미
    public void testSplitEomi_종성X_ㅐ() throws Exception {
        //TODO '하여'가 맞는 분석이 아닐런지..
        String text = "해";
        Variant result = EndingProcessor.splitEnding(text, "");
        assertEquals("하", result.getStem());
        assertEquals("어", result.getEnding());
    }

    @Test
    // 어간부의 마지막 음절과 어미부의 첫부분이 '오' 와 '너라'
    public void testSplitEomi_너라() throws Exception {
        //TODO 이리 오너라 -> 이리(N),오너(N),라(j)로 분석
        String text = "오너라";
        Variant result = EndingProcessor.splitEnding("오", "너라");
        assertEquals("오", result.getStem());
        assertEquals("너라", result.getEnding());
    }

    @Test
    public void testSplitEomi_히_이Ending() throws Exception {
        String text = "고요히";
        Variant result = EndingProcessor.splitEnding(text, "");
        assertEquals("고요하", result.getStem());
        assertEquals("이", result.getEnding());

        text = "꾸준히";
        result = EndingProcessor.splitEnding(text, "");
        assertEquals("꾸준하", result.getStem());
        assertEquals("이", result.getEnding());
    }

    @Test
    public void testSplitEomi_하여() throws Exception {
        String text = "사용하여";
        Variant result = EndingProcessor.splitEnding("사용하", "여");
        assertEquals("사용하", result.getStem());
        assertEquals("어", result.getEnding());

        text = "고려하여";
        result = EndingProcessor.splitEnding("고려하", "여");
        assertEquals("고려하", result.getStem());
        assertEquals("어", result.getEnding());
    }

    @Test
    public void testSplitEomi() throws Exception {
//        String text = "피곤하시겠다";
//        String[] result = EndingProcessor.splitEnding("피곤하시겠", "다");
//        System.out.println(result[0] + "," +result[1]);

        Variant result = EndingProcessor.splitEnding("사랑해서", "");
        System.out.println(result.getStem() + "," + result.getEnding());
    }

    @Test
    public void testSplitPomi_셨겠() throws Exception {
        Variant result = EndingProcessor.splitPrefinalEnding("피곤하셨겠");
        assertEquals("피곤하", result.getStem());
        assertEquals("시었겠", result.getPrefinalEnding());
    }

    @Test
    public void testSplitPomi_이였() throws Exception {
        Variant result = EndingProcessor.splitPrefinalEnding("가시였");
        assertEquals("가시이", result.getStem());
        assertEquals("었", result.getPrefinalEnding());
    }

    @Test
    public void testSplitPomi_끝음절이_시_인용언() throws Exception {
        Variant result = EndingProcessor.splitPrefinalEnding("성가시");
        assertTrue(result.isEmpty());
    }

}
