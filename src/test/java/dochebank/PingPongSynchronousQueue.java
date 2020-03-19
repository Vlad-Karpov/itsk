package dochebank;

import java.util.concurrent.SynchronousQueue;

public class PingPongSynchronousQueue extends Thread {

    private String pingPongStr;
    private SynchronousQueue<Boolean> queue;

    public PingPongSynchronousQueue(String thePingPong, SynchronousQueue<Boolean> queue) {
        pingPongStr = thePingPong;
        this.queue = queue;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                queue.take();
                System.out.print(pingPongStr);
                queue.put(true);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
