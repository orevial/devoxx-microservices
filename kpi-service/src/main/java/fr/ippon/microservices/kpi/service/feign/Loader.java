package fr.ippon.microservices.kpi.service.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "loader-service")
public interface Loader {
	@RequestMapping(name = "/nbIndexedDocuments", method = RequestMethod.GET, consumes = "application/json")
	Integer getNbIndexedDocuments();
}