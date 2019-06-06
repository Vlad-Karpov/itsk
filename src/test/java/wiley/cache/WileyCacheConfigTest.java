package wiley.cache;

import org.junit.Test;
import org.junit.Assert;

import javax.xml.bind.JAXBException;

/**
 * Wiley cache configuration test.
 */
public class WileyCacheConfigTest {

    @Test
    public void test1() throws JAXBException {
        WileyCacheConfig cfg = WileyCacheConfig.getDefaultInstance();
        cfg.setMaxItemInCache(10000);
        cfg.setWileyCacheDirectory("e:/WileyCache");
        cfg.setWileyCacheStrategy(WileyCacheConfig.WileyCacheStrategy.MEMORY_AND_FILE);
        cfg.saveConfiguration("e:/dfltCfg.xml");
    }

    @Test
    public void test2() throws JAXBException {
        WileyCacheConfig cfg = WileyCacheConfig.loadConfiguration("e:/dfltCfg.xml");
        System.out.print(cfg);
    }

    @Test
    public void test3() throws JAXBException {
        WileyCacheConfig cfg = WileyCacheConfig.getDefaultInstance();
        cfg.setMaxItemInCache(8);
        cfg.setMaxItemInMemoryCache(4);
        cfg.setWileyCacheDirectory("e:/WileyCache");
        cfg.setWileyCacheStrategy(WileyCacheConfig.WileyCacheStrategy.MEMORY_AND_FILE);
        WileyCache<Integer, SomeCachedClass> wc = new WileyCache<Integer, SomeCachedClass>();
        try {
            for (int i = 0; i < 10; i++) {
                wc.put(i, new SomeCachedClass(i));
            }
            SomeCachedClass sc0 = wc.get(0);
            Assert.assertNull(sc0);
            SomeCachedClass sc1 = wc.get(1);
            Assert.assertNull(sc1);

            SomeCachedClass sc;
            for (int i = 2; i < 10; i ++) {
                sc = wc.get(i);
                String str = "0 one " + (i * 2) + " two " + (i * 2 + 1) + " three";
                Assert.assertEquals("!", new SomeCachedClass(i, i * 2, i * 2 + 1, str), sc);
            }

            for (int i = 0; i < 5; i++) {
                sc = wc.get(2);
                Assert.assertEquals("!", new SomeCachedClass(2, 4, 5, "0 one 4 two 5 three"), sc);
            }
            sc = wc.get(2);
            Assert.assertEquals("!", new SomeCachedClass(2, 4, 5, "0 one 4 two 5 three"), sc);
        } finally {
            wc.clear();
        }
    }

}
