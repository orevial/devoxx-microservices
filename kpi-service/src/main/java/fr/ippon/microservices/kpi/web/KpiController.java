package fr.ippon.microservices.kpi.web;

import fr.ippon.microservices.kpi.service.KpiLoaderService;
import fr.ippon.microservices.kpi.service.KpiSearchService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
public class KpiController {

	@Value("${spring.application.name:}")
	private String appName;

	@Value("${server.port:-1}")
	private int serverPort;

	@Inject
	private KpiLoaderService kpiLoaderService;

	@Inject
	private KpiSearchService kpiSearchService;

	@RequestMapping(method = RequestMethod.GET, value = "/loader/nbImport")
	public Integer getNbImportedData() {
		try {
			return kpiLoaderService.getNbImport();
		} catch (Exception e) {
			throw new RuntimeException("Unable to get KPI: " + e.getMessage(), e);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/nbIndexedDocuments")
	public long getNbIndexedDocuments() throws Exception {
		return kpiSearchService.getNbImport();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/stats/{dept}/nbCommunes")
	public long getStatsNbCommunes(@PathVariable String dept) throws Exception {
		return kpiSearchService.nbCommunes(dept);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/whoami")
	public String whoAmI() {
		return "I am the " + appName.replace("-", " ").toUpperCase() + ", running on port " + serverPort;
	}
}
