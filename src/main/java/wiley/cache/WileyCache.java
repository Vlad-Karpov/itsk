package wiley.cache;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Create a configurable two-level cache (for caching Objects).  Level 1 is memory, level 2 is filesystem.
 * Config params should let one specify the cache strategies and max sizes of level  1 and 2.
 */
public class WileyCache<K extends Comparable<K>, V extends Serializable> implements Map<K, V> {

    /**
     * Sequence for index number of objects.
     */
    private AtomicLong indexNumberSequence = new AtomicLong(0L);

    /**
     * In memory cache.
     */
    HashMap<K, ValueWrapper<K, V>> inMemoryCache = new HashMap<K, ValueWrapper<K, V>>();
    /**
     * Index inMemoryCache by hitCount and indexNumber.
     */
    TreeSet<ValueWrapper<K, V>> inMemoryValueWrapperIndex = new TreeSet<ValueWrapper<K, V>>();
    /**
     * In file cache.
     */
    HashMap<K, ValueWrapper<K, V>> inFileCache = new HashMap<K, ValueWrapper<K, V>>();
    /**
     * Index fileCache by hitCount and indexNumber.
     */
    TreeSet<ValueWrapper<K, V>> fileValueWrapperIndex = new TreeSet<ValueWrapper<K, V>>();

    /**
     * Configuration of cache.
     */
    private WileyCacheConfig wileyCacheConfig;

    /**
     * Constructor. Default configuration.
     */
    public WileyCache() {
        wileyCacheConfig = WileyCacheConfig.getDefaultInstance();
    }

    /**
     * Constructor. Configuration from object.
     *
     * @param wileyCacheConfig Conaiguration from object.
     */
    public WileyCache(WileyCacheConfig wileyCacheConfig) {
        this.wileyCacheConfig = wileyCacheConfig;
    }

    /**
     * Constructor. Configuration from xml file.
     *
     * @param wileyCacheConfigXmlFile path and file name of configuration.
     * @throws JAXBException exeption
     */
    public WileyCache(String wileyCacheConfigXmlFile) throws JAXBException {
        this.wileyCacheConfig = WileyCacheConfig.loadConfiguration(wileyCacheConfigXmlFile);
    }

    @Override
    public int size() {
        return inMemoryCache.size() + inFileCache.size();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return inMemoryCache.containsKey(key) || inFileCache.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        boolean result = false;
        Collection<ValueWrapper<K, V>> clm = inMemoryCache.values();
        Iterator<ValueWrapper<K, V>> iclm = clm.iterator();
        while (iclm.hasNext()) {
            ValueWrapper<K, V> vw = iclm.next();
            if (vw.value.equals(value)) {
                result = true;
                break;
            }
        }
        if (!result) {
            Collection<ValueWrapper<K, V>> cld = inFileCache.values();
            Iterator<ValueWrapper<K, V>> icld = clm.iterator();
            while (icld.hasNext()) {
                ValueWrapper<K, V> vw = icld.next();
                String pathname = wileyCacheConfig.getWileyCacheDirectory() + "\\" + vw.indexNumber;
                ValueWrapper<K, V> vwr = readObjectFromDisk(pathname);
                if (vwr != null) {
                    if (vwr.value.equals(value)) {
                        result = true;
                        break;
                    }
                }
            }
        }
        return result;
    }

    @Override
    public V get(Object key) {
        V result = null;
        ValueWrapper<K, V> vw = inMemoryCache.get(key);
        if (vw != null) {
            result = vw.value;
            //increment hit count and modify index
            inMemoryValueWrapperIndex.remove(vw);
            vw.hitCount++;
            inMemoryValueWrapperIndex.add(vw);
        } else {
            vw = inFileCache.get(key);
            if (vw != null) {
                //read from file
                String pathname = wileyCacheConfig.getWileyCacheDirectory() + "\\" + vw.indexNumber;
                ValueWrapper<K, V> vwr = readObjectFromDisk(pathname);
                if (vwr != null) {
                    result = vwr.value;
                } else {
                    result = null;
                }

                if (result != null) {
                    //increment hit count and modify index
                    fileValueWrapperIndex.remove(vw);
                    vw.hitCount++;
                    fileValueWrapperIndex.add(vw);
                    // If in memory index hit count less then in file item, then up file item to memory
                    if (inMemoryValueWrapperIndex.last().hitCount < vw.hitCount) {

                        // find the object with minimum hit count and minimum index number.
                        ValueWrapper<K, V> evictionItem = inMemoryValueWrapperIndex.first();
                        //and remove it form the memory cache
                        inMemoryCache.remove(evictionItem.key);
                        inMemoryValueWrapperIndex.remove(evictionItem);

                        //remove vw from disk
                        deleteObjectFromDisk(pathname);
                        inFileCache.remove(key);
                        fileValueWrapperIndex.remove(vw);

                        //add vw to memory
                        vw.value = result;
                        inMemoryCache.put((K) key, vw);
                        inMemoryValueWrapperIndex.add(vw);

                        //add evictionItem to disc
                        pathname = wileyCacheConfig.getWileyCacheDirectory() + "\\" + evictionItem.indexNumber;
                        writeObjectToDisk(pathname, evictionItem);
                        //Clear object value before save wrapper into memory
                        evictionItem.value = null;
                        inFileCache.put(evictionItem.key, evictionItem);
                        fileValueWrapperIndex.add(evictionItem);

                    }
                    //
                }

            }
        }
        return result;
    }

    @Override
    public V put(K key, V value) {
        V result = null;
        ValueWrapper<K, V> vw = new ValueWrapper<K, V>();
        vw.setIndexNumber(indexNumberSequence.incrementAndGet());
        vw.setKey(key);
        vw.setValue(value);
        if (inMemoryCache.size() == wileyCacheConfig.getMaxItemInMemoryCache()) {
            // find the object with minimum hit count and minimum index number.
            ValueWrapper<K, V> evictionItem = inMemoryValueWrapperIndex.first();
            //and remove it form the memory cache
            inMemoryCache.remove(evictionItem.key);
            inMemoryValueWrapperIndex.remove(evictionItem);
            if (wileyCacheConfig.getWileyCacheStrategy() == WileyCacheConfig.WileyCacheStrategy.MEMORY_AND_FILE) {
                // if file cache option available, then put evictionItem into a disk file
                if (fileValueWrapperIndex.size() == (wileyCacheConfig.getMaxItemInCache() - wileyCacheConfig.getMaxItemInMemoryCache())) {
                    // find the object with minimum hit count and minimum index number in the file cache.
                    ValueWrapper<K, V> evictionFileItem = fileValueWrapperIndex.first();
                    //and remove it form the file cache
                    inFileCache.remove(evictionFileItem.key);
                    fileValueWrapperIndex.remove(evictionFileItem);
                    // Remove file from disk.
                    String pathname = wileyCacheConfig.getWileyCacheDirectory() + "\\" + evictionFileItem.indexNumber;
                    deleteObjectFromDisk(pathname);
                }
                // Write object to disk file.
                String pathname = wileyCacheConfig.getWileyCacheDirectory() + "\\" + evictionItem.indexNumber;
                writeObjectToDisk(pathname, evictionItem);
                //Clear object value before save wrapper into memory
                evictionItem.value = null;
                inFileCache.put(evictionItem.key, evictionItem);
                fileValueWrapperIndex.add(evictionItem);
            }
        }
        ValueWrapper<K, V> vwr = inMemoryCache.put(key, vw);
        inMemoryValueWrapperIndex.add(vw);
        if (vwr != null) {
            result = vwr.value;
        }
        return result;
    }

    /**
     * Read object from disk.
     *
     * @param pathname path and file name.
     * @return Value wrapper object.
     */
    ValueWrapper<K, V> readObjectFromDisk(String pathname) {
        ValueWrapper<K, V> result = null;
        File f = new File(pathname);
        try {
            if (f.exists()) {
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(new File(pathname)));
                try {
                    Object obj = in.readObject();
                    ValueWrapper<K, V> vwr = (ValueWrapper<K, V>) obj;
                    result = vwr;
                } catch (Exception e) {
                    //log.error("Can not read file " + pathname + "!", e);
                    e.printStackTrace();
                } finally {
                    in.close();
                }
            } else {
                //log.error("Can not find file " + pathname + "!");
            }
        } catch (Exception e) {
            //log.error("Can not read file " + pathname + "!", e);
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Write object to disk.
     *
     * @param pathname path and file name.
     * @param theItem  object to write.
     */
    void writeObjectToDisk(String pathname, ValueWrapper<K, V> theItem) {
        File f = new File(pathname);
        try {
            f.deleteOnExit();
            if (f.createNewFile()) {
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(f));
                try {
                    out.writeObject(theItem);
                } catch (Exception e) {
                    //log.error("Can not create file " + pathname + "!", e);
                    e.printStackTrace();
                } finally {
                    out.flush();
                    out.close();
                }
            } else {
                //log.error("Can not create file " + pathname + "!");
            }
        } catch (Exception e) {
            //log.error("Can not create file " + pathname + "!", e);
            e.printStackTrace();
        }
    }

    /**
     * Delete object from disk.
     *
     * @param pathname path and file name.
     */
    void deleteObjectFromDisk(String pathname) {
        try {
            File f = new File(pathname);
            if (!f.delete()) {
                //log.warn("Can not delete file " + pathname + "!");
            }
        } catch (Throwable e) {
            //log.warn("Can not delete file " + pathname + "!", e);
        }
    }

    @Override
    public V remove(Object key) {
        V result = null;
        ValueWrapper<K, V> vw = inMemoryCache.get(key);
        if (vw != null) {
            result = vw.value;
            inMemoryCache.remove(vw.key);
            inMemoryValueWrapperIndex.remove(vw);
        } else {
            vw = inFileCache.get(key);
            if (vw != null) {
                inFileCache.remove(key);
                String pathname = wileyCacheConfig.getWileyCacheDirectory() + "\\" + vw.indexNumber;
                ValueWrapper<K, V> vwr = readObjectFromDisk(pathname);
                if (vwr != null) {
                    result = vwr.value;
                    deleteObjectFromDisk(pathname);
                    fileValueWrapperIndex.remove(vwr);
                } else {
                    result = null;
                }
            }
        }
        return result;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        Set<? extends K> st = m.keySet();
        for (K key : st) {
            V vle = m.get(key);
            put(key, vle);
        }
    }

    @Override
    public void clear() {
        Iterator<ValueWrapper<K, V>> itr = fileValueWrapperIndex.iterator();
        while (itr.hasNext()) {
            ValueWrapper<K, V> vw = itr.next();
            // Remove file from disk.
            String pathname = wileyCacheConfig.getWileyCacheDirectory() + "\\" + vw.indexNumber;
            deleteObjectFromDisk(pathname);
        }
        inFileCache.clear();
        fileValueWrapperIndex.clear();
        inMemoryCache.clear();
        inMemoryValueWrapperIndex.clear();
    }

    @Override
    public Set<K> keySet() {
        Set<K> result = inMemoryCache.keySet();
        result.addAll(inFileCache.keySet());
        return result;
    }

    /**
     * Vary bad method!
     *
     * @return all values.
     */
    @Override
    public Collection<V> values() {
        ArrayList<V> result = new ArrayList<V>();
        Collection<ValueWrapper<K, V>> clm = inMemoryCache.values();
        Iterator<ValueWrapper<K, V>> iclm = clm.iterator();
        while (iclm.hasNext()) {
            ValueWrapper<K, V> vw = iclm.next();
            result.add(vw.value);
        }
        Collection<ValueWrapper<K, V>> cld = inFileCache.values();
        Iterator<ValueWrapper<K, V>> icld = cld.iterator();
        while (icld.hasNext()) {
            ValueWrapper<K, V> vw = icld.next();
            String pathname = wileyCacheConfig.getWileyCacheDirectory() + "\\" + vw.indexNumber;
            ValueWrapper<K, V> vwr = readObjectFromDisk(pathname);
            if (vwr != null) {
                result.add(vwr.value);
            }
        }
        return result;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> result = new HashSet<Entry<K, V>>();
        Collection<ValueWrapper<K, V>> clm = inMemoryCache.values();
        Iterator<ValueWrapper<K, V>> iclm = clm.iterator();
        while (iclm.hasNext()) {
            ValueWrapper<K, V> vw = iclm.next();
            vw.setOwner(this);
            result.add(vw);
        }
        Collection<ValueWrapper<K, V>> cld = inFileCache.values();
        Iterator<ValueWrapper<K, V>> icld = cld.iterator();
        while (icld.hasNext()) {
            ValueWrapper<K, V> vw = icld.next();
            vw.setOwner(this);
            result.add(vw);
        }
        return result;
    }

    V readValue(ValueWrapper<K, V> vw) {
        String pathname = wileyCacheConfig.getWileyCacheDirectory() + "\\" + vw.indexNumber;
        ValueWrapper<K, V> vwr = readObjectFromDisk(pathname);
        if (vwr != null) {
            vwr.setOwner(this);
            return vwr.value;
        }
        return vw.value;
    }

}
