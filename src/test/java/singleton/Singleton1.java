package singleton;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Singleton1.
 * Not threadSafe!
 */
public class Singleton1 {

    private static AtomicReference<Singleton1> inst = new AtomicReference();

    private static int cnt = 0;

    private Singleton1() {
        cnt++;
    }

    public static Singleton1 getInstance() {
        for (; ; ) {
            Singleton1 currentInst = inst.get();
            if (currentInst == null) {
                if (inst.compareAndSet(currentInst, new Singleton1())) {
                    break;
                }
            } else {
                break;
            }
        }
        return inst.get();
    }

    @Override
    public String toString() {
        return "Singleton{" +
                "cnt=" + cnt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Singleton)) return false;

        Singleton1 singleton = (Singleton1) o;

        if (cnt != singleton.cnt) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return cnt;
    }


}
