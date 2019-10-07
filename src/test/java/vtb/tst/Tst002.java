package vtb.tst;

import org.junit.Test;

import java.util.Arrays;

/**
 * 18) 2-е песочных чаов на 7 и 11 минут, надо отмерить 15
 *     (отмеряем 7, потом 2 раза по 4)
 * 15?
 *          7   11
 *   7      0   4
 *   11     4   0
 *   15
 */
public class Tst002 {
    /**
     * Вот это выдаст
     * [10.2, 56.0, 54.7]
     */
    @Test
    public void tst() {
        double[] numbers = {10.2, 56.0, 54.7};
        Arrays.stream(numbers).map(n -> n + 1).toArray();
        System.out.println(Arrays.toString(numbers));
    }
}
