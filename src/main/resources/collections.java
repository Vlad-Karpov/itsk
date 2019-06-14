public interface Collection<E> extends Iterable<E> {
    // Query Operations
    int size();
    boolean isEmpty();
    boolean contains(Object o);
    Iterator<E> iterator();
    Object[] toArray();
    <T> T[] toArray(T[] a);
    // Modification Operations
    boolean add(E e);
    boolean remove(Object o);
    // Bulk Operations
    boolean containsAll(java.util.Collection<?> c);
    boolean addAll(java.util.Collection<? extends E> c);
    boolean removeAll(java.util.Collection<?> c);
    default boolean removeIf(Predicate<? super E> filter);
    boolean retainAll(java.util.Collection<?> c);
    void clear();
    // Comparison and hashing
    boolean equals(Object o);
    int hashCode();
    default Spliterator<E> spliterator();
    default Stream<E> stream();
    default Stream<E> parallelStream();
    // !! from Iterable !!
    default void forEach(Consumer<? super T> action); //to be the target of the "for-each loop" statement
}
public interface Queue<E> extends java.util.Collection<E> {
    boolean add(E e);           //Inserts the specified element into this queue
    boolean offer(E e);         //Inserts the specified element into this queue. preferable to {@link #add}
    E remove();                 //Retrieves and removes the head of this queue. NoSuchElementException
    E poll();                   //Retrieves and removes the head of this queue
    E element();                //Retrieves, but does not remove, the head of this queue.  NoSuchElementException
    E peek();                   //Retrieves, but does not remove, the head of this queue
}   //PriorityQueue
public interface Deque<E> extends java.util.Queue<E> {
    void addFirst(E e);         //Inserts the specified element at the front of this deque
    void addLast(E e);          //Inserts the specified element at the end of this deque
    boolean offerFirst(E e);    //Inserts the specified element at the front
    boolean offerLast(E e);     //Inserts the specified element at the end of this deque
    E removeFirst();            //Retrieves and removes the first element of this deque. NoSuchElementException
    E removeLast();             //Retrieves and removes the last element of this deque. NoSuchElementException
    E pollFirst();              //Retrieves and removes the first element of this deque
    E pollLast();               //Retrieves and removes the last element of this deque
    E getFirst();               //Retrieves, but does not remove, the first element of this deque.NoSuchElementException
    E getLast();                //Retrieves, but does not remove, the last element of this deque.NoSuchElementException
    E peekFirst();              //Retrieves, but does not remove, the first element of this deque,
    E peekLast();               //Retrieves, but does not remove, the last element of this deque,
    boolean removeFirstOccurrence(Object o); //Removes the first occurrence of the specified element from this deque.
    boolean removeLastOccurrence(Object o); //Removes the last occurrence of the specified element from this deque.
    // *** Queue methods ***
    //add offer remove poll element peek
    // *** Stack methods ***
    void push(E e); //Pushes an element onto the stack represented by this deque (in other words, at the head)
    E pop(); //Pops an element from the stack represented by this deque. (removes and returns the first element)
    // *** Collection methods ***
}   //ArrayDeque, LinkedList
public interface List<E> extends java.util.Collection<E> {
    // Query Operations
    int size();
    boolean isEmpty();
    boolean contains(Object o);
    Iterator<E> iterator();
    Object[] toArray();
    <T> T[] toArray(T[] a);
    // Modification Operations
    boolean add(E e);
    boolean remove(Object o);
    // Bulk Modification Operations
    boolean containsAll(java.util.Collection<?> c);
    boolean addAll(java.util.Collection<? extends E> c);
    boolean addAll(int index, java.util.Collection<? extends E> c);
    boolean removeAll(java.util.Collection<?> c);
    boolean retainAll(java.util.Collection<?> c);
    default void replaceAll(UnaryOperator<E> operator);
    default void sort(Comparator<? super E> c);
    void clear();
    // Comparison and hashing
    boolean equals(Object o);
    int hashCode();
    // Positional Access Operations
    E get(int index);
    E set(int index, E element);
    void add(int index, E element);
    E remove(int index);
    // Search Operations
    int indexOf(Object o);
    int lastIndexOf(Object o);
    // List Iterators
    ListIterator<E> listIterator();
    ListIterator<E> listIterator(int index);
    // View
    java.util.List<E> subList(int fromIndex, int toIndex);
    default Spliterator<E> spliterator();
}   //ArrayList, LinkedList, Vector, Stack
public interface Set<E> extends java.util.Collection<E> {
    // Query Operations
    int size();
    boolean isEmpty();
    boolean contains(Object o);
    Iterator<E> iterator();
    Object[] toArray();
    <T> T[] toArray(T[] a);
    // Modification Operations
    boolean add(E e);
    boolean remove(Object o);
    // Bulk Operations
    boolean containsAll(java.util.Collection<?> c);
    boolean addAll(java.util.Collection<? extends E> c);
    boolean retainAll(java.util.Collection<?> c);
    boolean removeAll(java.util.Collection<?> c);
    void clear();
    // Comparison and hashing
    boolean equals(Object o);
    int hashCode();
    default Spliterator<E> spliterator();
}   //HashSet, LinkedHashSet
public interface SortedSet<E> extends java.util.Set<E> {
    Comparator<? super E> comparator();
    java.util.SortedSet<E> subSet(E fromElement, E toElement);
    java.util.SortedSet<E> headSet(E toElement);
    java.util.SortedSet<E> tailSet(E fromElement);
    E first();
    E last();
    default Spliterator<E> spliterator();
}
public interface NavigableSet<E> extends java.util.SortedSet<E> {
    E lower(E e); //ниже greatest element in this set strictly less than e
    E floor(E e); //пол  greatest element in this set less than or equal to e
    E ceiling(E e); //потолок least element in this set greater than or equal to e
    E higher(E e); //выше least element in this set greater than or equal to e
    E pollFirst();  //Retrieves and removes the first (lowest) element
    E pollLast();   //Retrieves and removes the last (highest) element
    Iterator<E> iterator();
    java.util.NavigableSet<E> descendingSet();
    Iterator<E> descendingIterator();
    java.util.NavigableSet<E> subSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive);
    java.util.NavigableSet<E> headSet(E toElement, boolean inclusive);
    java.util.NavigableSet<E> tailSet(E fromElement, boolean inclusive);
    java.util.SortedSet<E> subSet(E fromElement, E toElement);
    java.util.SortedSet<E> headSet(E toElement);
    java.util.SortedSet<E> tailSet(E fromElement);
}   //TreeSet
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public interface Map<K,V> {
    // Query Operations
    int size();
    boolean isEmpty();
    boolean containsKey(Object key);
    boolean containsValue(Object value);
    V get(Object key);
    // Modification Operations
    V put(K key, V value);
    V remove(Object key);
    // Bulk Operations
    void putAll(java.util.Map<? extends K, ? extends V> m);
    void clear();
    // Views
    java.util.Set<K> keySet();
    java.util.Collection<V> values();
    java.util.Set<java.util.Map.Entry<K, V>> entrySet();
    interface Entry<K,V> {};
    // Comparison and hashing
    boolean equals(Object o);
    int hashCode();
    // Defaultable methods
    default V getOrDefault(Object key, V defaultValue);
    default void forEach(BiConsumer<? super K, ? super V> action);
    default void replaceAll(BiFunction<? super K, ? super V, ? extends V> function);
    default V putIfAbsent(K key, V value);
    default boolean remove(Object key, Object value);
    default boolean replace(K key, V oldValue, V newValue);
    default V replace(K key, V value);
    default V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction);
    default V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction);
    default V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction);
    default V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction);
}   //HashMap, Hashtable, ConcurrentHashMap, LinkedHashMap, WeakHashMap
public interface SortedMap<K,V> extends java.util.Map<K,V> {
    Comparator<? super K> comparator();
    java.util.SortedMap<K,V> subMap(K fromKey, K toKey);
    java.util.SortedMap<K,V> headMap(K toKey);
    java.util.SortedMap<K,V> tailMap(K fromKey);
    K firstKey();
    K lastKey();
    java.util.Set<K> keySet();
    java.util.Collection<V> values();
    java.util.Set<Entry<K, V>> entrySet();
}
public interface NavigableMap<K,V> extends java.util.SortedMap<K,V> {
    java.util.Map.Entry<K,V> lowerEntry(K key);
    K lowerKey(K key);
    java.util.Map.Entry<K,V> floorEntry(K key);
    K floorKey(K key);
    java.util.Map.Entry<K,V> ceilingEntry(K key);
    K ceilingKey(K key);
    java.util.Map.Entry<K,V> higherEntry(K key);
    K higherKey(K key);
    java.util.Map.Entry<K,V> firstEntry();
    java.util.Map.Entry<K,V> lastEntry();
    java.util.Map.Entry<K,V> pollFirstEntry();
    java.util.Map.Entry<K,V> pollLastEntry();
    java.util.NavigableMap<K,V> descendingMap();
    java.util.NavigableSet<K> navigableKeySet();
    java.util.NavigableSet<K> descendingKeySet();
    java.util.NavigableMap<K,V> subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive);
    java.util.NavigableMap<K,V> headMap(K toKey, boolean inclusive);
    java.util.NavigableMap<K,V> tailMap(K fromKey, boolean inclusive);
    java.util.SortedMap<K,V> subMap(K fromKey, K toKey);
    java.util.SortedMap<K,V> headMap(K toKey);
    java.util.SortedMap<K,V> tailMap(K fromKey);
} //TreeMap
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public class Collections {
// sort, binarySearch, reverse, shuffle, swap, fill, copy, min, max, rotate, replaceAll, indexOfSubList,
// unmodifiable... Collection, Set, SortedSet, NavigableSet, List, RandomAccessList, Map, SortedMap, NavigableMap
// synchronized... Collection, Set, SortedSet, NavigableSet, List, RandomAccessList, Map, SortedMap, NavigableMap
// Dynamically typesafe collection wrappers
// checked... Collection, Queue, Set, SortedSet, NavigableSet, List, RandomAccessList, Map, SortedMap, NavigableMap
// Empty collections
// empty... Iterator, ListIterator, Enumeration, Set, SortedSet, NavigableSet, List, Map, SortedMap, NavigableMap
// Singleton collections
// singleton...Iterator, Spliterator, List, Map
// Miscellaneous
//
}
public class Arrays {
    public static <T> java.util.List<T> asList(T... a) {
        return new Arrays.ArrayList<>(a);
    }
}

public class ReferenceQueue<T> {
    //... для (Soft, Weak, Phantom)Reference и WeakHashMap
}