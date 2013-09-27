package kr.co.datastreams.dsma.util.cache;

import java.util.concurrent.*;

/**
 * Implementation of memoization.
 *
 * User: shkim
 * Date: 13. 9. 23
 * Time: 오후 4:15
 *
 */
public class Memoizer<A, V> implements Computable<A, V> {

    private final ConcurrentMap<A, Future<V>> cache = new ConcurrentHashMap<A, Future<V>>();
    private final Computable<A, V> computable;


    public Memoizer(Computable<A, V> computable) {
        this.computable = computable;
    }

    /**
     * If the Future object which is associated with the specified key(arg) is not already exists in the cache,
     * creates a FutureTask object and put it into the cache(Map). Then runs the FutureTask with a Callable argument
     * which was passed when creating Memoizer object.
     * FutureTask returns the result of the Computable.compute() method.
     *
     * @param arg
     * @return
     * @throws InterruptedException
     */
    @Override
    public V compute(final A arg) throws InterruptedException {
        while (true) {
            Future<V> f = cache.get(arg);
            if (f == null) {
                Callable<V> callable = new Callable<V>() {
                    @Override
                    public V call() throws Exception {
                        return computable.compute(arg);
                    }
                };

                FutureTask<V> ft = new FutureTask<V>(callable);
                f = cache.putIfAbsent(arg, ft);
                if (f == null) {
                    f = ft;
                    ft.run();
                }
            }

            try {
                return f.get();
            } catch (CancellationException e) {
                cache.remove(arg, f);
            } catch (ExecutionException e) {
                e.printStackTrace();
                launderThrowable(e);
            }
        }
    }

    public RuntimeException launderThrowable(Throwable t) {
        if (t instanceof RuntimeException) {
            return (RuntimeException) t;
        } else if (t instanceof Error) {
            throw (Error)t;
        } else {
            throw new IllegalStateException("Exception", t);
        }
    }

}
