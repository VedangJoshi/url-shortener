
package common.filters;

import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.lang.IgnitePredicate;
import org.apache.ignite.services.ServiceConfiguration;


public class encodeURLServiceFilter implements IgnitePredicate<ClusterNode>{
    /**
     * Checks if {@code node} needs to be considered as a encode URl Service Node.
     * @param node Cluster node instance.
     * @return {@code true} if the node has to be considered as encode URL Service Node, {@code false} otherwise.
     */
    public boolean apply(ClusterNode node) {
        Boolean dataNode = node.attribute("encodeURL.service.node");
        return dataNode != null && dataNode;
    }
}
