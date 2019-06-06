package yandex.sort.inner;

import org.junit.Assert;

import java.util.List;

/**
 * Base class.
 */
public class SortGeneralTest {

    protected <T extends Comparable<? super T>> void checkList(List<T> list) {
        int i = 0;
        T prev = null;
        for (T current : list) {
            if (prev != null) {
                if (prev.compareTo(current) > 0) {
                    Assert.fail("Out of order: pos = " + i + ", prev = " + prev + " and current = " + current);
                }
            }
            prev = current;
            i++;
        }
    }


}
