package vtb.tst;

import org.junit.Test;

/**
 * Что будет выведено в консоль?
 *      My cat likes milk!!!
 *      My cat likes
 *  *   My cat likes!!!
 *      My cat likes milk
 */
public class Tst014 {

    @Test
    public void tst() {
        String str = "My cat likes";
        update(str, "milk");
        str += "!!!";
        System.out.println(str);
    }

    public void update(String string, String suffix) {
        string += " " + suffix;
    }
}
