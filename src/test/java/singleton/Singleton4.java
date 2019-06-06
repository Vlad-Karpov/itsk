package singleton;

public class Singleton4 {

    private static final Singleton4 inst = new Singleton4();

    private Singleton4() {
    }

    public static Singleton4 getInstance() {
        return inst;
    }

}
