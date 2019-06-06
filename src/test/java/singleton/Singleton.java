package singleton;

/**
 * Singleton.
 * ThreadSafe.
 */
public class Singleton {

    private static volatile Singleton inst = null;

    private static int cnt = 0;

    private Singleton() {
        cnt++;
    }

    public static Singleton getInstance() {
        if (inst == null) {
            synchronized (Singleton.class) {
                if (inst == null) {
                    inst = new Singleton();
                }
            }
        }
        return inst;
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

        Singleton singleton = (Singleton) o;

        if (cnt != singleton.cnt) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return cnt;
    }
}
