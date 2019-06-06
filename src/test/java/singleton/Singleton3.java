package singleton;

/**
 * Singleton3.
 * ThreadSafe!
 */
public class Singleton3 {

    private static class Singleton3Inner {
        private static Singleton3 instance = new Singleton3();
    }

    private Singleton3() {
    }

    public static Singleton3 getInstance() {
        return Singleton3Inner.instance;
    }

}
