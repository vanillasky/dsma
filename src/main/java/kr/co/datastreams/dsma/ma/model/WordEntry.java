package kr.co.datastreams.dsma.ma.model;

import kr.co.datastreams.dsma.ma.PosTag;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 7. 16
 * Time: 오전 10:10
 * To change this template use File | Settings | File Templates.
 */
public class WordEntry  {

    public static final int IDX_NOUN = 0;  // 명사
    public static final int IDX_VERB = 1;  // 동사
    public static final int IDX_BUSA = 2;  // 부사
    public static final int IDX_DOV = 3;   //
    public static final int IDX_BEV = 4;   //
    public static final int IDX_NE = 5;    //
    public static final int IDX_ADJ = 6;   // 형용사
    public static final int IDX_NPR = 7;   // 명사의 분류 (M:Measure)
    public static final int IDX_CNOUNX = 8; //
    public static final int IDX_REGURA = 9; // 불규칙

    protected long posTag;
    protected String string;
    protected char[] features;
    protected List<CompoundWordEntry> compounds = new ArrayList<CompoundWordEntry>();


    public static WordEntry createWithPosTags(String word, String[] tags) {
        return new WordEntry(word, tags);
    }

    public static WordEntry create(String word) {
        return new WordEntry(word);
    }

    public static WordEntry createWithFeature(String word, char[] chars) {
        return new WordEntry(word, chars);
    }

    private WordEntry(String word) {
        this.string = word;
    }

    private WordEntry(String word, String[] tags) {
        this.string = word;
        this.posTag = buildTags(tags);
    }


    private WordEntry(String word, char[] features) {
        this.string = word;
        this.features = features;
    }

    private WordEntry(String word, char[] features, List<CompoundWordEntry> compounds) {
        this.string = word;
        this.features = features;
        this.compounds = compounds;
    }



    public String toString() {
       StringBuilder buf = new StringBuilder();
       buf.append("WordEntry { word: ").append(string)
          .append(", features:").append(features != null ? String.valueOf(features) : "null")
          .append(", compounds: ");
       for (CompoundWordEntry each : compounds) {
           buf.append(each).append(",");
       }
       buf.append("}");
       return buf.toString();
    }

    public char[] getFeatures() {
        return features;
    }

    public char getFeature(int index) {
        return ( features != null && features.length > index ) ? features[index] : '0';
    }

    public List<CompoundWordEntry> getCompounds() {
        return compounds;
    }

    void setCompounds(List<CompoundWordEntry> compounds) {
        this.compounds = compounds;
    }

    /**
     * 명사로 사용할 수 있는지를 반환한다.
     *
     * @return true if the word can be noun.
     */
    public boolean availableAsNoun() {
        return availableAs(IDX_NOUN);
    }

    /**
     * 동사로 사용할 수 있는 단어인지를 반환한다.
     *
     * @return true if the word can be verb.
     */
    public boolean availableAsVerb() {
        return availableAs(IDX_VERB);
    }

    private boolean availableAs(int propertyIndex) {
        return getFeature(propertyIndex) == '1';
    }

    public String getString() {
        return string;
    }

    public boolean isTagOf(long tagNum) {
        return (this.posTag & Long.MAX_VALUE & tagNum) > 0L;
    }

    public long tag() {
        return this.posTag;
    }


    public static long buildTags(String[] tags) {
        long result = 0L;
        Long tagNum = 0L;
        for (String each : tags) {
            if (each.equalsIgnoreCase("N")) {
                each = "NN";
            } else if (each.equalsIgnoreCase("V")) {
                each = "VV";
            }

            tagNum = PosTag.getTagNum(each.trim());
            if (tagNum != null) {
                result = result | tagNum;
            }
        }
        return result;
    }



}
