package com.handmade.ecommerce.search.elasticsearch.query;

import com.handmade.ecommerce.search.api.model.Pagination;
import com.handmade.ecommerce.search.api.model.SearchRequest;
import com.handmade.ecommerce.search.api.model.SortCriteria;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Builds Elasticsearch queries from search requests.
 * Handles query construction, filters, facets, pagination, and sorting.
 */
@Slf4j
@Component
public class QueryBuilder {
    
    /**
     * Build Elasticsearch query from search request
     */
    public SearchSourceBuilder buildQuery(SearchRequest request) {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        
        // Build query
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        
        // Add main query
        if (request.getQuery() != null && !request.getQuery().isEmpty()) {
            boolQuery.must(QueryBuilders.multiMatchQuery(request.getQuery())
                .field("name", 2.0f)  // Boost name field
                .field("description")
                .field("tags")
                .fuzziness("AUTO"));
        } else {
            boolQuery.must(QueryBuilders.matchAllQuery());
        }
        
        // Add filters
        if (request.getFilters() != null && !request.getFilters().isEmpty()) {
            addFilters(boolQuery, request.getFilters());
        }
        
        sourceBuilder.query(boolQuery);
        
        // Add pagination
        if (request.getPagination() != null) {
            addPagination(sourceBuilder, request.getPagination());
        }
        
        // Add sorting
        if (request.getSort() != null) {
            addSorting(sourceBuilder, request.getSort());
        }
        
        // Add facets/aggregations
        if (request.getFacets() != null && !request.getFacets().isEmpty()) {
            addFacets(sourceBuilder, request.getFacets());
        }
        
        return sourceBuilder;
    }
    
    /**
     * Add filters to query
     */
    private void addFilters(BoolQueryBuilder boolQuery, Map<String, Object> filters) {
        for (Map.Entry<String, Object> entry : filters.entrySet()) {
            String field = entry.getKey();
            Object value = entry.getValue();
            
            if (value instanceof Map) {
                // Range filter (e.g., price range)
                @SuppressWarnings("unchecked")
                Map<String, Object> range = (Map<String, Object>) value;
                
                var rangeQuery = QueryBuilders.rangeQuery(field);
                if (range.containsKey("min")) {
                    rangeQuery.gte(range.get("min"));
                }
                if (range.containsKey("max")) {
                    rangeQuery.lte(range.get("max"));
                }
                boolQuery.filter(rangeQuery);
                
            } else if (value instanceof java.util.List) {
                // Terms filter (multiple values)
                boolQuery.filter(QueryBuilders.termsQuery(field, (java.util.List<?>) value));
                
            } else {
                // Term filter (single value)
                boolQuery.filter(QueryBuilders.termQuery(field, value));
            }
        }
    }
    
    /**
     * Add pagination
     */
    private void addPagination(SearchSourceBuilder sourceBuilder, Pagination pagination) {
        sourceBuilder.from(pagination.getOffset());
        sourceBuilder.size(pagination.getSize());
    }
    
    /**
     * Add sorting
     */
    private void addSorting(SearchSourceBuilder sourceBuilder, SortCriteria sort) {
        if ("relevance".equals(sort.getField())) {
            // Sort by score (relevance)
            sourceBuilder.sort("_score", 
                sort.getOrder() == SortCriteria.SortOrder.DESC ? SortOrder.DESC : SortOrder.ASC);
        } else {
            // Sort by field
            sourceBuilder.sort(sort.getField(), 
                sort.getOrder() == SortCriteria.SortOrder.DESC ? SortOrder.DESC : SortOrder.ASC);
        }
    }
    
    /**
     * Add facets (aggregations)
     */
    private void addFacets(SearchSourceBuilder sourceBuilder, java.util.List<String> facets) {
        for (String facet : facets) {
            // Add terms aggregation for each facet
            sourceBuilder.aggregation(
                AggregationBuilders.terms(facet + "_facet")
                    .field(facet)
                    .size(100)  // Max 100 facet values
            );
        }
    }
}
