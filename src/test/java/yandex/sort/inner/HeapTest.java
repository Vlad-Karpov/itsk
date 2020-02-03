package yandex.sort.inner;

import org.junit.Test;
import yandex.sort.SortContext;

import java.util.ArrayList;
import java.util.List;

public class HeapTest extends SortGeneralTest {

    @Test
    public void test1() {
        SortContext sc = new SortContext();
        List<Long> list = new ArrayList<Long>();
        list.add(7L);
        list.add(3L);
        list.add(6L);
        list.add(1L);
        list.add(2L);
        list.add(4L);
        list.add(5L);
        Heap.heapSort(list, sc);
    }

}
