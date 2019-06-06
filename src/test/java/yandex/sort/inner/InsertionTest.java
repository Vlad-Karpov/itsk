package yandex.sort.inner;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import yandex.sort.SortContext;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.*;

/**
 * Тестирование сортировки вставками.
 */
public class InsertionTest extends SortGeneralTest {

    @Test
    public void test20() {

        System.out.println("-4 >> 1: " + (-4 >> 1));
        System.out.println("-3 >> 1: " + (-3 >> 1));
        System.out.println("-2 >> 1: " + (-2 >> 1));
        System.out.println("-1 >> 1: " + (-1 >> 1));
        System.out.println("0 >> 1: " + (0 >> 1));
        System.out.println("1 >> 1: " + (1 >> 1));
        System.out.println("2 >> 1: " + (2 >> 1));
        System.out.println("3 >> 1: " + (3 >> 1));
        System.out.println("4 >> 1: " + (4 >> 1));

        System.out.println("-4 >>> 1: " + (-4 >>> 1));
        System.out.println("-3 >>> 1: " + (-3 >>> 1));
        System.out.println("-2 >>> 1: " + (-2 >>> 1));
        System.out.println("-1 >>> 1: " + (-1 >>> 1));
        System.out.println("0 >>> 1: " + (0 >>> 1));
        System.out.println("1 >>> 1: " + (1 >>> 1));
        System.out.println("2 >>> 1: " + (2 >>> 1));
        System.out.println("3 >>> 1: " + (3 >>> 1));
        System.out.println("4 >>> 1: " + (4 >>> 1));

    }

    @Test
    public void test123() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate((Long.SIZE / Byte.SIZE) * 200000 );
        Long rnd;
        for (int i = 0; i < 200000; i++) {
            rnd = Math.round(Math.random() * 10000L);
            buffer.putLong(rnd);
        }
        File filePath = new File("test/tmp");
        if (filePath.exists())
            FileUtils.deleteDirectory(filePath);
        Assert.assertTrue(filePath.mkdir());
        File file = new File(filePath, "rnd.bin");
        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(buffer.array());
        fos.flush();
        fos.close();

    }

    public List<Long> getRndListFromFile() throws IOException {
        File filePath = new File("test/tmp");
        File file = new File(filePath, "rnd.bin");
        FileInputStream fos = new FileInputStream(file);
        ByteBuffer buffer = ByteBuffer.allocate((Long.SIZE / Byte.SIZE) * 200000 );
        fos.read(buffer.array());
        fos.close();
        List<Long> result = new ArrayList<Long>();
        for (int i = 0; i < 200000; i++) {
            result.add(buffer.getLong());
        }
        return result;
    }

        @Test
    public void test1() throws IOException {

        SortContext sc = new SortContext();

        List<Long> list = new ArrayList<Long>();
        Insertion.insertionSort(list, sc);
        checkList(list);
        list.add(10L);
        Insertion.insertionSort(list, sc);
        checkList(list);
        list.add(20L);
        Insertion.insertionSort(list, sc);
        checkList(list);
        list.add(15L);
        Insertion.insertionSort(list, sc);
        checkList(list);

        list.clear();
        list = getRndListFromFile();
        List<Long> binaryInsertionList = new ArrayList<Long>();
        List<Long> binaryInsertionShellList = new ArrayList<Long>();
        List<Long> shellList = new ArrayList<Long>();
        List<Long> shellList1 = new ArrayList<Long>();
        List<Long> collectionsSortList = new ArrayList<Long>();

        Long rnd;
        for (int i = 0; i < 200000; i++) {
            rnd = list.get(i);
            binaryInsertionList.add(rnd);
            binaryInsertionShellList.add(rnd);
            shellList.add(rnd);
            shellList1.add(rnd);
            collectionsSortList.add(rnd);
        }

        Calendar cBegin = Calendar.getInstance();
        Insertion.insertionSort(list, sc);
        Calendar cEnd = Calendar.getInstance();
        System.out.println("insertionSort time = " + (cEnd.getTimeInMillis() - cBegin.getTimeInMillis()));
        checkList(list);
        System.out.println(sc);

        cBegin = Calendar.getInstance();
        Insertion.binaryInsertionSort(binaryInsertionList, sc);
        cEnd = Calendar.getInstance();
        System.out.println("binaryInsertionSort time = " + (cEnd.getTimeInMillis() - cBegin.getTimeInMillis()));
        checkList(binaryInsertionList);
        System.out.println(sc);

        //Integer[] shellGaps = {256000, 128000, 32000, 16000, 8000, 3750, 1750, 701, 301, 132, 57, 23, 10, 4, 1};

        Integer[] shellGaps = {122710, 52448, 22417, 9581, 4095, 1750, 701, 301, 132, 57, 23, 10, 4, 1};

        cBegin = Calendar.getInstance();
        Insertion.binaryInsertionShellSort(binaryInsertionShellList, shellGaps, sc);
        cEnd = Calendar.getInstance();
        System.out.println("binaryInsertionShellSort time = " + (cEnd.getTimeInMillis() - cBegin.getTimeInMillis()));
        checkList(binaryInsertionShellList);
        System.out.println(sc);

        cBegin = Calendar.getInstance();
        Insertion.shellSort(shellList, shellGaps, sc);
        cEnd = Calendar.getInstance();
        System.out.println("shellSort time = " + (cEnd.getTimeInMillis() - cBegin.getTimeInMillis()));
        checkList(shellList);
        System.out.println(sc);

        cBegin = Calendar.getInstance();
        Insertion.shellSort(shellList1, sc);
        cEnd = Calendar.getInstance();
        System.out.println("shellSort1 time = " + (cEnd.getTimeInMillis() - cBegin.getTimeInMillis()));
        checkList(shellList1);
        System.out.println(sc);

        cBegin = Calendar.getInstance();
        Collections.sort(collectionsSortList);
        cEnd = Calendar.getInstance();
        System.out.println("Collections.sort time = " + (cEnd.getTimeInMillis() - cBegin.getTimeInMillis()));
        checkList(collectionsSortList);

    }

    @Test
    public void test2() {

        SortContext sc = new SortContext();

        List<Long> shellList = new ArrayList<Long>();
        Long rnd;
        for (int i = 0; i < 100000; i++) {
            rnd = Math.round(Math.random() * 10000L);
            shellList.add(rnd);
        }



        //Integer[] shellGaps = {1750, 701, 301, 132, 57, 23, 10, 4, 1};

        Integer[] shellGaps = {64000, 32000, 16000, 8000, 3750, 1750, 701, 301, 132, 57, 23, 10, 4, 1};


        /*
        fibonachi
        0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597, 2584, 4181, 6765, 10946, 17711, 28657,
        46368, 75025, 121393, 196418, 317811, 514229, 832040, 1346269, 2178309, 3524578, 5702887, 9227465, 14930352,
        24157817, 39088169 ...
        */
        //Integer[] shellGaps = {75025, 46368, 28657, 17711, 10946, 6765, 4181, 2584, 1597, 987, 610, 377, 233, 144, 89, 55, 34, 21, 13, 8, 5, 3, 2, 1};

        Calendar cBegin = Calendar.getInstance();
        Insertion.shellSort(shellList, shellGaps, sc);
        Calendar cEnd = Calendar.getInstance();
        System.out.println("shellSort time = " + (cEnd.getTimeInMillis() - cBegin.getTimeInMillis()));
        checkList(shellList);
        System.out.println(sc);
    }


/*
    @Test
    public void test2() {
        List<Long> list = new LinkedList<Long>();
        Insertion.insertionSort(list);
        checkList(list);
        list.add(10L);
        Insertion.insertionSort(list);
        checkList(list);
        list.add(20L);
        Insertion.insertionSort(list);
        checkList(list);
        list.add(15L);
        Insertion.insertionSort(list);
        checkList(list);

        list.clear();
        Long rnd;
        for (int i = 0; i < 100000; i++) {
            rnd = Math.round(Math.random() * 10000L);
            list.add(rnd);
        }

        Calendar cBegin = Calendar.getInstance();
        Insertion.insertionSort(list);
        Calendar cEnd = Calendar.getInstance();
        System.out.println("time = " + (cEnd.getTimeInMillis() - cBegin.getTimeInMillis()));

        checkList(list);

    }
*/


    @Test
    public void qqTest() {
        //Integer[] shellGaps = {64000, 32000, 16000, 8000, 3750, 1750, 701, 301, 132, 57, 23, 10, 4, 1};
        Integer[] shellGaps = {122710, 52448, 22417, 9581, 4095, 1750, 701, 301, 132, 57, 23, 10, 4, 1};
        double dLog1, dLog2 = 0.0d, delta;
        int i = shellGaps.length - 1;
        Integer gap;
        while (i >= 0) {
            gap = shellGaps[i];
            while (true) {
                dLog1 = Math.log(gap);
                delta = dLog2 - dLog1;
                if (i > 5) {
                    dLog2 = dLog1;
                    break;
                }
                if (delta < -0.85) {
                    dLog2 = dLog1;
                    break;
                } else {
                    gap = gap + 1;
                }
            }
            System.out.println("i = " + i + " , gap = " + gap + " , log(gap) = " + dLog2 + " , delta = " + delta);
            i--;
        }
    }


}
