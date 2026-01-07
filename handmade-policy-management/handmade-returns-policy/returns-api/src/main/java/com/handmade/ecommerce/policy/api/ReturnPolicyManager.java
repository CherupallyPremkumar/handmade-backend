package com.handmade.ecommerce.policy.api;

import com.handmade.ecommerce.policy.domain.aggregate.ReturnPolicy;
import com.handmade.ecommerce.policy.domain.valueobject.ReturnDecision;
import org.chenile.workflow.api.StateEntityService;
import java.time.LocalDate;

public interface ReturnPolicyManager extends StateEntityService<ReturnPolicy> {
    ReturnPolicy resolveActivePolicy(String country, String category, LocalDate date);
    ReturnDecision validateReturnRequest(String country, String category, String orderId, String reason);
}
