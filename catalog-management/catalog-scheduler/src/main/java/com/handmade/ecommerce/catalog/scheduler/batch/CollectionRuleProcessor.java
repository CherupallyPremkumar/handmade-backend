package com.handmade.ecommerce.catalog.scheduler.batch;

import com.handmade.ecommerce.catalog.model.Collection;
import com.handmade.ecommerce.catalog.model.CollectionProductMapping;
import com.handmade.ecommerce.catalog.repository.CollectionRepository;
import com.handmade.ecommerce.catalog.service.integration.ExternalProductDto;
import com.handmade.ecommerce.catalog.service.rule.DynamicRuleEvaluator;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Processor: Takes a Product, checks it against ALL active collections,
 * and returns the list of mappings to create.
 */
@Component
public class CollectionRuleProcessor implements ItemProcessor<ExternalProductDto, List<CollectionProductMapping>> {

    private final CollectionRepository collectionRepository;
    private final DynamicRuleEvaluator ruleEvaluator;
    
    // Cache active collections to avoid DB hit per product
    // In production: use a proper CacheManager or StepExecutionListener to load once per step
    private List<Collection> cachedCollections;

    @Autowired
    public CollectionRuleProcessor(CollectionRepository collectionRepository, DynamicRuleEvaluator ruleEvaluator) {
        this.collectionRepository = collectionRepository;
        this.ruleEvaluator = ruleEvaluator;
    }

    @Override
    public List<CollectionProductMapping> process(ExternalProductDto product) throws Exception {
        if (cachedCollections == null) {
            cachedCollections = collectionRepository.findAllActiveDynamicCollections();
        }

        List<CollectionProductMapping> mappings = new ArrayList<>();
        for (Collection collection : cachedCollections) {
            if (ruleEvaluator.matches(collection.getRuleExpression(), product)) {
                CollectionProductMapping mapping = new CollectionProductMapping();
                mapping.setCollectionId(collection.getCollectionId());
                mapping.setProductId(product.getId());
                mapping.setAddedBy("BATCH_JOB");
                // mapping.setDisplayOrder(...) - could calculation logic be here
                mappings.add(mapping);
            }
        }
        return mappings;
    }
}
