package dochebank;

import org.junit.Test;

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
        //Thread.sleep(3L * 60L * 1000L);
    }

    @Test
    public void pingPongTest1() throws InterruptedException {
        PingPong1 pingPongPing = new PingPong1("ping ");
        PingPong1 pingPongPong = new PingPong1("pong, ");
        pingPongPing.start();
        pingPongPong.start();
        //Thread.sleep(2L * 1000L);
    }

}
