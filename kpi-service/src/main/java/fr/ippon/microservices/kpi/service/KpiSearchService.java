package fr.ippon.microservices.kpi.service;

import javax.inject.Inject;

import fr.ippon.microservices.kpi.settings.ESSettings;
import org.elasticsearch.action.count.CountResponse;
import org.elasticsearch.client.Client;
import org.springframework.stereotype.Service;

@Service
public class KpiSearchService {

	@Inject
	private Client esClient;

	@Inject
	private ESSettings esSettings;

	public long getNbImport() throws Exception {

		CountResponse response = esClient.prepareCount(esSettings.getIndex())
				.execute()
				.actionGet();

		return response.getCount();
	}
}
