package com.handmade.ecommerce.catalog.service.bootstrap;

import com.handmade.ecommerce.catalog.model.Collection;
import com.handmade.ecommerce.catalog.model.CollectionType;
import com.handmade.ecommerce.catalog.repository.CollectionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Bootstraps the database with example Dynamic Collections.
 * Demonstrates the power of "DB-driven Rules".
 */
@Component
public class CatalogDataSeeder implements CommandLineRunner {

    private final CollectionRepository collectionRepository;

    public CatalogDataSeeder(CollectionRepository collectionRepository) {
        this.collectionRepository = collectionRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (collectionRepository.count() > 0) {
            return; // Data already exists
        }

        System.out.println("SEEDING CATALOG DATA...");

        // 1. "Under $50" - Dynamic Rule: Price Check
        createCollection(
                "Under $50",
                "under-50",
                CollectionType.DYNAMIC,
                "price < 50.00");

        // 2. "Summer Vibes" - Dynamic Rule: Name Check
        createCollection(
                "Summer Vibes",
                "summer-vibes",
                CollectionType.DYNAMIC,
                "name.contains('Summer') or name.contains('Sun')");

        // 3. "Premium Selection" - Dynamic Rule: Price + Active
        createCollection(
                "Premium Selection",
                "premium-selection",
                CollectionType.DYNAMIC,
                "price > 100.00 and active == true");

        System.out.println("CATALOG DATA SEEDING COMPLETE.");
    }

    private void createCollection(String name, String slug, CollectionType type, String rule) {
        Collection c = new Collection();
        c.setName(name);
        c.setSlug(slug);
        c.setType(type);
        c.setRuleExpression(rule);
        c.setAutoUpdate(true);
        c.setActive(true);
        c.setProductCount(0L);
        collectionRepository.save(c);
    }
}
