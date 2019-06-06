package yandex.sort;

public interface SortContextInterface {
    void clear();

    long getCmpCount();

    void setCmpCount(long cmpCount);

    long getSwapCount();

    void setSwapCount(long swapCount);

    long incCmpCount();

    long incSwapCount();
}
