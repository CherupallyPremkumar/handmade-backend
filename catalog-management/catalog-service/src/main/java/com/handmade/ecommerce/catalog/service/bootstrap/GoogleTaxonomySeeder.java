package com.handmade.ecommerce.catalog.service.bootstrap;

import com.handmade.ecommerce.catalog.model.Category;
import com.handmade.ecommerce.catalog.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Bootstraps categories from Google Product Taxonomy
 * https://www.google.com/basepages/producttype/taxonomy-with-ids.en-US.txt
 */
@Component
@Order(1) // Run before CatalogDataSeeder
public class GoogleTaxonomySeeder implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(GoogleTaxonomySeeder.class);
    private static final String GOOGLE_TAXONOMY_URL = "https://www.google.com/basepages/producttype/taxonomy-with-ids.en-US.txt";

    private final CategoryRepository categoryRepository;
    private final Map<String, String> categoryIdMap = new HashMap<>(); // name -> id

    public GoogleTaxonomySeeder(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (categoryRepository.count() > 0) {
            log.info("Categories already exist. Skipping Google Taxonomy import.");
            return;
        }

        log.info("Importing categories from Google Product Taxonomy...");

        try {
            URL url = new URL(GOOGLE_TAXONOMY_URL);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

            String line;
            int count = 0;
            int limit = 100; // Import first 100 categories for demo (full taxonomy has 6000+)

            while ((line = reader.readLine()) != null && count < limit) {
                if (line.trim().isEmpty() || line.startsWith("#")) {
                    continue; // Skip comments and empty lines
                }

                parseAndCreateCategory(line);
                count++;
            }

            reader.close();
            log.info("Successfully imported {} categories from Google Taxonomy", count);

        } catch (Exception e) {
            log.error("Failed to import Google Taxonomy. Using fallback categories.", e);
            seedFallbackCategories();
        }
    }

    private void parseAndCreateCategory(String line) {
        // Format: "123 - Apparel & Accessories > Clothing > Dresses"
        // or just: "Apparel & Accessories > Clothing > Dresses"

        String categoryPath = line;

        // Remove ID prefix if present (e.g., "123 - ")
        if (line.matches("^\\d+\\s*-\\s*.*")) {
            categoryPath = line.replaceFirst("^\\d+\\s*-\\s*", "");
        }

        // Split by " > " to get hierarchy
        String[] parts = categoryPath.split("\\s*>\\s*");

        String parentId = null;
        int level = 0;

        for (String categoryName : parts) {
            categoryName = categoryName.trim();

            // Check if category already exists
            if (categoryIdMap.containsKey(categoryName)) {
                parentId = categoryIdMap.get(categoryName);
                level++;
                continue;
            }

            // Create new category
            Category category = new Category();
            category.setName(categoryName);
            category.setSlug(generateSlug(categoryName));
            category.setParentId(parentId);
            category.setLevel(level);
            category.setActive(true);
            category.setFeatured(level == 0); // Feature root categories
            category.setDisplayOrder(level * 100);

            Category saved = categoryRepository.save(category);
            categoryIdMap.put(categoryName, saved.getId());

            parentId = saved.getId();
            level++;
        }
    }

    private String generateSlug(String name) {
        return name.toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("\\s+", "-")
                .replaceAll("-+", "-")
                .replaceAll("^-|-$", "");
    }

    private void seedFallbackCategories() {
        log.info("Seeding fallback handmade categories...");

        // Root categories
        String arts = createRootCategory("Arts & Crafts", "arts-crafts");
        String jewelry = createRootCategory("Jewelry & Accessories", "jewelry-accessories");
        String home = createRootCategory("Home & Living", "home-living");
        String clothing = createRootCategory("Clothing & Shoes", "clothing-shoes");

        // Arts & Crafts children
        createChildCategory("Paintings", "paintings", arts, 1);
        createChildCategory("Sculptures", "sculptures", arts, 1);
        createChildCategory("Pottery & Ceramics", "pottery-ceramics", arts, 1);

        // Jewelry children
        createChildCategory("Necklaces", "necklaces", jewelry, 1);
        createChildCategory("Bracelets", "bracelets", jewelry, 1);
        createChildCategory("Earrings", "earrings", jewelry, 1);

        // Home & Living children
        createChildCategory("Furniture", "furniture", home, 1);
        createChildCategory("Decor", "decor", home, 1);
        createChildCategory("Lighting", "lighting", home, 1);

        log.info("Fallback categories seeded successfully");
    }

    private String createRootCategory(String name, String slug) {
        Category category = new Category();
        category.setName(name);
        category.setSlug(slug);
        category.setLevel(0);
        category.setActive(true);
        category.setFeatured(true);
        category.setDisplayOrder(0);

        Category saved = categoryRepository.save(category);
        categoryIdMap.put(name, saved.getId());
        return saved.getId();
    }

    private void createChildCategory(String name, String slug, String parentId, int level) {
        Category category = new Category();
        category.setName(name);
        category.setSlug(slug);
        category.setParentId(parentId);
        category.setLevel(level);
        category.setActive(true);
        category.setFeatured(false);
        category.setDisplayOrder(level * 100);

        Category saved = categoryRepository.save(category);
        categoryIdMap.put(name, saved.getId());
    }
}
