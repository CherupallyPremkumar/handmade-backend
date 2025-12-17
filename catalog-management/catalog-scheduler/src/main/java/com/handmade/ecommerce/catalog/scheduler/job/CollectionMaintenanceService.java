package com.handmade.ecommerce.catalog.scheduler.job;

import com.handmade.ecommerce.catalog.model.Collection;
import com.handmade.ecommerce.catalog.model.CollectionProductMapping;
import com.handmade.ecommerce.catalog.repository.CollectionProductMappingRepository;
import com.handmade.ecommerce.catalog.repository.CollectionRepository;
import com.handmade.ecommerce.catalog.service.integration.ExternalProductDto;
import com.handmade.ecommerce.catalog.service.integration.ProductServiceClient;
import com.handmade.ecommerce.catalog.service.rule.DynamicRuleEvaluator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * The Scheduler: Background maintenance for Catalog
 * Runs periodic jobs to keep dynamic collections fresh.
 * 
 * Migrated to catalog-scheduler module for independent scaling.
 * @deprecated Replaced by Spring Batch + Quartz implementation in 'config' package.
 */
@Service
@Deprecated
public class CollectionMaintenanceService {

    private static final Logger log = LoggerFactory.getLogger(CollectionMaintenanceService.class);

    @Autowired
    private CollectionRepository collectionRepository;

    @Autowired
    private CollectionProductMappingRepository mappingRepository;

    @Autowired
    private ProductServiceClient productServiceClient;

    @Autowired
    private DynamicRuleEvaluator ruleEvaluator;

    /**
     * Refresh all dynamic collections.
     * Running every hour (cron expression or fixedDelay).
     * For demo purposes, we'll use a fixedDelay of 1 hour (3600000 ms).
     */
    // @Scheduled(fixedDelay = 3600000)
    @Transactional
    public void refreshDynamicCollections() {
        log.info("Starting Dynamic Collection Refresh Job...");
        
        List<Collection> dynamicCollections = collectionRepository.findAllActiveDynamicCollections();
        if (dynamicCollections.isEmpty()) {
            log.info("No active dynamic collections found. Job finished.");
            return;
        }

        // 1. Fetch Candidates (In real world: Use Batch Processing / Streaming)
        List<ExternalProductDto> allProducts = productServiceClient.getAllProducts();
        log.info("Fetched {} products for evaluation.", allProducts.size());

        for (Collection collection : dynamicCollections) {
            refreshCollection(collection, allProducts);
        }
        
        log.info("Dynamic Collection Refresh Job Completed.");
    }

    private void refreshCollection(Collection collection, List<ExternalProductDto> products) {
        log.info("Refreshing collection: {} (Rule: {})", collection.getName(), collection.getRuleExpression());
        
        // 2. Clear existing mappings (Simple Strategy: Wipe and Rebuild)
        // Optimization: Use diffing in V2
        mappingRepository.deleteByCollectionId(collection.getCollectionId());

        int addedCount = 0;
        for (ExternalProductDto product : products) {
            if (ruleEvaluator.matches(collection.getRuleExpression(), product)) {
                createMapping(collection, product, addedCount++);
            }
        }
        log.info("Collection {} updated. Added {} products.", collection.getName(), addedCount);
    }

    private void createMapping(Collection collection, ExternalProductDto product, int order) {
        CollectionProductMapping mapping = new CollectionProductMapping();
        mapping.setCollectionId(collection.getCollectionId());
        mapping.setProductId(product.getId());
        mapping.setDisplayOrder(order);
        mapping.setAddedBy("SYSTEM_JOB");
        mappingRepository.save(mapping);
    }
}
