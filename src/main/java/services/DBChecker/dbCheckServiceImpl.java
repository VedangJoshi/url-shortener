
package services.DBChecker;

import common.DataStore;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteAtomicSequence;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.resources.IgniteInstanceResource;
import org.apache.ignite.services.ServiceContext;
import services.DBChecker.common.dbCheckService;
import services.encodeURL.common.URL;

public class dbCheckServiceImpl implements dbCheckService {
    @IgniteInstanceResource
    private Ignite ignite;

    /* Reference to the cache. */
    private IgniteCache<Integer, URL> urlCache;

    public void init(ServiceContext ctx) throws Exception {
        System.out.println("\n ** Initializing encodeURL Service on node:" + ignite.cluster().localNode() + " **");
        urlCache = ignite.cache("URL");
    }

    public void execute(ServiceContext ctx) throws Exception {
        System.out.println("\n ** Executing encodeURL service on node:" + ignite.cluster().localNode() + " **");
    }

    public void cancel(ServiceContext ctx) {
        System.out.println("\n ** Stopping Maintenance Service on node:" + ignite.cluster().localNode() + " **");
    }


    public Boolean ifDuplicate(String url) {
        DataStore db = new DataStore();
        db.init();
        if(db.ifURLUnique(url)) {
            return false; // unique
        }

        return true; // duplicate
    }
}
