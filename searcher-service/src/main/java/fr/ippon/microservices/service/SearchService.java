package fr.ippon.microservices.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.ippon.microservices.model.CityResult;

@Service
public class SearchService {

	@Inject
	private Client esClient;

	private final ObjectMapper objectMapper = new ObjectMapper();

	public List<CityResult> searchCity(String city) throws Exception {

		SearchResponse response = esClient.prepareSearch("aoc_aop").setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(QueryBuilders.matchQuery("commune", city)).execute().actionGet();

		List<CityResult> cityResults = new ArrayList<>();
		if (response != null) {
			for (SearchHit hit : response.getHits().getHits()) {
				CityResult cityResult = objectMapper.readValue(hit.getSourceAsString(), CityResult.class);
				cityResults.add(cityResult);
			}
		}
		return cityResults;
	}

	public List<CityResult> searchDept(String dept) throws Exception {

		SearchResponse response = esClient.prepareSearch("aoc_aop").setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(QueryBuilders.matchQuery("departement", dept)).execute().actionGet();

		List<CityResult> cityResults = new ArrayList<>();
		if (response != null) {
			for (SearchHit hit : response.getHits().getHits()) {
				CityResult cityResult = objectMapper.readValue(hit.getSourceAsString(), CityResult.class);
				cityResults.add(cityResult);
			}
		}
		return cityResults;
	}

	public List<String> searchAireGeo(String aireGeo) {
		SearchResponse response = esClient.prepareSearch("aoc_aop").setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(QueryBuilders.matchPhraseQuery("appellations.aireGeo", aireGeo)).execute().actionGet();

		List<String> communes = new ArrayList<String>();
		for (SearchHit hit : response.getHits().getHits()) {
			communes.add(hit.getSource().get("commune") + " - (" + hit.getSource().get("departement") + ")");
		}
		return communes;

	}

	public Map<String, Set<String>> searchAireIda(String searchedIda) throws Exception {
		SearchResponse response = esClient.prepareSearch("aoc_aop").setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(QueryBuilders.matchPhraseQuery("appellations.ida", searchedIda)).setSize(50).execute().actionGet();

		Map<String, Set<String>> result = new HashMap<>();
		for (SearchHit hit : response.getHits().getHits()) {
			List<Map<String,Object>> appellations = (List) hit.getSource().get("appellations");
			for (Map<String,Object> appellation : appellations) {

				String currentIda = appellation.get("ida").toString();

				if (!result.containsKey(currentIda)) {
					result.put(currentIda, new HashSet<>());
				}
				result.get(currentIda).add(hit.getSource().get("commune") + " - (" + hit.getSource().get("departement") + ")");
			}
		}
		return result;
	}

}
