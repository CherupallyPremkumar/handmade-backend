package com.handmade.ecommerce.platform.api;

import com.handmade.ecommerce.platform.domain.aggregate.Marketplace;
import org.chenile.workflow.api.StateEntityService;

/**
 * Marketplace Manager - State Entity Service
 * Manages marketplace lifecycle using Chenile State Machine
 * 
 * Follows same pattern as PlatformManager
 */
public interface MarketplaceManager extends StateEntityService<Marketplace> {
    // State machine operations inherited from StateEntityService:
    // - retrieve(String id)
    // - create(Marketplace entity)
    // - store(Marketplace entity)
    // - processById(String id, ChenileExchange exchange)
}
