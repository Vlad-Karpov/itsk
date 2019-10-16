package vtb.tst;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Что будет выведено в консоль?
 *
 * <pre>{@code
 *      List<String> list = Arrays.asList(new String[] {"1", "2", "2", "4", "9", "1", "0"});
 *      Set<String> set = new HashSet<>(list);
 *      System.out.println(list.size());
 *      System.out.println(set.size());
 * }</pre>
 *
 * <ul>
 *  <li>7 7</li>
 *  <li>* 7 5</li>
 *  <li>5 7</li>
 *  <li>5 5</li>
 * </ul>
 */
public class Tst025 {

    @Test
    public void tst01() {
        List<String> list = Arrays.asList(new String[]{"1", "2", "2", "4", "9", "1", "0"});
        Set<String> set = new HashSet<>(list);
        System.out.println(list.size());
        System.out.println(set.size());
    }

}
