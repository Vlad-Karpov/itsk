package dochebank;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;

public class PingPongAtomic extends Thread {

    final private String pingPongStr;
    final private AtomicBoolean check;
    final private Predicate<AtomicBoolean> p;

    public PingPongAtomic(String thePingPong, AtomicBoolean check, Predicate<AtomicBoolean> p) {
        pingPongStr = thePingPong;
        this.check = check;
        this.p = p;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            synchronized (check) {
                if (p.test(check)) {
                    System.out.print(pingPongStr);
                }
            }
        }
    }

}
