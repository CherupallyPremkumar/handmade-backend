package com.handmade.ecommerce.offer.service.impl;

import com.handmade.ecommerce.inventory.api.InventoryService;
import com.handmade.ecommerce.inventory.command.CreateInventoryCommand;
import com.handmade.ecommerce.offer.domain.aggregate.Offer;
import com.handmade.ecommerce.offer.domain.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Service to migrate legacy Price and Variant data to the new Offer model.
 * Bridges the gap between hmvariant, hm_price and the new hm_offer table.
 */
@Service
public class OfferMigrationService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private InventoryService inventoryService;

    /**
     * Executes the migration logic.
     * Scans hmvariant and hm_price to create Offer entities.
     */
    @Transactional
    public void migrateToOffers() {
        String sql = "SELECT v.id as variant_id, v.product_id, v.artisan_id as seller_id, " +
                     "p.current_price, p.base_currency " +
                     "FROM hmvariant v " +
                     "JOIN hm_price p ON v.id = p.variant_id " +
                     "WHERE v.artisan_id IS NOT NULL";

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

        for (Map<String, Object> row : rows) {
            String variantId = (String) row.get("variant_id");
            String productId = (String) row.get("product_id");
            String sellerId = (String) row.get("seller_id");
            BigDecimal price = (BigDecimal) row.get("current_price");
            String currency = (String) row.get("base_currency");

            // Check if offer already exists to ensure idempotency
            if (offerRepository.findByVariantId(variantId).stream()
                    .noneMatch(o -> o.getSellerId().equals(sellerId))) {
                
                Offer offer = new Offer();
                offer.setVariantId(variantId);
                offer.setProductId(productId);
                offer.setSellerId(sellerId);
                offer.setRegionId("GLOBAL"); // Default region for legacy data
                offer.setPrice(price);
                offer.setCurrency(currency != null ? currency : "USD");
                offer.setCurrentState("ACTIVE");
                offer.setOfferCode("OFFER-" + variantId + "-" + sellerId);
                
                offerRepository.save(offer);

                // Initialize Inventory
                inventoryService.createInventory(CreateInventoryCommand.builder()
                        .variantId(variantId)
                        .sellerId(sellerId)
                        .quantityAvailable(0)
                        .quantityReserved(0)
                        .isBackOrderAllowed(false)
                        .build());
            }
        }
    }
}
