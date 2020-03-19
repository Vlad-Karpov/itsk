package dochebank;

import org.junit.Test;

import java.util.concurrent.Exchanger;
import java.util.concurrent.Semaphore;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Вывод ping pong, и не как иначе....
 */
public class PingPongTest {

    @Test
    public void pingPongTest() throws InterruptedException {
        PingPong pingPongPing = new PingPong("ping ");
        PingPong pingPongPong = new PingPong("pong, ");
        pingPongPing.start();
        pingPongPong.start();
        Thread.sleep(10L * 1000L);
    }

    @Test
    public void pingPongTest1() throws InterruptedException {
        PingPong1 pingPongPing = new PingPong1("ping ");
        PingPong1 pingPongPong = new PingPong1("pong, ");
        pingPongPing.start();
        pingPongPong.start();
        Thread.sleep(10L * 1000L);
    }

    @Test
    public void pingPongTest2() throws InterruptedException {
        AtomicBoolean check = new AtomicBoolean();
        PingPongAtomic p1 = new PingPongAtomic("ping ", check, true);
        PingPongAtomic p2 = new PingPongAtomic("pong, ", check, false);
        p1.start();
        p2.start();
        Thread.sleep(10L);
    }

    @Test
    public void pingPongTest3() throws InterruptedException {
        Exchanger<Boolean> exch = new Exchanger<Boolean>();
        PingPongExchanger p1 = new PingPongExchanger("ping ", exch);
        PingPongExchanger p2 = new PingPongExchanger("pong, ", exch);
        p1.start();
        p2.start();
        Thread.sleep(10L);
    }


    @Test
    public void pingPongTest4() throws InterruptedException {
        SynchronousQueue<Boolean> exch = new SynchronousQueue<Boolean>();
        PingPongSynchronousQueue p1 = new PingPongSynchronousQueue("ping ", exch);
        PingPongSynchronousQueue p2 = new PingPongSynchronousQueue("pong, ", exch);
        p1.start();
        p2.start();
        exch.put(true);
        Thread.sleep(10L);
    }

    @Test
    public void pingPongTest5() throws InterruptedException {
        Semaphore check = new Semaphore(1);
        PingPongSemaphore p1 = new PingPongSemaphore("ping ", check);
        PingPongSemaphore p2 = new PingPongSemaphore("pong, ", check);
        p1.start();
        p2.start();
        Thread.sleep(10L);
    }

}
