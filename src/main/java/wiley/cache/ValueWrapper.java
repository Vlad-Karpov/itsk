package wiley.cache;

import java.io.Serializable;
import java.util.Map;

/**
 * Wrapper of value object.
 */
public class ValueWrapper<K extends Comparable<K>, V extends Serializable> implements Map.Entry<K, V>, Comparable<ValueWrapper>, Serializable {

    /**
     * Key.
     */
    K key;
    /**
     * Value.
     */
    V value;

    /**
     * Index nember.
     */
    long indexNumber;

    /**
     * Owner of object.
     */
    transient WileyCache<K, V> owner = null;

    /**
     * Hit object counter;
     */
    long hitCount = 0L;

    public ValueWrapper() {
    }

    public ValueWrapper(WileyCache<K, V> owner) {
        this.owner = owner;
    }

    public V getValue() {
        if (value == null && owner != null) {
            return owner.readValue(this);
        }
        return value;
    }

    public V setValue(V value) {
        this.value = value;
        return this.value;
    }

    public long getIndexNumber() {
        return indexNumber;
    }

    public void setIndexNumber(long indexNumber) {
        this.indexNumber = indexNumber;
    }

    public long getHitCount() {
        return hitCount;
    }

    public void setHitCount(long hitCount) {
        this.hitCount = hitCount;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public WileyCache<K, V> getOwner() {
        return owner;
    }

    public void setOwner(WileyCache<K, V> owner) {
        this.owner = owner;
    }

    @Override
    public int compareTo(ValueWrapper o) {
        Long result = hitCount - o.hitCount;
        if (result.equals(0L)) {
            result = indexNumber - o.indexNumber;
        }
        return result.intValue();
    }

    @Override
    public String toString() {
        return "ValueWrapper{" +
                "key=" + key +
                ", value=" + value +
                ", indexNumber=" + indexNumber +
                ", hitCount=" + hitCount +
                '}';
    }

}
