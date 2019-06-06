package example2;

/**
 * Интерфейс фабрики классов.
 */
public interface FactoryIntf<T extends EncryptionIntf> {
    T createObject(Class<T> theClass) throws IllegalAccessException, InstantiationException;
}
