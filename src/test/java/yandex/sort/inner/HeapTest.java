package yandex.sort.inner;

import org.junit.Test;
import yandex.sort.SortContext;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HeapTest extends SortGeneralTest {

    @Test
    public void test1() {
        SortContext sc = new SortContext();
        List<Long> list = new ArrayList<Long>();
        list.add(1L);
        list.add(2L);
        list.add(3L);
        list.add(4L);
        list.add(5L);
        list.add(6L);
        list.add(7L);
        list.add(8L);
        Heap.heapSort(list, sc);
        Heap.heapPrint(list, l -> {
            System.out.print(l + ", ");
        });
        System.out.println("");
        list.forEach(e -> System.out.print(e + ", "));
        System.out.println("");
    }

    @Test
    public void test2() {
        SortContext sc = new SortContext();
        List<Long> list = new ArrayList<Long>();
        Heap.heapSort(list, sc);
        checkList(list);
        list.add(10L);
        Heap.heapSort(list, sc);
        checkList(list);
        list.add(20L);
        Heap.heapSort(list, sc);
        checkList(list);
        list.add(15L);
        Heap.heapSort(list, sc);
        checkList(list);

        list.clear();
        Long rnd;
        for (int i = 0; i < 10000000; i++) {
            rnd = Math.round(Math.random() * 100000L);
            list.add(rnd);
        }

        Calendar cBegin = Calendar.getInstance();
        Heap.heapSort(list, sc);
        Calendar cEnd = Calendar.getInstance();
        System.out.println("time = " + (cEnd.getTimeInMillis() - cBegin.getTimeInMillis()));
        System.out.println(sc);

        checkList(list);
    }

    @Test
    public void test3() {
        SortContext sc = new SortContext();
        List<Long> list = new ArrayList<Long>();
        list.add(9L);
        list.add(5L);
        list.add(8L);
        list.add(5L);
        list.add(17L);
        list.add(5L);
        list.add(6L);
        list.add(5L);
        list.add(5L);
        list.add(5L);
        list.add(4L);
        list.add(13L);
        list.add(5L);
        list.add(2L);
        list.add(5L);
        list.add(1L);
        list.add(5L);
        list.add(5L);
        list.add(5L);
        Heap.heapSort(list, sc);
        list.forEach(e -> System.out.print(e + ", "));
        checkList(list);
    }


}
