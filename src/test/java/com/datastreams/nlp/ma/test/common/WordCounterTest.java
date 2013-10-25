package com.datastreams.nlp.ma.test.common;

import com.datastreams.nlp.common.util.WordCounter;
import kr.co.datastreams.commons.util.FileUtil;
import kr.co.datastreams.commons.util.StringUtil;

import org.junit.Test;


import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

/**
 *
 * User: shkim
 * Date: 13. 9. 26
 * Time: 오후 1:23
 *
 */
public class WordCounterTest {

    String source =
            "최경주, 물오른 '별빛사냥'\n" +
            "'PGA 올스타전' 투어 챔피언십 공동9위 \n" +
            "최경주(32ㆍ슈페리어)가 미국프로골프(PGA)투어 마지막 공식대회인 투어챔피언십(총상금 500만달러)에서 공동9위에 오르며 시즌 7번째 톱10에 진입했다. \n" +
            "최경주는 4일(한국시간) 조지아주 애틀랜타의 이스트레이크GC(파 70)에서 열린 대회 최종라운드서 버디 4개를 낚고 보기 2개를 범해 2언더파 68타를 쳐 최종합계 3언더파 277타로 레티에프 구센(남아공)과 공동 9위를 차지했다. \n" +
            "상금 순위 30위 이내의 세계톱랭커들이 출전, 자웅을 겨룬 이번 대회에서 14만6,000달러의 상금을 보탠 최경주는 시즌 상금누계가 220만4,970달러에 달해 상금랭킹 17위에 랭크됐다. \n" +
            "투어챔피언십과 동시에 벌어진 서든팜뷰로클래식의 4라운드가 우천으로 5일로 연기됨에 따라 시즌상금랭킹이 최종 확정되지는 않았으나 최경주는 무난히 상금랭킹 20위안에 들 수 있을 것으로 보인다. \n" +
            "시즌 2승과 함께 일곱번째 톱10에 진입한 최경주는 2000년 PGA투어 데뷔 이후 올해 최고의 성적을 거뒀다. 또 내년시즌 4대 메이저대회중 마스터스, US오픈, 브리티시오픈 출전권을 확보했다. PGA선수권은 내년시즌 상금랭킹에 따라 출전권이 주어진다. \n" +
            "올해 691만2,625달러를 벌어들인 타이거 우즈(미국)는 서든 팜 뷰로클래식 결과에 관계없이 통산 5번째이자 4시즌 연속 상금왕에 올랐다. 우즈는 이번에 오른쪽 무릎부상으로 4언더파 276타를 쳐 공동 7위에 그쳤다. \n" +
            "최경주는 이날 퍼팅수는 30개로 다소 많았으나 14개의 드라이버샷(비거리 263.5야드)중 10개가 페어웨이에 안착하는 등 정교한 샷을 앞세워 파4의 3,4번홀에서 잇따라 버디를 잡아내며 선전, 공동 9위로 라운드를 마쳤다. \n" +
            "비제이 싱(피지)은 최종합계 12언더파 268타를 기록, 찰스 하웰3세(미국)를 2타차로 제치고 정상에 올랐다. 시즌 2승, 통산 11승을 챙긴 싱은 우승상금 90만달러를 보태 필 미켈슨(미국ㆍ431만1,971달러)에 이어 상금랭킹 3위(375만6,563달러)에 자리했다. ";

    @Test
    public void testCountWord() throws Exception {
        String word = "최경주는";
        int numOfThread = 4;

        final String[] words = source.split(" ");
        int expectedWordCount = count(words, word) * numOfThread;

        Thread[] threads = new Thread[numOfThread];
        final WordCounter counter = new WordCounter();

        for (int i=0; i < threads.length; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (String word : words) {
                        counter.count(word);
                    }
                }
            });

        }

        for (int i=0; i < threads.length; i++) {
            threads[i].start();
            TimeUnit.MILLISECONDS.sleep(100);
        }


        System.out.println("word count:"+ counter.gerFrequencyOfUse(word));
        assertEquals(expectedWordCount, (int)counter.gerFrequencyOfUse(word));
    }

    private int count(String[] words, String word) {
        int result = 0;
        for (String each : words) {
            if (each.equals(word)) {
                result++;
            }
        }
        return result;
    }
}