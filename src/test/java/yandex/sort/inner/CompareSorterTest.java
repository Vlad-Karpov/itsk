package yandex.sort.inner;

import org.junit.Test;
import yandex.sort.SortContext;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 *
 */
public class CompareSorterTest extends SortGeneralTest {

    @Test
    public void test4() {
        SortContext sc = new SortContext();
        //List<Long> listBubble = new ArrayList<Long>();
        //List<Long> listInsertion = new ArrayList<Long>();
        List<Long> listComb = new ArrayList<Long>();
        List<Long> listShell = new ArrayList<Long>();
        List<Long> shellList1 = new ArrayList<Long>();

        Long rnd;
        for (int i = 0; i < 24000000; i++) {
            rnd = Math.round(Math.random() * 100000L);
            //listBubble.add(rnd);
            //listInsertion.add(rnd);
            listComb.add(rnd);
            listShell.add(rnd);
            shellList1.add(rnd);
        }

        Calendar cBegin;
        Calendar cEnd;

        /*
        cBegin = Calendar.getInstance();
        Bubble.bubbleSort(listBubble);
        cEnd = Calendar.getInstance();
        System.out.println("bubbleSort time = " + (cEnd.getTimeInMillis() - cBegin.getTimeInMillis()));
        checkList(listBubble);

        cBegin = Calendar.getInstance();
        Insertion.insertionSort(listInsertion);
        cEnd = Calendar.getInstance();
        System.out.println("insertionSort time = " + (cEnd.getTimeInMillis() - cBegin.getTimeInMillis()));
        checkList(listInsertion);
        */

        //Integer[] shellGaps = {1750, 701, 301, 132, 57, 23, 10, 4, 1};
        //Integer[] shellGaps = {64000, 32000, 16000, 8000, 3750, 1750, 701, 301, 132, 57, 23, 10, 4, 1};
        Integer[] shellGaps = {18284000, 9192000, 4096000, 2048000, 1024000, 512000, 256000, 128000, 32000, 16000, 8000, 3750, 1750, 701, 301, 132, 57, 23, 10, 4, 1};
        cBegin = Calendar.getInstance();
        Insertion.shellSort(listShell, shellGaps, sc);
        cEnd = Calendar.getInstance();
        System.out.println("shellSort time = " + (cEnd.getTimeInMillis() - cBegin.getTimeInMillis()));
        checkList(listShell);
        System.out.println(sc);

        cBegin = Calendar.getInstance();
        Insertion.shellSort(shellList1, sc);
        Collections.sort(shellList1);
        cEnd = Calendar.getInstance();
        System.out.println("Collections.sort time = " + (cEnd.getTimeInMillis() - cBegin.getTimeInMillis()));
        checkList(shellList1);
        System.out.println(sc);


        cBegin = Calendar.getInstance();
        Bubble.combSort(listComb, sc);
        cEnd = Calendar.getInstance();
        System.out.println("combSort time = " + (cEnd.getTimeInMillis() - cBegin.getTimeInMillis()));
        checkList(listComb);
        System.out.println(sc);


    }

}
