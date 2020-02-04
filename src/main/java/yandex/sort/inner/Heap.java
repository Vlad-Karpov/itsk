package yandex.sort.inner;

import yandex.sort.SortContextInterface;

import java.util.List;
import java.util.function.Consumer;

public class Heap {

    public static <T extends Comparable<? super T>> List<T> heapSort(List<T> list, SortContextInterface sortContextObject) {
        if (sortContextObject != null) sortContextObject.clear();
        return innerHeapSort(list, sortContextObject);
    }

    private static <T extends Comparable<? super T>> List<T> innerHeapSort(List<T> list, SortContextInterface sortContextObject) {
        int cld = 0;
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
                        cld = (c << 1) + 1;
                        if (cld < s) {
                            cmpAndSwap(list, cld, c, sortContextObject);
                        }
                        cld = (c << 1) + 2;
                        if (cld < s) {
                            cmpAndSwap(list, cld, c, sortContextObject);
                        }
                    }
                    if ((c & 1) == 0) {         //even - from right
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

    private static <T extends Comparable<? super T>> void sift(List<T> list, int c, SortContextInterface sortContextObject) {

    }

    private static <T extends Comparable<? super T>> void cmpAndSwap(List<T> list, int cld, int c, SortContextInterface sortContextObject) {
        T cldV = list.get(cld);
        T cV = list.get(c);
        sortContextObject.incCmpCount();
        if (cldV.compareTo(cV) > 0) {
            list.set(cld, cV);
            list.set(c, cldV);
            sortContextObject.incSwapCount();
        }
    }

    public static <T extends Comparable<? super T>> List<T> heapPrint(List<T> list, Consumer<T> func) {
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
                        func.accept(list.get(c));
                    }
                    if ((c & 1) == 0) {         //even - from right
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
