package com.homebase.ecom.service;

import com.homebase.ecom.constants.CacheConstants;
import com.homebase.ecom.constants.HeaderConstants;
import com.homebase.ecom.constants.QueryConstants;
import com.homebase.ecom.mybatis.customer.CustomerExists;
import org.chenile.query.model.ResponseRow;
import org.chenile.query.model.SearchRequest;
import org.chenile.query.model.SearchResponse;
import org.chenile.query.service.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Service for customer validation with caching support.
 * <p>
 * This service is responsible for validating customer existence in the
 * database.
 * Results are cached to improve performance and reduce database load.
 * <p>
 * Extracted from PopulateGuestOrCustomerInterceptor to enable proper @Cacheable
 * functionality
 * (Spring AOP requires external method calls for proxy-based caching).
 *
 * @see com.homebase.ecom.interceptors.PopulateGuestOrCustomerInterceptor
 */
@Service
public class CustomerValidationService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerValidationService.class);

    private final SearchService<Map<String, Object>> searchService;

    public CustomerValidationService(SearchService<Map<String, Object>> searchService) {
        this.searchService = Objects.requireNonNull(searchService, "SearchService cannot be null");
    }

    /**
     * Validates if a customer exists in the database.
     * <p>
     * Results are cached to avoid repeated database queries.
     * Cache key is based on customerId for efficient lookups.
     * <p>
     * IMPORTANT: Always returns a CustomerExists object (never null).
     * Check the isExists() method to determine if customer was found.
     *
     * @param userInfo Map containing customerId and email (required keys)
     * @return CustomerExists object with exists flag set appropriately
     * @throws IllegalArgumentException if userInfo is null or missing required
     *                                  fields
     */
    @Cacheable(
            value = "customerExistsCache",
            key = "#userInfo.get('x-customer-id')",
            unless = "#result == null || !#result.exists"  // only cache if exists = true
    )
    public CustomerExists validateCustomer(Map<String, Object> userInfo) {
        // Validate input
        validateUserInfo(userInfo);

        String customerId = (String) userInfo.get(HeaderConstants.KEY_CUSTOMER_ID);
        String email = (String) userInfo.get(HeaderConstants.KEY_EMAIL);

        logger.debug("Validating customer existence: customerId={}, email={}", customerId, email);

        try {
            // Build search request
            SearchRequest<Map<String, Object>> searchRequest = buildSearchRequest(customerId, email);

            // Execute search
            SearchResponse searchResponse = searchService.search(searchRequest);

            // Process response
            return processSearchResponse(searchResponse, customerId);

        } catch (Exception e) {
            logger.error("Error validating customer: customerId={}, error={}", customerId, e.getMessage(), e);
            throw new CustomerValidationException("Failed to validate customer: " + customerId, e);
        }
    }

    /**
     * Evicts customer from cache.
     * Useful when customer data is updated or deleted.
     *
     * @param customerId Customer ID to evict from cache
     */
    @CacheEvict(value = CacheConstants.CUSTOMER_EXISTS_CACHE, key = "#customerId")
    public void evictCustomerCache(String customerId) {
        logger.info("Evicting customer from cache: {}", customerId);
    }

    /**
     * Clears entire customer cache.
     * Use with caution - only for administrative purposes.
     */
    @CacheEvict(value = CacheConstants.CUSTOMER_EXISTS_CACHE, allEntries = true)
    public void clearCustomerCache() {
        logger.warn("Clearing entire customer cache");
    }

    /**
     * Validates that userInfo map contains required fields.
     *
     * @param userInfo User information map
     * @throws IllegalArgumentException if validation fails
     */
    private void validateUserInfo(Map<String, Object> userInfo) {
        if (userInfo == null || userInfo.isEmpty()) {
            throw new IllegalArgumentException("UserInfo cannot be null or empty");
        }

        String customerId = (String) userInfo.get(HeaderConstants.KEY_CUSTOMER_ID);
        if (!StringUtils.hasText(customerId)) {
            throw new IllegalArgumentException("CustomerId is required in userInfo");
        }

        String email = (String) userInfo.get(HeaderConstants.KEY_EMAIL);
        if (!StringUtils.hasText(email)) {
            throw new IllegalArgumentException("Email is required in userInfo");
        }
    }

    /**
     * Builds search request for customer validation.
     *
     * @param customerId Customer ID
     * @param email      Customer email
     * @return Configured SearchRequest
     */
    private SearchRequest<Map<String, Object>> buildSearchRequest(String customerId, String email) {
        SearchRequest<Map<String, Object>> searchRequest = new SearchRequest<>();

        Map<String, Object> filters = new HashMap<>();
        filters.put(QueryConstants.FILTER_CUSTOMER_ID, customerId);
        filters.put(QueryConstants.FILTER_EMAIL, email);

        searchRequest.setFilters(filters);
        searchRequest.setQueryName(QueryConstants.QUERY_EXISTS_CUSTOMER);

        return searchRequest;
    }

    /**
     * Processes search response and extracts customer data.
     *
     * @param searchResponse Search response from database
     * @param customerId     Customer ID for logging
     * @return CustomerExists object or null if not found
     */
    private CustomerExists processSearchResponse(SearchResponse searchResponse, String customerId) {
        if (searchResponse == null || searchResponse.getList() == null || searchResponse.getList().isEmpty()) {
            logger.info("Customer not found in database: {}", customerId);
            return null;
        }

        ResponseRow row = searchResponse.getList().get(0);
        if (row == null || row.getRow() == null) {
            logger.warn("Invalid search response for customer: {}", customerId);
            return null;
        }

        CustomerExists customer = (CustomerExists) row.getRow();
        logger.debug("Customer found and cached: {}", customerId);
        return customer;
    }

    /**
     * Custom exception for customer validation errors.
     */
    public static class CustomerValidationException extends RuntimeException {
        public CustomerValidationException(String message) {
            super(message);
        }

        public CustomerValidationException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
