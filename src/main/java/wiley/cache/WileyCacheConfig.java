package wiley.cache;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.File;
import java.io.Serializable;

/**
 * Configuration of WileyCache.
 */
@XmlRootElement(namespace = "cache.wiley", name = "WileyCacheConfig")
@XmlType(name = "WileyCacheConfig", namespace = "cache.wiley",
        propOrder = {
                "maxItemInCache",
                "maxItemInMemoryCache",
                "wileyCacheDirectory",
                "wileyCacheStrategy"})
public class WileyCacheConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Default configuration.
     */
    private static WileyCacheConfig defaultConfig;

    /**
     * Initate default configuration.
     */
    static {
        defaultConfig = new WileyCacheConfig();
        defaultConfig.setMaxItemInMemoryCache(1000);
        defaultConfig.setMaxItemInCache(10000);
        defaultConfig.setWileyCacheDirectory("");
        defaultConfig.setWileyCacheStrategy(WileyCacheStrategy.MEMORY_AND_FILE);
    }

    /**
     * Maximum number items in cache.
     */
    private int maxItemInCache;

    /**
     * Maximum number items in memory cache.
     */
    private int maxItemInMemoryCache;

    /**
     * Cache directory where cache put cached objects as a data files.
     */
    private String wileyCacheDirectory;

    /**
     * Cache strategy.
     * Available values: WileyCacheStrategy.MEMORY_AND_FILE, WileyCacheStrategy.MEMORY, WileyCacheStrategy.FILE
     *
     * @serial Cache strategy.
     * @see wiley.cache.WileyCacheConfig.WileyCacheStrategy
     */
    private WileyCacheStrategy wileyCacheStrategy = WileyCacheStrategy.MEMORY_AND_FILE;

    /**
     * Maximum number items in cache.
     * @serial Maximum number items in cache.
     *
     * @return Maximum number items in cache.
     */
    @XmlElement
    public int getMaxItemInCache() {
        return maxItemInCache;
    }

    /**
     * Maximum number items in cache.
     *
     * @param maxItemInCache Maximum number items in cache.
     */
    public void setMaxItemInCache(int maxItemInCache) {
        this.maxItemInCache = maxItemInCache;
    }

    /**
     * Maximum number items in memory cache.
     * @serial Maximum number items in memory cache.
     *
     * @return Maximum number items in memory cache.
     */
    @XmlElement
    public int getMaxItemInMemoryCache() {
        return maxItemInMemoryCache;
    }

    /**
     * Maximum number items in memory cache.
     *
     * @param maxItemInMemoryCache Maximum number items in memory cache.
     */
    public void setMaxItemInMemoryCache(int maxItemInMemoryCache) {
        this.maxItemInMemoryCache = maxItemInMemoryCache;
    }

    /**
     * Cache directory where cache put cached objects as a data files.
     * @serial Cache directory where cache put cached objects as a data files.
     *
     * @return Cache directory where cache put cached objects as a data files.
     */
    @XmlElement
    public String getWileyCacheDirectory() {
        return wileyCacheDirectory;
    }

    /**
     * Cache directory where cache put cached objects as a data files.
     *
     * @param wileyCacheDirectory Cache directory where cache put cached objects as a data files.
     */
    public void setWileyCacheDirectory(String wileyCacheDirectory) {
        this.wileyCacheDirectory = wileyCacheDirectory;
    }

    /**
     * Cache strategy.
     * Available values: WileyCacheStrategy.MEMORY_AND_FILE, WileyCacheStrategy.MEMORY, WileyCacheStrategy.FILE
     *
     * @return Cache strategy.
     * @serial Cache strategy.
     * @see wiley.cache.WileyCacheConfig.WileyCacheStrategy
     */
    @XmlElement
    public WileyCacheStrategy getWileyCacheStrategy() {
        return wileyCacheStrategy;
    }

    /**
     * Cache strategy.
     * Available values: WileyCacheStrategy.MEMORY_AND_FILE, WileyCacheStrategy.MEMORY, WileyCacheStrategy.FILE
     *
     * @param wileyCacheStrategy Cache strategy.
     * @serial Cache strategy.
     * @see wiley.cache.WileyCacheConfig.WileyCacheStrategy
     */
    public void setWileyCacheStrategy(WileyCacheStrategy wileyCacheStrategy) {
        this.wileyCacheStrategy = wileyCacheStrategy;
    }

    /**
     * Save configuration in xml file on the disk.
     *
     * @param fileNameAndPath file name and path.
     * @throws JAXBException exception.
     */
    public void saveConfiguration(String fileNameAndPath) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(WileyCacheConfig.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.marshal(this, new File(fileNameAndPath));
    }

    /**
     * Load existing configuration.
     * @param wileyCacheConfigXmlFile path and file name.
     * @return configuration object.
     * @throws JAXBException exception.
     */
    public static WileyCacheConfig loadConfiguration(String wileyCacheConfigXmlFile) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(WileyCacheConfig.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (WileyCacheConfig) unmarshaller.unmarshal(new File(wileyCacheConfigXmlFile));
    }

    /**
     * Static factory method.
     *
     * @return default WileyCacheConfig instance.
     */
    public static WileyCacheConfig getDefaultInstance() {
        return defaultConfig;
    }

    @Override
    public String toString() {
        return "WileyCacheConfig{" +
                "maxItemInCache=" + maxItemInCache +
                ", maxItemInMemoryCache=" + maxItemInMemoryCache +
                ", wileyCacheDirectory='" + wileyCacheDirectory + '\'' +
                ", wileyCacheStrategy=" + wileyCacheStrategy +
                '}';
    }

    /**
     * Wiley cache strategy.
     */
    public enum WileyCacheStrategy {

        MEMORY("IN MEMORY"),
        MEMORY_AND_FILE("IN MEMORY AND FILE");

        private String wileyCacheStrategyStr;

        WileyCacheStrategy(String wileyCacheStrategyStr) {
            this.wileyCacheStrategyStr = wileyCacheStrategyStr;
        }

        public String getWileyCacheStrategyStr() {
            return wileyCacheStrategyStr;
        }

        public void setWileyCacheStrategyStr(String wileyCacheStrategyStr) {
            this.wileyCacheStrategyStr = wileyCacheStrategyStr;
        }
    }

}
