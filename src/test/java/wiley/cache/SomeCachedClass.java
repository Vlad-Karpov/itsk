package wiley.cache;

import java.io.Serializable;

/**
* Some serializable class for tests.
*/
public class SomeCachedClass implements Serializable {

    private static final long serialVersionUID = 1L;

    private static int cnt = -1;

    private int p0;
    private int p1;
    private int p2;
    private String s1;

    public SomeCachedClass() {
        cnt++;
        p1 = cnt;
        cnt++;
        p2 = cnt;
        s1 = p0 + " one " + p1 + " two " + p2 + " three";
    }

    public SomeCachedClass(int p1, int p2, String s1) {
        this.p1 = p1;
        this.p2 = p2;
        this.s1 = s1;
    }

    public SomeCachedClass(int p0, int p1, int p2, String s1) {
        this.p0 = p0;
        this.p1 = p1;
        this.p2 = p2;
        this.s1 = s1;
    }

    public SomeCachedClass(int theP0) {
        this();
        this.p0 = theP0;
    }

    public int getP0() {
        return p0;
    }

    public void setP0(int p0) {
        this.p0 = p0;
    }

    public int getP1() {
        return p1;
    }

    public void setP1(int p1) {
        this.p1 = p1;
    }

    public int getP2() {
        return p2;
    }

    public void setP2(int p2) {
        this.p2 = p2;
    }

    public String getS1() {
        return s1;
    }

    public void setS1(String s1) {
        this.s1 = s1;
    }

    @Override
    public String toString() {
        return "SomeCachedClass{" +
                "p0=" + p0 +
                ", p1=" + p1 +
                ", p2=" + p2 +
                ", s1='" + s1 + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SomeCachedClass)) return false;

        SomeCachedClass that = (SomeCachedClass) o;

        if (p0 != that.p0) return false;
        if (p1 != that.p1) return false;
        if (p2 != that.p2) return false;
        if (s1 != null ? !s1.equals(that.s1) : that.s1 != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = p0;
        result = 31 * result + p1;
        result = 31 * result + p2;
        result = 31 * result + (s1 != null ? s1.hashCode() : 0);
        return result;
    }

}
