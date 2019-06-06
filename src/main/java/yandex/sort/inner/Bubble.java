package yandex.sort.inner;

import yandex.sort.SortContextInterface;

import java.util.List;

/**
 * Пузырьковая сортировака.
 */
public class Bubble {

    public static <T extends Comparable<? super T>> void bubbleSort(List<T> list, SortContextInterface sortContextObject) {
        if (sortContextObject != null ) sortContextObject.clear();
        bubbleSortCore(list, sortContextObject);
    }

    /**
     * todo: shakerSort
     * @param list
     * @param <T>
     */
    public static <T extends Comparable<? super T>> void shakerSort(List<T> list, SortContextInterface sortContextObject) {

    }


    public static <T extends Comparable<? super T>> void combSort(List<T> list, SortContextInterface sortContextObject) {
        if (sortContextObject != null ) sortContextObject.clear();
        int s = list.size();
        int gap = s - 1;
        float shrink = 1.2473309f;
        while (gap >= 1) {
            for (int i = 0; i + gap < list.size(); ++i) {
                T theOne = list.get(i);
                T theNext = list.get(i + gap);
                if (sortContextObject != null ) sortContextObject.incCmpCount();
                if (theOne.compareTo(theNext) > 0) {
                    if (sortContextObject != null ) sortContextObject.incSwapCount();
                    list.set(i, theNext);
                    list.set(i + gap, theOne);
                }
            }
            gap /= shrink;
        }
        bubbleSortCore(list, sortContextObject);
    }

    private static <T extends Comparable<? super T>> void bubbleSortCore(List<T> list, SortContextInterface sortContextObject) {
        int s = list.size();
        for (int i = 0; i < s - 1; i++) {
            int cmpCnt = 0;
            for (int j = 0; j < s - i - 1; j++) {
                T theOne = list.get(j);
                T theNext = list.get(j + 1);
                if (sortContextObject != null ) sortContextObject.incCmpCount();
                if (theOne.compareTo(theNext) > 0) {
                    if (sortContextObject != null ) sortContextObject.incSwapCount();
                    list.set(j, theNext);
                    list.set(j + 1, theOne);
                    cmpCnt++;
                }
            }
            if (cmpCnt == 0) {
                return;
            }
        }
    }

}
