package yandex.fibonacci;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Напишите lock-free реализацию класса с методом BigInteger next(), который возвращает элементы последовательности
 * Фибоначчи. Код должен корректно работать в многопоточной среде.
 */
public class BigIntFibonacci {

    private FibonacciAtomicWripper f1 = new FibonacciAtomicWripper();
    private FibonacciSynchronized f2 = new FibonacciSynchronized();

    @Test
    public void test1() {
        for (int i = 0; i < 10000; i++) {
            new FibonacciThread().start();
        }

    }

    class FibonacciThread extends Thread {

        @Override
        public void run() {
            for (int i = 0; i < 100000; i++) {
                synchronized (f1) {
                    Assert.assertEquals("!", f1.next(), f2.next());
                }
            }
        }

    }


    class Fibonacci {

        private BigInteger lo;
        private BigInteger hi;

        public Fibonacci() {
            hi = new BigInteger("1");
            lo = new BigInteger("1");
        }

        public Fibonacci(Fibonacci thePrev) {
            hi = thePrev.hi;
            lo = thePrev.lo;
        }

        public Fibonacci next() {
            Fibonacci result = new Fibonacci(this);
            result.hi = result.lo.add(result.hi);
            result.lo = result.hi.subtract(result.lo);
            return result;
        }
    }

    class FibonacciAtomicWripper {

        private AtomicReference<Fibonacci> fref = new AtomicReference(new Fibonacci());

        public BigInteger next() {
            for (; ; ) {
                Fibonacci current = fref.get();
                Fibonacci next = current.next();
                if (fref.compareAndSet(current, next)) {
                    return next.lo;
                }
            }
        }
    }

    class FibonacciSynchronized {

        private BigInteger lo;
        private BigInteger hi;

        public FibonacciSynchronized() {
            hi = new BigInteger("1");
            lo = new BigInteger("1");
        }

        public synchronized BigInteger next() {
            hi = lo.add(hi);
            lo = hi.subtract(lo);
            return lo;
        }
    }

}
