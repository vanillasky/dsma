package com.datastreams.dsma.ma;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 6. 24
 * Time: 오후 3:50
 * To change this template use File | Settings | File Templates.
 */
public class Candidates implements Cloneable {

    private String stem;
    private String josa;
    private String eomi;
    private int wordPattern;

    public Candidates(String stem, String josa, String eomi, int wordPattern) {
        this.stem = stem;
        this.josa = josa;
        this.eomi = eomi;
        this.wordPattern = wordPattern;
    }
}
