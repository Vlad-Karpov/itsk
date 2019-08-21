package dochebank;

/**
 * Вывод ping pong, и не как иначе....
 */
public class PingPong extends Thread {

    private String pingPongStr;

    public PingPong(String thePingPong) {
        pingPongStr = thePingPong;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            synchronized (PingPong.class) {
                System.out.print(pingPongStr);
                PingPong.class.notify();
                yield();
                try {
                    PingPong.class.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
