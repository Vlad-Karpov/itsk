package yandex.sort;

public class SortContext implements SortContextInterface {

    private long cmpCount;
    private long swapCount;

    public SortContext() {
        cmpCount = 0L;
        swapCount = 0L;
    }

    public void clear() {
        cmpCount = 0L;
        swapCount = 0L;
    }

    public long getCmpCount() {
        return cmpCount;
    }

    public void setCmpCount(long cmpCount) {
        this.cmpCount = cmpCount;
    }

    public long getSwapCount() {
        return swapCount;
    }

    public void setSwapCount(long swapCount) {
        this.swapCount = swapCount;
    }

    public long incCmpCount() {
        return cmpCount++;
    }

    public long incSwapCount() {
        return swapCount++;
    }

    @Override
    public String toString() {
        return "SortContext{" +
                "cmpCount=" + cmpCount +
                ", swapCount=" + swapCount +
                '}';
    }

}
