/**
 *
 */
package fr.ippon.microservices.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.ippon.microservices.model.DiscoveryResponse;
import fr.ippon.microservices.model.DiscoveryServiceResponse;
import fr.ippon.microservices.services.DiscoveryProxyService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

/**
 * @author stephane.lagraulet
 *
 */
@RestController
@RequestMapping("/discovery")
public class DiscoveryController {

    @Inject
    private DiscoveryProxyService discoveryService;

    private final ObjectMapper mapper = new ObjectMapper();

    @RequestMapping(method = RequestMethod.GET, value = "/listService/{serviceName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public DiscoveryServiceResponse listService(@PathVariable String serviceName) throws Exception {
        return discoveryService.listServiceInstances(serviceName);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/listAllServices", produces = MediaType.APPLICATION_JSON_VALUE)
    public DiscoveryResponse listAllServices() throws Exception {
        return discoveryService.listAllServicesInstances();
    }
}
