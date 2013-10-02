package kr.co.datastreams.dsma.model;

import kr.co.datastreams.commons.util.StringUtil;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 7. 16
 * Time: 오전 10:10
 * To change this template use File | Settings | File Templates.
 */
public class WordEntry  {

//    public static final int IDX_NOUN = 0;  // 명사
//    public static final int IDX_VERB = 1;  // 동사
//    public static final int IDX_BUSA = 2;  // 부사
//    public static final int IDX_DOV = 3;   //
//    public static final int IDX_BEV = 4;   //
//    public static final int IDX_NE = 5;    //
//    public static final int IDX_ADJ = 6;   // 형용사
//    public static final int IDX_NPR = 7;   // 명사의 분류 (M:Measure)
//    public static final int IDX_CNOUNX = 8; //
//    public static final int IDX_REGURA = 9; // 불규칙

    protected long posTag;
    protected String string;
    protected String[] tags;
    protected PosTag.IrregularType irregularType;

    public static WordEntry createWithTags(String word, String[] tags) {
        return new WordEntry(word, tags);
    }

    public static WordEntry create(String word) {
        return new WordEntry(word);
    }

    private WordEntry(String word) {
        this.string = word;
    }

    private WordEntry(String word, String[] tags) {
        this.string = word;
        this.tags = tags;
        buildTags(tags);
    }

    public String toString() {
       StringBuilder buf = new StringBuilder();
       buf.append("WordEntry { word: ").append(string)
          .append(", tag:").append(StringUtil.join(tags, ","))
          .append(", irregularType:").append(irregularType)
          .append("}");
       return buf.toString();
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


    private void buildTags(String[] tags) {
        long result = 0L;
        Long tagNum = 0L;
        for (String each : tags) {
            tagNum = PosTag.getTagNum(each.trim());
            if (tagNum > 0) {
                result = result | tagNum;
            } else if (each.startsWith("IRR")) {
                this.irregularType = parseIrregularType(each.trim());
            }
        }
        this.posTag = result;
    }

    private PosTag.IrregularType parseIrregularType(String tag) {
        return tag.equalsIgnoreCase("IRRB") ? PosTag.IrregularType.IRRB :
               tag.equalsIgnoreCase("IRRS") ? PosTag.IrregularType.IRRS :
               tag.equalsIgnoreCase("IRRD") ? PosTag.IrregularType.IRRD :
               tag.equalsIgnoreCase("IRRL") ? PosTag.IrregularType.IRRL :
               tag.equalsIgnoreCase("IRRH") ? PosTag.IrregularType.IRRH :
               tag.equalsIgnoreCase("IRRLU") ? PosTag.IrregularType.IRRLU :
               tag.equalsIgnoreCase("IRRLE") ? PosTag.IrregularType.IRRLE : null;
    }

    public boolean isIrregular() {
        return irregularType != null;
    }


    public PosTag.IrregularType getIrregularType() {
        return irregularType;
    }


}
