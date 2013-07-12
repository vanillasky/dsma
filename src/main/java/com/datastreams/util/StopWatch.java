package com.datastreams.util;

import java.io.PrintStream;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 6. 24
 * Time: 오후 2:19
 * To change this template use File | Settings | File Templates.
 */
public class StopWatch {

    long start;
    long end;

    public StopWatch() {
        start = 0L;
        end = 0L;
    }

    public void start() {
        start = System.currentTimeMillis();
    }

    public void stop() {
        end = System.currentTimeMillis();
    }

    public long getStartTime() {
        return start;
    }

    public long getEndTime() {
        return end;
    }

    public double getIntervalInSec() {
        return (double)getInterval() / 1000D;
    }

    public long getInterval() {
        return ( start < end ) ? end - start : 0L;
    }

    public void showMessage(PrintStream stream, String message) {
        stream.println(new StringBuilder(message).append(": ").append(getIntervalInSec()).append(" seconds").toString());
    }

    public void showMessage(String message) {
        showMessage(System.out, message);
    }

}
