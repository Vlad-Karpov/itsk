package dochebank;

public class PingPong1 extends Thread {

    private String pingPongStr;

    public PingPong1(String thePingPong) {
        pingPongStr = thePingPong;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            synchronized (PingPong1.class) {
                System.out.print(pingPongStr);
                yield();
                PingPong1.class.notify();
                try {
                    PingPong1.class.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}



