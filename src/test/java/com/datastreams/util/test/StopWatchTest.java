package com.datastreams.util.test;


import com.datastreams.util.StopWatch;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 6. 24
 * Time: 오후 2:29
 * To change this template use File | Settings | File Templates.
 */
public class StopWatchTest {

    StopWatch timer;

    @Before
    public void setUp() throws Exception {
        timer = new StopWatch();
    }


    @Test
    public void testGetIntervalInSec() throws Exception {
        timer.start();
        Thread.sleep(1500);
        timer.stop();
        assertEquals("1.5", String.valueOf(timer.getIntervalInSec()));
        timer.showMessage("Elapsed");
    }

    @Test
    public void testGetIntervalInSec_under_1sec() throws Exception {
        timer.start();
        Thread.sleep(640);
        timer.stop();
        assertEquals("640", String.valueOf(timer.getInterval()));
        timer.showMessage("Elapsed");
    }
}
