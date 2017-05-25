
package services.encodeURL;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import javax.cache.Cache;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteAtomicSequence;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.query.SqlQuery;
import org.apache.ignite.resources.IgniteInstanceResource;
import org.apache.ignite.services.ServiceContext;
import services.encodeURL.common.URL;
import services.encodeURL.common.encodeURLService;

public class encodeURLServiceImpl implements encodeURLService {
    @IgniteInstanceResource
    private Ignite ignite;

    /* Reference to the cache. */
    private IgniteCache<Integer, URL> urlCache;

    /* TODO: Unique suffixes generator */
    private IgniteAtomicSequence sequence;

    public void init(ServiceContext ctx) throws Exception {
        System.out.println("\n ** Initializing encodeURL Service on node:" + ignite.cluster().localNode() + " **");
        urlCache = ignite.cache("URL cache");
    }

    public void execute(ServiceContext ctx) throws Exception {
        System.out.println("\n ** Executing encodeURL service on node:" + ignite.cluster().localNode() + " **");
         /* Getting the sequence that will be used for appending to URL in case of hash collisions */
        sequence = ignite.atomicSequence("SuffixIDs", 1, true);
    }

    public void cancel(ServiceContext ctx) {
        System.out.println("\n ** Stopping Maintenance Service on node:" + ignite.cluster().localNode() + " **");
    }

    public String getHash(String url) {
        StringBuilder hashedUrl = new StringBuilder();
        int i = 0;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(url.getBytes("UTF-16"));
            String s = Base64.getEncoder().encodeToString(hash);
            char[] arr = s.toCharArray();

            for (i = 0; i < 7; i++) {
                hashedUrl.append(arr[i]);
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return hashedUrl.toString();
    }
}
