package com.handmade.ecommerce.policy.api;

import com.handmade.ecommerce.policy.domain.aggregate.ListingPolicy;
import com.handmade.ecommerce.policy.domain.valueobject.ListingDecision;
import org.chenile.workflow.api.StateEntityService;
import java.time.LocalDate;
import java.util.List;

public interface ListingPolicyManager extends StateEntityService<ListingPolicy> {
    ListingPolicy resolveActivePolicy(String country, String category, LocalDate date);
    ListingDecision validateListing(String country, String category, Object listingData);
    List<ListingPolicy> getAllActivePolicies();
    List<ListingPolicy> getAllDraftPolicies();
}
