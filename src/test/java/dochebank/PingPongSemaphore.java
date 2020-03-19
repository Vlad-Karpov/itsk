package dochebank;

import java.util.concurrent.Semaphore;

public class PingPongSemaphore extends Thread {

    private String pingPongStr;
    private Semaphore check;

    public PingPongSemaphore(String thePingPong, Semaphore check) {
        pingPongStr = thePingPong;
        this.check = check;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                check.acquire();
                System.out.print(pingPongStr);
                check.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
