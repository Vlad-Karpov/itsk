package dochebank;

import java.util.concurrent.Exchanger;

public class PingPongExchanger extends Thread {

    private String pingPongStr;
    private Exchanger<Boolean> exch;

    public PingPongExchanger(String thePingPong, Exchanger<Boolean> exch) {
        pingPongStr = thePingPong;
        this.exch = exch;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            System.out.print(pingPongStr);
            try {
                exch.exchange(true);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
