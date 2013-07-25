package kr.co.datastreams.dsma.ma;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 7. 25
 * Time: 오전 11:24
 * To change this template use File | Settings | File Templates.
 */
public class Variant {

    private final String stem;
    private final String ending;
    private final String prefinalEnding;
    private final String josa;
    public  static final Variant EMPTY = new Variant();

    private Variant(String stem, String ending, String prefinalEnding, String josa) {
        this.stem = stem;
        this.ending = ending;
        this.prefinalEnding = prefinalEnding;
        this.josa = josa;
    }

    private Variant() {
        this.stem = null;
        this.ending = null;
        this.prefinalEnding = null;
        this.josa = null;
    }


    public static Variant create(String stem, String ending) {
        return new Variant(stem, ending, null, null);
    }

    public String getStem() {
        return stem;
    }

    public String getEnding() {
        return ending;
    }

    public String getPrefinalEnding() {
        return prefinalEnding;
    }

    public String getJosa() {
        return josa;
    }

    public boolean isEmpty() {
        return stem == null ? true : false;
    }
}
