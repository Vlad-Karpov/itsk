package yandex.sort.inner;

import org.junit.Assert;
import yandex.sort.SortContextInterface;

import java.util.List;

/**
 * Сотрировка вставками.
 */
public class Insertion {

    /**
     * binaryInsertionSort.
     * @param list list
     * @param <T> object of list
     */
    public static <T extends Comparable<? super T>> void binaryInsertionSort(List<T> list, SortContextInterface sortContextObject) {
        if (sortContextObject != null ) sortContextObject.clear();
        insertPositionCore(list, list.size(), 1, 0, sortContextObject);
    }

    /**
     * binaryInsertionShellSort.
     * @param list list
     * @param gapSequence
     * @param <T> object of list
     */
    public static <T extends Comparable<? super T>> void binaryInsertionShellSort(List<T> list, Integer[] gapSequence, SortContextInterface sortContextObject) {
        if (sortContextObject != null ) sortContextObject.clear();
        int s = list.size();
        for (int gap : gapSequence) {
            for (int k = 0; k < gap; k++) {
                insertPositionCore(list, s, gap, k, sortContextObject);
            }
        }

    }

    /**
     * 5 10 15 20 25 30
     * (5 + 35) / 2 = 20          (5 + 20)/2 = 12
     * @param list
     * @param low
     * @param high
     * @param gap
     * @param key
     * @param <T>
     * @return
     */
    public static <T extends Comparable<? super T>> int binarySerch(List<T> list, int low, int high, int gap, T key, int shift, SortContextInterface sortContextObject) {
        int cmp;
        int medium;
        int mediumr;
        low = (low - shift) / gap;
        high = (high - shift) / gap;
        medium = ((low + high) >> 1);
        mediumr = shift + medium * gap;
        while (low < high) {
            cmp = list.get(mediumr).compareTo(key);
            if (sortContextObject != null ) sortContextObject.incCmpCount();
            if (cmp > 0) {
                high = medium;
            } else {
                low = medium + 1;
            }
            medium = ((low + high) >> 1);
            mediumr = shift + medium * gap;
        }
        return mediumr;
    }

    private static <T extends Comparable<? super T>> void insertPositionCore(List<T> list, int s, int gap, int k, SortContextInterface sortContextObject) {
        for (int j = k + gap; j < s; j += gap) {
            T key = list.get(j);
            int medium = binarySerch(list, k, j, gap, key, k, sortContextObject);
            int i = j - gap;
            while (i >= medium) {
                list.set(i + gap, list.get(i));
                i = i - gap;
                if (sortContextObject != null ) sortContextObject.incSwapCount();
            }
            list.set(i + gap, key);
            if (sortContextObject != null ) sortContextObject.incSwapCount();
        }
    }


    /**
     * Classic.
     * @param list
     * @param <T>
     */
    public static <T extends Comparable<? super T>> void insertionSort(List<T> list, SortContextInterface sortContextObject) {
        if (sortContextObject != null ) sortContextObject.clear();
        insertSortCore(list, list.size(), 1, 0, sortContextObject);
    }

    /**
     * Shell sort.
     * @param list
     * @param gapSequence
     * @param <T>
     */
    public static <T extends Comparable<? super T>> void shellSort(List<T> list, Integer[] gapSequence, SortContextInterface sortContextObject) {
        if (sortContextObject != null ) sortContextObject.clear();
        int s = list.size();
        for (int gap : gapSequence) {
            insertionShellSortCore(list, s, gap, sortContextObject);
        }
    }

    /**
     * Shell sort with shrink.
     * @param list
     * @param <T>
     */
    public static <T extends Comparable<? super T>> void shellSort(List<T> list, SortContextInterface sortContextObject) {
        if (sortContextObject != null ) sortContextObject.clear();
        int s = list.size();
        int gap = s - 1;
        float shrink = 1.2473309f;
        while (gap >= 1) {
            insertionShellSortCore(list, s, gap, sortContextObject);
            gap /= shrink;
        }
        insertSortCore(list, s, 1, 0, sortContextObject);
    }

    private static <T extends Comparable<? super T>> void insertionShellSortCore(List<T> list, int s, int gap, SortContextInterface sortContextObject) {
        for (int k = 0; k < gap; k++) {
            insertSortCore(list, s, gap, k, sortContextObject);
        }
    }

    private static <T extends Comparable<? super T>> void insertSortCore(List<T> list, int s, int gap, int k, SortContextInterface sortContextObject) {
        for (int j = k + gap; j < s; j += gap) {
            T key = list.get(j);
            int i = j - gap;
            while (i >= 0 && list.get(i).compareTo(key) > 0) {
                if (sortContextObject != null ) {
                    sortContextObject.incCmpCount();
                    sortContextObject.incSwapCount();
                }
                list.set(i + gap, list.get(i));
                i = i - gap;
            }
            list.set(i + gap, key);
            sortContextObject.incSwapCount();
        }
    }

}
