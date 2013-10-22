package com.datastreams.nlp.common.cache;

/**
 *
 * User: shkim
 * Date: 13. 9. 23
 * Time: 오후 4:17
 *
 */
public interface Computable<A, V> {

    /**
     * Computes an operation
     *
     * @param arg
     * @return
     * @throws InterruptedException
     */
    V compute(A arg) throws InterruptedException;
}
