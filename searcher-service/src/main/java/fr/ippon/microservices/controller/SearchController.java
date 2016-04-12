package fr.ippon.microservices.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.ippon.microservices.model.CityResult;
import fr.ippon.microservices.service.SearchService;

@RestController
@RequestMapping("/search")
public class SearchController {

	@Inject
	private SearchService searchService;
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/city/{city}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<CityResult> searchByCity(@PathVariable String city) throws Exception {
		return searchService.searchCity(city);
		
	}

	@RequestMapping(method = RequestMethod.GET, value = "/geo/{aireGeo}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> searchByAireGeo(@PathVariable String aireGeo) throws Exception {
		return searchService.searchAireGeo(aireGeo);
	}
}