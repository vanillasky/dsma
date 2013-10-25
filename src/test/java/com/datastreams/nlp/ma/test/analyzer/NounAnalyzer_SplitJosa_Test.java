package com.datastreams.nlp.ma.test.analyzer;

import com.datastreams.nlp.ma.analyzer.NounAnalyzer;
import com.datastreams.nlp.ma.model.MorphemeList;
import com.datastreams.nlp.ma.model.Token;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

// 체언분석기 테스트

public class NounAnalyzer_SplitJosa_Test {

    private NounAnalyzer nounAnalyzer;

    @Before
    public void setUp() throws Exception {
        nounAnalyzer = new NounAnalyzer();
    }

    @Test
    public void test_에_로끝나는_명사() throws Exception {
        Token token = Token.korean("멍에는");
        String[] expected = {"<멍,NNG>+<에는,JX>", "<멍에,NNG>+<는,JX>"};

        List<MorphemeList> candidates = nounAnalyzer.execute(token);
        assertEquals(2, candidates.size());
        assertEquals(expected[0], candidates.get(0).toString());
        assertEquals(expected[1], candidates.get(1).toString());
    }


    @Test
    public void testSimpleWord_NJ_학교에서만은() throws Exception {
        Token token = Token.korean("학교에서만은");
        String expected = "<학교,NNG>+<에서만은,JKG>";

        List<MorphemeList> candidates= nounAnalyzer.execute(token);
        assertEquals(expected, candidates.get(0).toString());
    }

    @Test
    public void testSimpleWord_NJ_거리에서는() throws Exception {
        Token token = Token.korean("거리에서는");
        String expected = "<거리,NNG>+<에서는,JKB>";

        List<MorphemeList> candidates= nounAnalyzer.execute(token);
        assertEquals(expected, candidates.get(0).toString());

    }


    @Test
    public void testSimpleWord_ADVJ_갑자기는() throws Exception {
        Token token = Token.korean("갑자기는");
        String expected = "<갑자기,MAG>+<는,JX>";

        List<MorphemeList> candidates= nounAnalyzer.execute(token);
        assertEquals(expected, candidates.get(0).toString());
    }

    @Test
    public void testSimpleWord_NJ_가격표시기보다는() throws Exception {
        Token token = Token.korean("가격표시기보다는");
        String expected = "<가격표시기,NNG>+<보다는,JKB>";

        List<MorphemeList> candidates= nounAnalyzer.execute(token);
        assertEquals(expected, candidates.get(0).toString());
    }


    // 체언이 사전에 없어서 접미사를 분리, 체언부에 포함시키는 경우
    @Test
    public void testSimpleWord_NSJ_접미사를_분리해서_체언부에포함() throws Exception {
        Token t1 = Token.korean("술내기에서");
        String expected = "<술내기,NNG>+<에서,JKS>";
        List<MorphemeList> candidates= nounAnalyzer.execute(t1);
        System.out.println(candidates.get(0).getFirst().getSuffix());
        assertEquals(expected, candidates.get(0).toString());

        Token t2 = Token.korean("안성댁은");
        expected = "<안성댁,NNP>+<은,JX>";
        candidates = nounAnalyzer.execute(t2);
        assertEquals(expected, candidates.get(0).toString());
        assertEquals("<댁,XSN>", candidates.get(0).getFirst().getSuffix().toString());
    }

    @Test
    public void test_미등록단어() throws Exception {
        Token t1 = Token.korean("우쌀리가봉");

        List<MorphemeList> candidates= nounAnalyzer.execute(t1);
        assertTrue(candidates.isEmpty());

    }


    @Test
    public void test_의자로부터는() throws Exception {
        Token t = Token.korean("의자로부터는");
        String expected = "<의자,NNG>+<로부터는,JX>";
        List<MorphemeList> candidates = nounAnalyzer.execute(t);
        assertEquals(expected, candidates.get(0).toString());
        System.out.println(candidates.get(0).getConclusion().description());
    }


    @Test
    public void test_조사_깨나() throws Exception {
        Token t = Token.korean("의자깨나");
        String expected = "<의자,NNG>+<깨나,JX>";
        List<MorphemeList> candidates = nounAnalyzer.execute(t);
        assertEquals(expected, candidates.get(0).toString());
    }

    @Test
    public void test_조사_하고() throws Exception {
        Token t = Token.korean("집하고");
        String expected = "<집,NNG>+<하고,JC>";
        List<MorphemeList> candidates = nounAnalyzer.execute(t);
        //assertEquals(2, candidates.size());
        assertEquals(expected, candidates.get(0).toString());
    }

    @Test
    public void test_조사_만이() throws Exception {
        Token[] tokens = new Token[]{Token.korean("당신만이"), Token.korean("의자만이")};
        String[] expected = new String[]{"<당신,NP>+<만이,JX>", "<의자,NNG>+<만이,JX>"};

        int i = 0;
        for (Token t : tokens) {
            List<MorphemeList> candidates = nounAnalyzer.execute(t);
            assertEquals(1, candidates.size());
            assertEquals(expected[i], candidates.get(0).toString());
            i++;
        }
    }

    @Test
    public void test_명사형전성어미() throws Exception {
        Token t = Token.korean("사랑함은");
        List<MorphemeList> candidates = nounAnalyzer.execute(t);
        System.out.println(candidates);
    }


    @Test
    public void testWithFile() throws Exception {
        List<String> lines = read("src/test/java/com/datastreams/nlp/ma/test/형태소분석_어미의결합양상.txt");
        List<String> results = new ArrayList<String>();

        for (String each : lines) {
            Token token = Token.korean(each.trim());
            List<MorphemeList> candidates = nounAnalyzer.execute(token);
            for (MorphemeList result : candidates) {
                results.add(result.toString());
            }
        }

        for (String each : results) {
            System.out.println(each);
        }
    }

    public static List<String> read(String file) throws IOException {
        List<String> lines = new ArrayList<String>();
        BufferedReader reader = new BufferedReader(new FileReader(new File(file)));
        String line = null;
        while ((line = reader.readLine()) != null) {
            if (line.trim().length() == 0) continue;
            lines.add(line);
        }
        reader.close();
        return lines;
    }
}
