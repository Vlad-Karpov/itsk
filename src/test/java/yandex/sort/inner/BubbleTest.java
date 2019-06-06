package yandex.sort.inner;

import org.junit.Test;
import yandex.sort.SortContext;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Тестирование пузырьковой сортировки.
 */
public class BubbleTest extends SortGeneralTest {

    @Test
    public void test1() {
        SortContext sc = new SortContext();
        List<Long> list = new ArrayList<Long>();
        Bubble.bubbleSort(list, sc);
        checkList(list);
        list.add(10L);
        Bubble.bubbleSort(list, sc);
        checkList(list);
        list.add(20L);
        Bubble.bubbleSort(list, sc);
        checkList(list);
        list.add(15L);
        Bubble.bubbleSort(list, sc);
        checkList(list);

        list.clear();
        Long rnd;
        for (int i = 0; i < 100000; i++) {
            rnd = Math.round(Math.random() * 10000L);
            list.add(rnd);
        }

        Calendar cBegin = Calendar.getInstance();
        Bubble.bubbleSort(list, sc);
        Calendar cEnd = Calendar.getInstance();
        System.out.println("time = " + (cEnd.getTimeInMillis() - cBegin.getTimeInMillis()));
        System.out.println(sc);

        checkList(list);

    }

    @Test
    public void test3() {
        SortContext sc = new SortContext();
        List<Long> list = new ArrayList<Long>();
        Bubble.bubbleSort(list, sc);
        checkList(list);
        list.add(10L);
        Bubble.bubbleSort(list, sc);
        checkList(list);
        list.add(20L);
        Bubble.bubbleSort(list, sc);
        checkList(list);
        list.add(15L);
        Bubble.bubbleSort(list, sc);
        checkList(list);

        list.clear();
        Long rnd;
        for (int i = 0; i < 100000; i++) {
            rnd = Math.round(Math.random() * 10000L);
            list.add(rnd);
        }

        Calendar cBegin = Calendar.getInstance();
        Bubble.combSort(list, sc);
        Calendar cEnd = Calendar.getInstance();
        System.out.println("time = " + (cEnd.getTimeInMillis() - cBegin.getTimeInMillis()));
        System.out.println(sc);

        checkList(list);

    }

    @Test
    public void test4() {
        SortContext sc = new SortContext();
        List<Long> listBubble = new ArrayList<Long>();
        List<Long> listComb = new ArrayList<Long>();

        Long rnd;
        for (int i = 0; i < 100000; i++) {
            rnd = Math.round(Math.random() * 10000L);
            listBubble.add(rnd);
            listComb.add(rnd);
        }

        Calendar cBegin = Calendar.getInstance();
        Bubble.bubbleSort(listBubble, sc);
        Calendar cEnd = Calendar.getInstance();
        System.out.println("bubbleSort time = " + (cEnd.getTimeInMillis() - cBegin.getTimeInMillis()));
        checkList(listBubble);
        System.out.println(sc);

        cBegin = Calendar.getInstance();
        Bubble.combSort(listComb, sc);
        cEnd = Calendar.getInstance();
        System.out.println("combSort time = " + (cEnd.getTimeInMillis() - cBegin.getTimeInMillis()));
        checkList(listComb);
        System.out.println(sc);


    }

/*
    @Test
    public void test2() {
        List<Long> list = new LinkedList<Long>();
        Bubble.bubbleSort(list);
        checkList(list);
        list.add(10L);
        Bubble.bubbleSort(list);
        checkList(list);
        list.add(20L);
        Bubble.bubbleSort(list);
        checkList(list);
        list.add(15L);
        Bubble.bubbleSort(list);
        checkList(list);

        list.clear();
        Long rnd;
        for (int i = 0; i < 100000; i++) {
            rnd = Math.round(Math.random() * 10000L);
            list.add(rnd);
        }

        Calendar cBegin = Calendar.getInstance();
        Bubble.bubbleSort(list);
        Calendar cEnd = Calendar.getInstance();
        System.out.println("time = " + (cEnd.getTimeInMillis() - cBegin.getTimeInMillis()));

        checkList(list);

    }
*/

}
