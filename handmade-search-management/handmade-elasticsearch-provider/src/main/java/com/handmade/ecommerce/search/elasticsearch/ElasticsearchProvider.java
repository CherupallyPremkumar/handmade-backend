package com.handmade.ecommerce.search.elasticsearch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.handmade.ecommerce.search.api.SearchProvider;
import com.handmade.ecommerce.search.api.model.*;
import com.handmade.ecommerce.search.elasticsearch.config.ElasticsearchConfiguration;
import com.handmade.ecommerce.search.elasticsearch.query.QueryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.DeleteIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

/**
 * Elasticsearch implementation of SearchProvider.
 * Handles search, indexing, and index management using Elasticsearch.
 */
@Slf4j
@Component
public class ElasticsearchProvider implements SearchProvider {
    
    @Autowired
    private RestHighLevelClient client;
    
    @Autowired
    private ElasticsearchConfiguration config;
    
    @Autowired
    private QueryBuilder queryBuilder;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Override
    public String getProviderName() {
        return "elasticsearch";
    }
    
    @Override
    public boolean supports(String entityType) {
        // Elasticsearch supports all entity types
        return true;
    }
    
    @Override
    public <T> com.handmade.ecommerce.search.api.model.SearchResponse<T> executeSearch(
            com.handmade.ecommerce.search.api.model.SearchRequest request, Class<T> resultType) {
        
        try {
            String indexName = config.getIndexName(request.getEntityType());
            
            // Build Elasticsearch query
            SearchSourceBuilder sourceBuilder = queryBuilder.buildQuery(request);
            
            // Create search request
            SearchRequest searchRequest = new SearchRequest(indexName);
            searchRequest.source(sourceBuilder);
            
            // Execute search
            long startTime = System.currentTimeMillis();
            SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
            long took = System.currentTimeMillis() - startTime;
            
            // Parse results
            List<T> results = new ArrayList<>();
            for (SearchHit hit : response.getHits().getHits()) {
                T result = objectMapper.convertValue(hit.getSourceAsMap(), resultType);
                results.add(result);
            }
            
            // Parse facets
            Map<String, List<FacetValue>> facets = parseFacets(response.getAggregations());
            
            // Build response
            return com.handmade.ecommerce.search.api.model.SearchResponse.<T>builder()
                .results(results)
                .totalHits(response.getHits().getTotalHits().value)
                .facets(facets)
                .metadata(SearchMetadata.builder()
                    .took(took)
                    .provider(getProviderName())
                    .build())
                .build();
                
        } catch (IOException e) {
            log.error("Error executing search", e);
            throw new RuntimeException("Search failed", e);
        }
    }
    
    @Override
    public void indexDocument(com.handmade.ecommerce.search.api.model.IndexRequest request) {
        try {
            String indexName = config.getIndexName(request.getEntityType());
            
            // Create index request
            IndexRequest indexRequest = new IndexRequest(indexName)
                .id(request.getEntityId())
                .source(objectMapper.writeValueAsString(request.getDocument()), XContentType.JSON);
            
            // Index document
            client.index(indexRequest, RequestOptions.DEFAULT);
            
            log.debug("Indexed document: {}/{}", indexName, request.getEntityId());
            
        } catch (IOException e) {
            log.error("Error indexing document", e);
            throw new RuntimeException("Indexing failed", e);
        }
    }
    
    @Override
    public void bulkIndexDocuments(List<com.handmade.ecommerce.search.api.model.IndexRequest> requests) {
        if (requests == null || requests.isEmpty()) {
            return;
        }
        
        try {
            BulkRequest bulkRequest = new BulkRequest();
            
            for (com.handmade.ecommerce.search.api.model.IndexRequest request : requests) {
                String indexName = config.getIndexName(request.getEntityType());
                
                IndexRequest indexRequest = new IndexRequest(indexName)
                    .id(request.getEntityId())
                    .source(objectMapper.writeValueAsString(request.getDocument()), XContentType.JSON);
                
                bulkRequest.add(indexRequest);
            }
            
            BulkResponse response = client.bulk(bulkRequest, RequestOptions.DEFAULT);
            
            if (response.hasFailures()) {
                log.error("Bulk indexing had failures: {}", response.buildFailureMessage());
            } else {
                log.debug("Bulk indexed {} documents", requests.size());
            }
            
        } catch (IOException e) {
            log.error("Error bulk indexing documents", e);
            throw new RuntimeException("Bulk indexing failed", e);
        }
    }
    
    @Override
    public void deleteDocument(String entityType, String entityId) {
        try {
            String indexName = config.getIndexName(entityType);
            
            DeleteRequest deleteRequest = new DeleteRequest(indexName, entityId);
            client.delete(deleteRequest, RequestOptions.DEFAULT);
            
            log.debug("Deleted document: {}/{}", indexName, entityId);
            
        } catch (IOException e) {
            log.error("Error deleting document", e);
            throw new RuntimeException("Delete failed", e);
        }
    }
    
    @Override
    public void createIndex(String entityType) {
        try {
            String indexName = config.getIndexName(entityType);
            
            // Check if index exists
            GetIndexRequest getIndexRequest = new GetIndexRequest(indexName);
            boolean exists = client.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
            
            if (!exists) {
                CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);
                // TODO: Add index mappings based on entity type
                client.indices().create(createIndexRequest, RequestOptions.DEFAULT);
                log.info("Created index: {}", indexName);
            } else {
                log.debug("Index already exists: {}", indexName);
            }
            
        } catch (IOException e) {
            log.error("Error creating index", e);
            throw new RuntimeException("Index creation failed", e);
        }
    }
    
    @Override
    public void deleteIndex(String entityType) {
        try {
            String indexName = config.getIndexName(entityType);
            
            DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(indexName);
            client.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
            
            log.info("Deleted index: {}", indexName);
            
        } catch (IOException e) {
            log.error("Error deleting index", e);
            throw new RuntimeException("Index deletion failed", e);
        }
    }
    
    /**
     * Parse facets from Elasticsearch aggregations
     */
    private Map<String, List<FacetValue>> parseFacets(Aggregations aggregations) {
        Map<String, List<FacetValue>> facets = new HashMap<>();
        
        if (aggregations == null) {
            return facets;
        }
        
        // TODO: Parse aggregations into facets
        // This will be implemented based on specific facet types
        
        return facets;
    }
}
