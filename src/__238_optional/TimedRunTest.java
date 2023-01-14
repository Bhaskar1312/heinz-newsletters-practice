package __238_optional;

import java.math.BigInteger;
import java.util.Optional;
import java.util.concurrent.*;

public class TimedRunTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newCachedThreadPool();
        TimedRun tr = new TimedRun(pool);
        Optional<BigInteger> prime = tr.execute(() -> {
            BigInteger val = BigInteger.ONE;
            while (!Thread.currentThread().isInterrupted()) {
                val = val.nextProbablePrime();
                if(val.toString().length() > 3) { // java-17 -> >4 optional empty
                    return val;
                }
            }
            return null; // no one will know as is interrupted
        }, 1, TimeUnit.SECONDS);

        System.out.println("prime: " + prime);

        if(prime.isPresent()) {
            System.out.println("Prime found: "+ prime.get());
        } else {
            System.out.println("prime not found");
        }

        System.out.println(prime.map(val -> "Stuart Prime: "+ val).orElseGet(()-> "Stuart Prime not found"));
        // .orElse("Stuart Prime not found"));

        pool.shutdown();
    }
}
