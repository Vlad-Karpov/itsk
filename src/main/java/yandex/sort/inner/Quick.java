package yandex.sort.inner;

import yandex.sort.SortContextInterface;

import java.util.List;

public class Quick {

    public static <T extends Comparable<? super T>> void quickSort(List<T> list, SortContextInterface sortContextObject) {
        if (sortContextObject != null ) sortContextObject.clear();
        innerQuickSort(list, 0, list.size() - 1, sortContextObject);
    }

    private static <T extends Comparable<? super T>> void innerQuickSort(List<T> list, int b1, int e1, SortContextInterface sortContextObject) {
        int c1 = 0;
        int c2 = 0;
        int b = b1;
        int e = e1;
        if (b < e) {
            Comparable midlElement = list.get(b + ((e - b) >> 1));
            while (true) {
                while (b < list.size() && ((c1 = ((Comparable) list.get(b)).compareTo(midlElement)) < 0)) {
                    b++;
                    sortContextObject.incCmpCount();
                }
                while (e > 0 && ((c2 = ((Comparable) list.get(e)).compareTo(midlElement)) > 0)) {
                    e--;
                    sortContextObject.incCmpCount();
                }
                if (b < e) {
                    if (!(c1 == 0 && c2 == 0)) {
                        T swap = list.get(b);
                        list.set(b, list.get(e));
                        list.set(e, swap);
                        sortContextObject.incSwapCount();
                    }
                    b++;
                    e--;
                } else {
                    break;
                }
            }
            if (e != e1) innerQuickSort(list, b1, e, sortContextObject);
            if (b != b1) innerQuickSort(list, b, e1, sortContextObject);
        }
    }

}
