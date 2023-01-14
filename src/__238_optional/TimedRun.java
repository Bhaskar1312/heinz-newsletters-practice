package __238_optional;

import java.util.Optional;
import java.util.concurrent.*;

// http://www.javaspecialists.eu/archive/Issue238.html
// https://vimeo.com/165927834
public class TimedRun {

    private final ExecutorService pool;

    public TimedRun(ExecutorService pool) {
        this.pool = pool;
    }

    public <T> Optional<T> execute(Callable<T> job, long timeout, TimeUnit unit)
            throws InterruptedException, ExecutionException {
        Future<T>  future = pool.submit(job);
        try {
            return Optional.of(future.get(timeout, unit));
        } catch (InterruptedException e) {
            future.cancel(true);
            throw e;
        } catch (TimeoutException e) {
            future.cancel(true);
        }
        return Optional.empty();
    }


    public static void main(String[] args) {

    }
}
