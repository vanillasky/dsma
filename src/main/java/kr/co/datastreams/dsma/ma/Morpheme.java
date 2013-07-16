package kr.co.datastreams.dsma.ma;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 7. 16
 * Time: 오전 10:10
 * To change this template use File | Settings | File Templates.
 */
public class Morpheme {
    private String word;

    public Morpheme(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public String toString() {
       return new StringBuilder("Morpheme { word: ").append(word).append("}").toString();
    }
}
