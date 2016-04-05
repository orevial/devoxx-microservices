package fr.ippon.microservices.kpi.service.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

import feign.Headers;
import feign.RequestLine;

@FeignClient("Loader")
public interface Loader {
	@RequestLine("GET /nbIndexedDocuments")
	@Headers("Content-Type: application/json")
	Integer getNbIndexedDocuments();
}