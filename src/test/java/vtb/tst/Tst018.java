package vtb.tst;

import org.junit.Test;

/**
 * Какие вызовы метода будут скомпилированны без ошибок?
 *
 *
 *      *       Class<Integer> c2 = Integer.class;
 *      *       Class<Integer> c3 = (Class<Integer>) new Integer(0).getClass();
 *      *       Class<Integer> c4 = int.class;
 *
 */
public class Tst018 {

    @Test
    public void tst1() {
        Class<Integer> c2 = Integer.class;
        System.out.println(c2);
    }
    @Test
    public void tst2() {
        Class<Integer> c2 = Integer.class;
        System.out.println(c2);
    }
    @Test
    public void tst3() {
        Class<Integer> c3 = (Class<Integer>) new Integer(0).getClass();
        System.out.println(c3);
    }
    @Test
    public void tst4() {
        Class<Integer> c4 = int.class;
        System.out.println(c4);
    }

}
