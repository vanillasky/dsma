package com.datastreams.nlp.ma.constants;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 10. 25
 * Time: 오후 5:23
 * To change this template use File | Settings | File Templates.
 */
public enum ConclusionPoint {
    NA(""),
    MoreThan2SyllableGMFound("2음절 이상의 후절어(조사/어미) 발견");


    private String description;


    ConclusionPoint(String s) {
        description = s;
    }

    public String description() {
        return description;
    }
}
