package example2;

/**
 * Фабрика классов.
 */
public class Factory<T extends EncryptionIntf> implements FactoryIntf<T> {
    @Override
    public T createObject(Class<T> theClass) throws IllegalAccessException, InstantiationException {
        return (T)theClass.newInstance();
    }
}
