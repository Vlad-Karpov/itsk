package yandex.sort.inner;

import yandex.sort.SortContextInterface;

import java.util.ArrayList;
import java.util.List;

public class Merge {

    public static <T extends Comparable<? super T>> List<T> mergeSort(List<T> list, SortContextInterface sortContextObject) {
        if (sortContextObject != null ) sortContextObject.clear();
        return mergeSortCore(list, sortContextObject);
    }

    private static <T extends Comparable<? super T>> List<T> mergeSortCore(List<T> list, SortContextInterface sortContextObject) {
        T one;
        T two;
        int size = list.size();
        List<T> list0 = list;
        List<T> list1 = new ArrayList<>(list);
        int blockSize = 1;
        while (blockSize < size) {
            for (int i = 0; i < size; i += blockSize * 2) {
                int j = 0;
                int k = 0;
                while ((j < blockSize && (i + j) < size) || (k < blockSize && (i + blockSize + k) < size)) {
                    if (j < blockSize && (i + j) < size) {
                        one = list0.get(i + j);
                    } else {
                        one = null;
                    }
                    if (k < blockSize && (i + blockSize + k) < size) {
                        two = list0.get(i + blockSize + k);
                    } else {
                        two = null;
                    }
                    sortContextObject.incCmpCount();
                    if ((one != null && two != null && (one.compareTo(two) < 0)) || (one != null && two == null)) {
                        list1.set(i + j + k, (T) one);
                        j++;
                    }
                    if ((one != null && two != null && (one.compareTo(two) >= 0)) || (one == null && two != null)) {
                        list1.set(i + j + k, (T) two);
                        k++;
                    }
                }
            }
            blockSize = blockSize << 1;
            List<T> list2 = list1;
            list1 = list0;
            list0 = list2;
        }
        return list0;
    }

}
