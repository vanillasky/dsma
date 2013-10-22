package com.datastreams.nlp.ma.dic;

import com.datastreams.nlp.common.annotation.Immutable;
import com.datastreams.nlp.ma.constants.PosTag;
import kr.co.datastreams.commons.util.StringUtil;

import javax.management.ImmutableDescriptor;


/**
 * 사전 수록 단어
 *
 * User: shkim
 * Date: 13. 7. 16
 * Time: 오전 10:10
 *
 */
@Immutable
public class WordEntry {

    protected long posTag;
    protected String string;
    protected String[] tags;
    protected PosTag.IrregularType irregularType;

    public static WordEntry createWithTags(String word, String... tags) {
        return new WordEntry(word, tags);
    }

    public static WordEntry createWithTag(String word, long tagNum) {
        return new WordEntry(word, tagNum);
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

    private WordEntry(String word, long  tagNum) {
        this.string = word;
        this.posTag = tagNum;
        this.tags = new String[]{PosTag.getTag(tagNum)};
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

    public WordEntry add(String syllable, long tagNum) {
        return WordEntry.createWithTag(getString() + syllable, tagNum);
    }
}
