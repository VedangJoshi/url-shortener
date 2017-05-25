
package common.cachestore;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.cache.Cache;
import javax.cache.integration.CacheLoaderException;
import javax.cache.integration.CacheWriterException;
import org.apache.ignite.binary.BinaryObject;
import org.apache.ignite.cache.store.CacheStoreAdapter;

public class MongoCacheStore extends CacheStoreAdapter<String, String> {
    // TODO: Implement interface to MongoDB

    private Map<String, String> storeImpl = new ConcurrentHashMap<String, String>();

    public String load(String key) throws CacheLoaderException {
        System.out.println(" >>> Getting Value From Cache Store: " + key);

        return storeImpl.get(key);
    }

    public void write(Cache.Entry<? extends String, ? extends String> entry) throws CacheWriterException {
        System.out.println(" >>> Writing Value To Cache Store: " + entry);

        storeImpl.put(entry.getKey(), entry.getValue());
    }

    public void delete(Object key) throws CacheWriterException {
        System.out.println(" >>> Removing Key From Cache Store: " + key);

        storeImpl.remove(key);
    }
}
