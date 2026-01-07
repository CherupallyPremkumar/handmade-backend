package com.handmade.ecommerce.policy.api;

import com.handmade.ecommerce.policy.domain.aggregate.InventoryPolicy;
import com.handmade.ecommerce.policy.domain.valueobject.InventoryDecision;
import org.chenile.workflow.api.StateEntityService;
import java.time.LocalDate;

public interface InventoryPolicyManager extends StateEntityService<InventoryPolicy> {
    InventoryPolicy resolveActivePolicy(String country, String category, LocalDate date);
    InventoryDecision validateStockUpdate(String country, String category, String productId, Integer quantity);
}
