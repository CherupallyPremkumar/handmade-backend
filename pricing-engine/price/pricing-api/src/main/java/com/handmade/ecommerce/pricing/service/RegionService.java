package com.handmade.ecommerce.pricing.service;

import com.handmade.ecommerce.pricing.entity.Region;
import com.handmade.ecommerce.pricing.entity.TaxRule;

import java.util.List;

/**
 * Region Service Interface
 * Handles region configuration and tax rules
 */
public interface RegionService {

    /**
     * Get region by code
     * 
     * @param regionCode Region code (e.g., "IN", "US", "EU")
     * @return Region entity
     */
    Region getRegion(String regionCode);

    /**
     * Get tax rule for region and product category
     * 
     * @param regionCode        Region code
     * @param productCategoryId Product category ID (nullable for default)
     * @return Applicable tax rule
     */
    TaxRule getTaxRule(String regionCode, String productCategoryId);

    /**
     * Get all tax rules for a region
     * 
     * @param regionCode Region code
     * @return List of tax rules
     */
    List<TaxRule> getTaxRules(String regionCode);

    /**
     * Get default currency for region
     * 
     * @param regionCode Region code
     * @return Default currency code
     */
    String getDefaultCurrency(String regionCode);

    /**
     * Check if region is supported
     * 
     * @param regionCode Region code
     * @return true if supported
     */
    boolean isRegionSupported(String regionCode);

    /**
     * Get all active regions
     * 
     * @return List of active regions
     */
    List<Region> getActiveRegions();
}
