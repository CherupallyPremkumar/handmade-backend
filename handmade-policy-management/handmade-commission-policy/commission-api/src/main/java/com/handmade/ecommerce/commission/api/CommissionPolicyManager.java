package com.handmade.ecommerce.commission.api;

import com.handmade.ecommerce.commission.domain.aggregate.CommissionPolicy;
import com.handmade.ecommerce.commission.dto.*;
import org.chenile.workflow.api.StateEntityService;

/**
 * Service to manage Commission Policies and Rules
 */
public interface CommissionPolicyManager extends StateEntityService<CommissionPolicy> {
    // Additional methods for rule management can be defined here if needed
    // Typically inherited from EntityStateService for STP support
}
