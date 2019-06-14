public class Object {
    public final native Class<?> getClass();

    public native int hashCode();
    public boolean equals(Object obj) { return (this == obj); }

    public final native void notify();
    public final native void notifyAll();
    public final native void wait(long timeout) throws InterruptedException;
    public final void wait(long timeout, int nanos) throws InterruptedException { /* wait(count(timeout, nanos)); */}
    public final void wait() throws InterruptedException { wait(0); }

    protected native Object clone() throws CloneNotSupportedException; //"shallow copy"
    public String toString() { return getClass().getName() + "@" + Integer.toHexString(hashCode()); }
    protected void finalize() throws Throwable { }
}