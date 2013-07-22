package kr.co.datastreams.dsma.ma;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 7. 16
 * Time: 오전 10:10
 * To change this template use File | Settings | File Templates.
 */
public class WordEntry extends Token {

    protected char[] features;
    protected List<CompoundWordEntry> compounds = new ArrayList<CompoundWordEntry>();

    public WordEntry(String word) {
        this.string = word;
    }

    public WordEntry(String word, char[] features) {
        this.string = word;
        this.features = features;
    }

    public WordEntry(String word, char[] features, List<CompoundWordEntry> compounds) {
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

    public List<CompoundWordEntry> getCompounds() {
        return compounds;
    }

    void setCompounds(List<CompoundWordEntry> compounds) {
        this.compounds = compounds;
    }
}
