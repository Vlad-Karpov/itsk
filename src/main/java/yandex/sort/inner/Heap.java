package yandex.sort.inner;

import yandex.sort.SortContextInterface;

import java.util.List;
import java.util.function.Consumer;

public class Heap {

    public static <T extends Comparable<? super T>> List<T> heapSort(List<T> list, SortContextInterface sortContextObject) {
        if (sortContextObject != null) sortContextObject.clear();
        return innerHeapSort(list, sortContextObject);
    }

    private static <T extends Comparable<? super T>> List<T> innerHeapSort(
            List<T> list, SortContextInterface sortContextObject) {
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
                    sift(list, c, s, sortContextObject);
                    if ((c & 1) == 0) {     //even - from right
                        c = c - 2 >> 1;
                        d = 2;
                    } else {                //odd - from left
                        c = c - 1 >> 1;
                        d = 1;
                    }
                    break;
                }
            }
        }
        while (s > 0) {
            sortContextObject.incSwapCount();
            T sV = list.get(s - 1);
            T hV = list.get(0);
            list.set(0, sV);
            list.set(s - 1, hV);
            s--;
            sift(list, 0, s, sortContextObject);
        }
        return list;
    }

    private static <T extends Comparable<? super T>> void sift(
            List<T> list, int c, int s,
            SortContextInterface sortContextObject) {
        while (c < s) {
            int l = (c << 1) + 1;
            int r = (c << 1) + 2;
            T lV = null;
            T rV = null;
            boolean ok = false;
            if (l < s) {
                lV = list.get(l);
                ok = true;
            }
            if (r < s) {
                rV = list.get(r);
                ok = true;
            }
            if (ok) {
                T cV = list.get(c);
                int ch = -1;
                T chV = null;
                sortContextObject.incCmpCount();
                int cmp;
                if (lV == null) {
                    cmp = -1;
                } else {
                    if (rV == null) {
                        cmp = 1;
                    } else {
                        cmp = lV.compareTo(rV);
                    }
                }
                if (cmp > 0) {
                    sortContextObject.incCmpCount();
                    if (cV.compareTo(lV) < 0) {
                        ch = l;
                        chV = lV;
                    }
                }
                if (cmp <= 0) {
                    sortContextObject.incCmpCount();
                    if (cV.compareTo(rV) < 0) {
                        ch = r;
                        chV = rV;
                    }
                }
                if (ch > 0) {
                    sortContextObject.incSwapCount();
                    list.set(ch, cV);
                    list.set(c, chV);
                    c = ch;
                } else {
                    break;
                }
            } else {
                break;
            }
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
