package fr.ippon.microservices.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.elasticsearch.action.suggest.SuggestRequestBuilder;
import org.elasticsearch.action.suggest.SuggestResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;
import org.springframework.stereotype.Service;

@Service
public class SuggestService {

	@Inject
	private Client esClient;

	public List<String> searchDept(String dept) {
		return performSuggest("departement", dept);
	}

	public List<String> searchCity(String city) {
		return performSuggest("commune", city);
	}

	private List<String> performSuggest(String field, String city) {
		CompletionSuggestionBuilder suggestionsBuilder = new CompletionSuggestionBuilder(field + "Suggest");
		suggestionsBuilder.text(city);
		suggestionsBuilder.field(field);
		SuggestRequestBuilder suggestRequestBuilder = esClient.prepareSuggest("aoc_aop")
				.addSuggestion(suggestionsBuilder);

		SuggestResponse suggestResponse = suggestRequestBuilder.execute().actionGet();

		Iterator<? extends Suggest.Suggestion.Entry.Option> iterator = suggestResponse.getSuggest()
				.getSuggestion(field + "Suggest").iterator().next().getOptions().iterator();

		List<String> items = new ArrayList<>();
		while (iterator.hasNext()) {
			Suggest.Suggestion.Entry.Option next = iterator.next();
			items.add(next.getText().string());
		}
		return items;
	}

}
