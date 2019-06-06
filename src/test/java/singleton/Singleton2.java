package singleton;

/**
 * Created by KARPOVVV on 23.08.17.
 */
public class Singleton2 {

    private static volatile Singleton2 inst = new Singleton2();

    private Singleton2() {
    }

    /**
     * static factory method
     * @return
     */
    public static Singleton2 getInstance() {
        return inst;
    }

}
