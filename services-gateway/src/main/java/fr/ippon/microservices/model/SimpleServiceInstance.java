package fr.ippon.microservices.model;

import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.ServiceType;
import org.apache.curator.x.discovery.UriSpec;
import org.springframework.cloud.zookeeper.discovery.ZookeeperInstance;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Map;

/**
 * This class is used as a wrapper class of Zookeeper "ServiceInstance", to avoid
 * exposing useless information about each service.
 *
 * Created by olivier.revial on 24/03/2016.
 */
public class SimpleServiceInstance extends ServiceInstance {

    private Map<String, String> metadata;

    public SimpleServiceInstance(ServiceInstance<ZookeeperInstance> instance) {
        super(
                instance.getName(),
                instance.getId(),
                instance.getAddress(),
                instance.getPort(),
                instance.getSslPort(),
                instance.getPayload(),
                instance.getRegistrationTimeUTC(),
                instance.getServiceType(),
                instance.getUriSpec()
        );
        this.metadata = instance.getPayload().getMetadata();
    }

    @JsonIgnore
    @Override
    public Integer getSslPort() {
        return super.getSslPort();
    }

    @JsonIgnore
    @Override
    public long getRegistrationTimeUTC() {
        return super.getRegistrationTimeUTC();
    }

    @JsonIgnore
    @Override
    public UriSpec getUriSpec() {
        return super.getUriSpec();
    }

    @JsonIgnore
    @Override
    public ServiceType getServiceType() {
        return super.getServiceType();
    }

    @JsonIgnore
    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    @JsonIgnore
    public Object getPayload() {
        return super.getPayload();
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }
}