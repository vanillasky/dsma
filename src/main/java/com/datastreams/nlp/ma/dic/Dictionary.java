package com.datastreams.nlp.ma.dic;

import com.datastreams.nlp.ma.constants.PosTag;
import kr.co.datastreams.commons.util.StringUtil;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 사전 검색을 위한 Helper 클래스.
 * 모든 사전 검색 기능은 Dictionary 클래스를 통해서만 외부에 제공한다.
 *
 *
 * User: shkim
 * Date: 13. 10. 16
 * Time: 오후 5:47
 *
 */
public class Dictionary {

    private static final TrieBaseDic fixedWordDic = FixedWordDic.getInstnace();
    private static final TrieBaseDic verbWordDic = VerbDic.getInstance();
    private static final MapBaseDic josaDic = JosaDic.getInstance();
    private static final MapBaseDic eomiDic = EomiDic.getInstance();
    private static final MapBaseDic prefixDic = PrefixDic.getInstance();
    private static final MapBaseDic suffixDic = SuffixDic.getInstance();


    /**
     * fixed(명사, 대명사, 수사, 관형사, 부사, 감탄사) 검색
     *
     * @param word - the string to find
     * @return
     * */
    public static WordEntry searchFixedWord(String word) {
        return fixedWordDic.getWord(word);
    }

    /**
     * 기본 어위 사전에서 prefix로 시작되는 단어를 찾는다
     * @param prefix
     * @return
     */
    public static Iterator searchFixedWordPrefixedBy(String prefix) {
        return fixedWordDic.searchPrefixedBy(prefix);
    }

    /**
     * 명사를 찾는다
     * @param word
     * @return
     */
    public static WordEntry searchNoun(String word) {
        return fixedWordDic.searchWith(word, PosTag.N);
    }

    /**
     * 명사 또는 부사를 찾는다
     * @param word
     * @return
     */
    public static WordEntry searchNounOrAbVerb(String word) {
        return fixedWordDic.searchWith(word, PosTag.N | PosTag.AD);
    }

    /**
     * fixed(명사, 대명사, 수사, 관형사, 부사, 감탄사)가 사전에 있는지 확인
     * @param word
     * @return
     */
    public static boolean existsFixedWord(String word) {
        return fixedWordDic.exists(word);
    }


    /**
     * 용언 사전에서 단어를 찾는다
     *
     * @param word - the word to find
     * @return WordEntry that is possible to use as verb.
     */
    public static WordEntry searchVerb(String word) {
        return verbWordDic.getWord(word);
    }

    /**
     * 용언 사전에서 prefix로 시작되는 단어를 찾는다
     * @param prefix
     * @return
     */
    public static Iterator searchVerbPrefixedBy(String prefix) {
        return verbWordDic.searchPrefixedBy(prefix);
    }

    /**
     * 사전에 수록된 어미를 찾아서 반환한다.
     * 미수록 단어이면 null을 반환.
     *
     * @param word - 어미
     * @return
     */
    public static WordEntry searchEomi(String word) {
        return eomiDic.search(word);
    }

    /**
     * 사전에 수록된 어미인지 확인한다.
     *
     * @param eomi
     * @return
     */
    public static boolean existsEomi(String eomi) {
        return eomiDic.exists(eomi);
    }


    public static WordEntry searchPrefix(String word) {
        return prefixDic.search(word);
    }

    public static boolean existsPrefix(String word) {
        return prefixDic.exists(word);
    }

    public static WordEntry searchSuffix(String word) {
        return suffixDic.search(word);
    }

    public static boolean existsSuffix(String word) {
        return suffixDic.exists(word);
    }


    public static WordEntry searchJosa(String word) {
        return josaDic.search(word);
    }

    public static boolean existsJosa(String word) {
        return josaDic.exists(word);
    }


    /**
     * PosTag에 해당하는 단어가 사전에 수록되어 있는지 확인한다.
     *
     * @param word - word to find
     * @param tagNum - PosTag to find
     * @return
     */
    public static boolean exists(String word, long tagNum) {
        WordEntry entry;

        if (PosTag.isTagOf(PosTag.V, tagNum)) {   // 체언
            entry = verbWordDic.searchWith(word, tagNum);
        }
        else if (PosTag.isTagOf(PosTag.J, tagNum)) {    // 조사
            entry = josaDic.searchWith(word, tagNum);
        }
        else if (PosTag.isTagOf(PosTag.XS, tagNum)) {  // 접미사
            entry = suffixDic.searchWith(word, tagNum);
        }
        else if (PosTag.isTagOf(PosTag.XPN, tagNum)) { // 접두사
            entry =  prefixDic.searchWith(word, tagNum);
        }
        else { // 명사, 관사, 부사, 감탄사 등
            entry = fixedWordDic.searchWith(word, tagNum);
        }

        return entry == null ? false : true;
    }

    public static WordEntry search(String word, long tagNum) {
        WordEntry entry;

        if (PosTag.isTagOf(PosTag.V, tagNum)) {   // 체언
            entry = verbWordDic.searchWith(word, tagNum);
        }
        else if (PosTag.isTagOf(PosTag.J, tagNum)) {    // 조사
            entry = josaDic.searchWith(word, tagNum);
        }
        else if (PosTag.isTagOf(PosTag.XS, tagNum)) {  // 접미사
            entry = suffixDic.searchWith(word, tagNum);
        }
        else if (PosTag.isTagOf(PosTag.XPN, tagNum)) { // 접두사
            entry =  prefixDic.searchWith(word, tagNum);
        }
        else { // 명사, 관사, 부사, 감탄사 등
            entry = fixedWordDic.searchWith(word, tagNum);
        }

        return entry;
    }


    /**
     * prefix 로 시작되는 단어를 찾는다.
     * 기본 어휘 사전에 없는 경우에는 용언 사전에서 찾는다.
     *
     * @param prefix
     * @return
     */
    public static Iterator searchPrefixedBy(String prefix) {
        List<WordEntry> entries = new ArrayList<WordEntry>();

        Iterator<WordEntry> fixedWords = fixedWordDic.searchPrefixedBy(prefix);
        Iterator<WordEntry> verbWords = verbWordDic.searchPrefixedBy(prefix);

        while (fixedWords.hasNext()) {
             entries.add(fixedWords.next());
        }

        while (verbWords.hasNext()) {
            entries.add(verbWords.next());
        }

        return entries.iterator();
    }


    public static void printFixedWordDictionary(PrintWriter writer) {
        fixedWordDic.print(writer);
    }

    public static void printVerbDictionary(PrintWriter writer) {
        verbWordDic.print(writer);
    }


}
