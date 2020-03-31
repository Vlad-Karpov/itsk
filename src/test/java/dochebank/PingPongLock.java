package dochebank;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class PingPongLock extends Thread {

    private String pingPongStr;
    private Lock lock;
    private Condition cnd;

    public PingPongLock(String thePingPong, Lock theLock, Condition cnd) {
        pingPongStr = thePingPong;
        this.lock = theLock;
        this.cnd = cnd;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            lock.lock();
            System.out.print(pingPongStr);
            cnd.signal();
            try {
                cnd.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.unlock();
        }
    }

}
