package singleton;

import org.junit.Assert;
import org.junit.Test;

/**
 * SingletonTest.
 */
public class SingletonTest {

    @Test
    public void tst1() {
        Singleton s1 = Singleton.getInstance();
        Singleton s2 = Singleton.getInstance();
        Assert.assertEquals(s1, s2);
        Singleton1 s3 = Singleton1.getInstance();
        Singleton1 s4 = Singleton1.getInstance();
        Assert.assertEquals(s3, s4);
        Singleton3 s5 = Singleton3.getInstance();
        Singleton3 s6 = Singleton3.getInstance();
        Assert.assertEquals(s5, s6);
        Singleton4 s7 = Singleton4.getInstance();
        Singleton4 s8 = Singleton4.getInstance();
        Assert.assertEquals(s7, s8);
    }

}

