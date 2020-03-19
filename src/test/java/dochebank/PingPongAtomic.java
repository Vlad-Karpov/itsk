package dochebank;

import java.util.concurrent.atomic.AtomicBoolean;

public class PingPongAtomic extends Thread {

    private String pingPongStr;
    private AtomicBoolean check;
    boolean qq;

    public PingPongAtomic(String thePingPong, AtomicBoolean check, boolean qq) {
        pingPongStr = thePingPong;
        this.check = check;
        this.qq = qq;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            if (check.get() == qq) {
                System.out.print(pingPongStr);
                while (check.compareAndSet(qq, !qq));
            }
        }
    }

}
