package com.datastreams.nlp.ma.test.dic;

import kr.co.datastreams.dsma.dic.SyllableDic;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 7. 23
 * Time: 오후 4:18
 * To change this template use File | Settings | File Templates.
 */
public class SyllableDicTest {

    char[] firstJosaSyllables = {
        '가', '같', '게', '고', '과', '까', '께', '나', '는', '니',
        '다', '대', '더', '도', '든', '라', '랑', '로', '를', '마',
        '만', '말', '며', '밖', '보', '부', '서', '아', '야', '에',
        '엘', '여', '와', '요', '으', '은', '을', '의', '이', '인',
        '엔', '일', '조', '처', '치', '커', '토', '하'
    };


    char[] secondJosaSyllables = {
            '가', '게', '고', '곤', '과', '까', '나', '녕', '는', '니',
            '다', '도', '든', '들', '따', '라', '랑', '러', '럼', '로',
            '록', '론', '를', '마', '만', '말', '며', '보', '부', '서',
            '선', '슨', '써', '씩', '야', '에', '엔', '여', '옵', '와',
            '은', '을', '의', '이', '인', '저', '조', '즉', '지', '진',
            '차', '처', '치', '커', '큼', '터', '테', '하'
    };

    @Test
    public void testFeatureComparison() throws Exception {
        String str = "가";
        char[] features = "1111111111110111100000000000000000000001".toCharArray();
        byte[] bytes = new byte[features.length];
        for (int i=0;i < bytes.length; i++) {
            bytes[i] = (byte)Integer.parseInt(features[i]+"");
        }


        byte[] result = SyllableDic.getFeature(str.charAt(0));

        assertArrayEquals(bytes, result);
    }

    // 조사의 첫음절로 사용되는 48개 음절 확인
    @Test
    public void testIsFirstSyllableOnJosa() throws Exception {
        for (char each : firstJosaSyllables) {
            assertTrue(SyllableDic.isFirstJosaSyllable(each));
        }
    }

    // 조사의 두번째이상의 사용되는 48개 음절 확인
    @Test
    public void testIsSecondSyllableOnJosa() throws Exception {
        for (char each : secondJosaSyllables) {
            assertTrue(SyllableDic.isSecondJosaSyllable(each));
        }
    }

//    @Test
//    public void testNLMB() throws Exception {
//        System.out.println(SyllableDic.isVerbEndsWithNLMBSyllable('갠', 'ㄹ'));
//    }

}
