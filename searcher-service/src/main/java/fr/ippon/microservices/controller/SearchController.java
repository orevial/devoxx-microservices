package fr.ippon.microservices.controller;

import fr.ippon.microservices.model.CityResult;
import fr.ippon.microservices.service.SearchService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
public class SearchController {

	@Value("${spring.application.name:}")
	private String appName;

	@Value("${server.port:-1}")
	private int serverPort;

	@Inject
	private SearchService searchService;

	@RequestMapping(method = RequestMethod.GET, value = "/city/{city}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<CityResult> searchByCity(@PathVariable String city) throws Exception {
		return searchService.searchCity(city);
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/dept/{dept}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<CityResult> searchByDept(@PathVariable String dept) throws Exception {
		return searchService.searchDept(dept);
		
	}

	@RequestMapping(method = RequestMethod.GET, value = "/geo/{aireGeo}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> searchByAireGeo(@PathVariable String aireGeo) throws Exception {
		return searchService.searchAireGeo(aireGeo);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/ida/{ida}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Set<String>> searchByIda(@PathVariable String ida) throws Exception {
		return searchService.searchAireIda(ida);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/whoami")
	public String whoAmI() {
		return "I am the " + appName.replace("-", " ").toUpperCase() + ", running on port " + serverPort;
	}
}