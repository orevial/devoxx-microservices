package fr.ippon.microservices.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import fr.ippon.microservices.model.ESAppellationDocument;
import fr.ippon.microservices.model.ESCommuneDocument;
import fr.ippon.microservices.model.MongoAireProduit;
import fr.ippon.microservices.model.MongoCommuneAire;
import fr.ippon.microservices.settings.MongoSettings;

import org.bson.Document;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.IOException;

@Service
public class ImporterService {
	private static final Logger logger = LoggerFactory.getLogger(ImporterService.class);

	public int nbIndexedDocuments = 0;

	private ObjectMapper mapper = new ObjectMapper();

	@Inject
	private Client esClient;

	@Inject
	private MongoSettings mongoSettings;

	private BulkProcessor bulkProcessor;

	public void loadData() throws IOException {
		MongoClientURI uri = new MongoClientURI(mongoSettings.getUri());
		MongoClient mongoClient = new MongoClient(uri);

		try {
			MongoDatabase db = mongoClient.getDatabase("aoc_aop");
			MongoCollection<Document> communesAiresCollection = db.getCollection("communes_aires");
			MongoCollection<Document> airesProduitsCollection = db.getCollection("aires_produits");

			logger.info(" communesAiresCollection - Nb elements to load : " + communesAiresCollection.count());

			BasicDBObject sort = new BasicDBObject("ci", 1);
			FindIterable<Document> communesCursor = communesAiresCollection.find().sort(sort);
			// nbIndexedDocuments = 0;
			initializeBulkProcessor();

			ESCommuneDocument previousCommune = null;
			ESCommuneDocument currentCommune = null;
			boolean commitPreviousCommune = false;
			for (Document commune : communesCursor) {

				MongoCommuneAire currentMongoCommuneAire = mapper.readValue(commune.toJson(), MongoCommuneAire.class);

				if (currentCommune == null || !previousCommune.getCi().equals(currentMongoCommuneAire.getCi())) {
					currentCommune = new ESCommuneDocument(currentMongoCommuneAire.getArt(),
							currentMongoCommuneAire.getCi(), currentMongoCommuneAire.getDepartement(),
							currentMongoCommuneAire.getCommune());
					if (previousCommune != null) {
						commitPreviousCommune = true;
					}
				}

				try {
					// Fetch all products from other Mongo collection
					BasicDBObject productsSort = new BasicDBObject("produit", 1);
					BasicDBObject productsQuery = new BasicDBObject("ida",
							Integer.valueOf(currentMongoCommuneAire.getIda()));
					FindIterable<Document> productsCursor = airesProduitsCollection.find(productsQuery).sort(productsSort);

					for (Document product : productsCursor) {
						MongoAireProduit produit = mapper.readValue(product.toJson(), MongoAireProduit.class);
						currentCommune.addAppellations(new ESAppellationDocument(produit.getIda(), produit.getAireGeo(),
								produit.getProduit()));
					}
				} catch (NumberFormatException e) {
					logger.info("Number format exception for : {}", currentMongoCommuneAire.getIda());
				} catch (IOException e) {
					logger.info("IO exception... {}");
				}

				if (commitPreviousCommune) {
					commitPreviousCommune = false;
					bulkProcessor.add(getIndexRequest(previousCommune));
				}
				previousCommune = currentCommune;
			}
			bulkProcessor.flush();
			bulkProcessor.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mongoClient.close();
			esClient.close();
		}
	}

	private IndexRequest getIndexRequest(ESCommuneDocument esCommune) {
		try {
			return new IndexRequest("aoc_aop", "commune", esCommune.getCi())
					.source(mapper.writeValueAsString(esCommune));
		} catch (JsonProcessingException e) {
			logger.error("Error while processing commune with id {}", esCommune.getCi());
		}
		return null;
	}

	private void initializeBulkProcessor() {
		bulkProcessor = BulkProcessor.builder(esClient, new BulkProcessor.Listener() {
			@Override
			public void beforeBulk(long executionId, BulkRequest request) {
				logger.info("Before bulk {} ", executionId);
			}

			@Override
			public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {

				int newlyIndexedDocuments = response.getItems().length;
				nbIndexedDocuments += newlyIndexedDocuments;
				logger.info("*** TOTAL INDEXED : {} ***", nbIndexedDocuments);
			}

			@Override
			public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
				logger.info("After bulk with failure...", failure);
			}
		}).setBulkActions(50).setConcurrentRequests(1).build();
	}

	public int getNbIndexedDocuments() {
		return nbIndexedDocuments;
	}
}
