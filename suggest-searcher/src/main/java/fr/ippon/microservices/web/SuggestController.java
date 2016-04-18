package fr.ippon.microservices.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.ippon.microservices.service.SuggestService;

@RestController
public class SuggestController {

	@Value("${spring.application.name:}")
	private String appName;

	@Value("${server.port:-1}")
	private int serverPort;

	@Autowired
	private SuggestService suggestService;
	
	@RequestMapping(method = RequestMethod.GET, value = "/city/{city}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> searchByCity(@PathVariable String city) throws Exception {
		return suggestService.searchCity(city);
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/dept/{dept}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> searchByDept(@PathVariable String dept) throws Exception {
		return suggestService.searchDept(dept);
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/whoami")
	public String whoAmI() {
		return "I am the " + appName.replace("-", " ").toUpperCase() + ", running on port " + serverPort;
	}

}
