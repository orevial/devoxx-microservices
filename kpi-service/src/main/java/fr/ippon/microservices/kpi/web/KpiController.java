package fr.ippon.microservices.kpi.web;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.ippon.microservices.kpi.service.KpiLoaderService;
import fr.ippon.microservices.kpi.service.KpiSearchService;

@RestController
public class KpiController {

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
}
