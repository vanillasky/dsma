package kr.co.datastreams.dsma.model;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 9. 13
 * Time: 오전 10:55
 * To change this template use File | Settings | File Templates.
 */
public enum JobStatus {
      READY(0)
    , RUNNING(1)
    , FINISHED(2)
    ;


    private int value;
    private JobStatus(int i) {
        value = i;
    }
}
