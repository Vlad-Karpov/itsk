package example2;

import org.junit.Test;


/**
 * Created by KARPOVVV on 20.07.17.
 */
public class example2 {
    @Test
    public void test1Test() throws InstantiationException, IllegalAccessException {
        Factory<DEScrypt> fact = new Factory<DEScrypt>();
        DEScrypt DESobj = fact.createObject(DEScrypt.class);
        System.out.println(DESobj.crypt("ddd", "fff"));
    }
    @Test
    public void test2Test() throws InstantiationException, IllegalAccessException {
        Factory<RSAcrypt> fact = new Factory<RSAcrypt>();
        RSAcrypt RSAobj = fact.createObject(RSAcrypt.class);
        System.out.println(RSAobj.crypt("ddd", "fff"));
    }
    @Test
    public void test3Test() throws InstantiationException, IllegalAccessException {
        Factory<GOSTcrypt> fact = new Factory<GOSTcrypt>();
        GOSTcrypt GOSTobj = fact.createObject(GOSTcrypt.class);
        System.out.println(GOSTobj.crypt("ddd", "fff"));
    }

}
