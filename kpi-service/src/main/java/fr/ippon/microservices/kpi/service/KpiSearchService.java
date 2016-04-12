package fr.ippon.microservices.kpi.service;

import javax.inject.Inject;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.springframework.stereotype.Service;

import fr.ippon.microservices.kpi.settings.ESSettings;

@Service
public class KpiSearchService {

	@Inject
	private Client esClient;

	@Inject
	private ESSettings esSettings;

	public long getNbImport() throws Exception {

		SearchResponse response = esClient.prepareSearch(esSettings.getIndex()).setSize(0).execute().actionGet();

		return response.getHits().getTotalHits();
	}

	public long nbCommunes(String departement) throws Exception {

		SearchResponse sr = esClient.prepareSearch(esSettings.getIndex())
				.setQuery(QueryBuilders.termQuery("departement", departement.toLowerCase()))
				.addAggregation(AggregationBuilders.terms("aggCommune").field("commune"))
				// .addAggregation(
				// AggregationBuilders.dateHistogram("agg2")
				// .field("birth")
				// .interval(DateHistogramInterval.YEAR)
				// )
				.execute().actionGet();
		Terms agg1 = sr.getAggregations().get("aggCommune");

		for (Bucket bucket : agg1.getBuckets()) {
			System.out.println(bucket.getKey() + " : " + bucket.getDocCount());

		}
		return sr.getHits().getTotalHits();
	}
}
