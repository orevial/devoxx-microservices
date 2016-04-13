package fr.ippon.microservices.discovery;

import com.netflix.turbine.discovery.Instance;
import com.netflix.turbine.discovery.InstanceDiscovery;
import org.apache.curator.x.discovery.ServiceInstance;
import org.springframework.cloud.zookeeper.discovery.ZookeeperInstance;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by florian.garcia on 23/03/2016.
 */

@Component
public class ZookeeperDiscovery implements InstanceDiscovery {

    @Inject
    private ZookeeperDiscoveryService discoveryService;

    @Override
    public Collection<Instance> getInstanceList() throws Exception {
        Collection<Instance> results = new ArrayList<>();

        for (ServiceInstance<ZookeeperInstance> instance: discoveryService.getAllServicesInstances()) {
            Instance result = new Instance(
                    instance.getAddress() + ":" + String.valueOf(instance.getPort()),
                    "default",
                    true
            );
            result.getAttributes().put("port", String.valueOf(instance.getPort()));
            results.add(result);
        }
        return results;
    }
}
