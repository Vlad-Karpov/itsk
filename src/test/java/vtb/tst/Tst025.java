package vtb.tst;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Что будет выведено в консоль?
 *
 *
 *
 */
public class Tst025 {

    @Test
    public void tst01() {
        List<Integer> list = Arrays.asList(new String[] {"1", "2", "2", "4", "9", "1", "0"});
        Set<String> set = new HashSet<>(list);
    }

}
