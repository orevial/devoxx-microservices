package fr.ippon.microservices.kpi.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import fr.ippon.microservices.kpi.service.feign.Loader;

@Service
public class KpiLoaderService {

	@Inject
	private Loader loader;

	public Integer getNbImport() throws Exception {
		return loader.getNbIndexedDocuments();
	}

}
