package yandex.sort.inner;

import yandex.sort.SortContextInterface;

import java.util.List;

public class Heap {

    public static <T extends Comparable<? super T>> List<T> heapSort(List<T> list, SortContextInterface sortContextObject) {
        if (sortContextObject != null) sortContextObject.clear();
        return innerHeapSort(list, sortContextObject);
    }

    private static <T extends Comparable<? super T>> List<T> innerHeapSort(List<T> list, SortContextInterface sortContextObject) {
        int c = 0;
        int d = 0;  //direction: 0 - come from parent, 1 - from left, 2 - from right
        int s = list.size();
        while (c >= 0) {
            switch (d) {
                case 0: {
                    if (c < s) {
                        d = 0;
                        c = (c << 1) + 1;
                    } else {
                        d = 1;
                    }
                    break;
                }
                case 1: {
                    if (c < s) {
                        d = 0;
                        c = (c << 1) + 2;
                    } else {
                        d = 2;
                    }
                    break;
                }
                case 2: {
                    if (c < s) {
                        System.out.println(list.get(c));
                    }
                    if ((c & 1) == 0) {  //even - from right
                        c = c - 2 >> 1;
                        d = 2;
                    } else {            //odd - from left
                        c = c - 1 >> 1;
                        d = 1;
                    }
                    break;
                }
            }
        }
        return list;
    }

}
