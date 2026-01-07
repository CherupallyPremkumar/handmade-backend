package com.handmade.ecommerce.policy.service.impl;

import com.handmade.ecommerce.policy.api.ListingPolicyManager;
import com.handmade.ecommerce.policy.domain.aggregate.ListingPolicy;
import com.handmade.ecommerce.policy.domain.valueobject.ListingDecision;
import com.handmade.ecommerce.policy.service.store.ListingPolicyEntityStore;
import org.chenile.stm.STM;
import org.chenile.stm.impl.STMActionsInfoProvider;
import org.chenile.utils.entity.service.EntityStore;
import org.chenile.workflow.service.impl.StateEntityServiceImpl;

import java.time.LocalDate;
import java.util.List;

public class ListingPolicyManagerImpl extends StateEntityServiceImpl<ListingPolicy> implements ListingPolicyManager {

    private ListingPolicyEntityStore entityStore;

    public ListingPolicyManagerImpl(STM<ListingPolicy> stm, STMActionsInfoProvider infoProvider, 
            EntityStore<ListingPolicy> entityStore) {
        super(stm, infoProvider, entityStore);
        this.entityStore = (ListingPolicyEntityStore) entityStore;
    }

    @Override
    public ListingPolicy resolveActivePolicy(String country, String category, LocalDate date) {
        return entityStore.findActivePolicy(country, category, date).orElse(null);
    }

    @Override
    public ListingDecision validateListing(String country, String category, Object listingData) {
        ListingPolicy policy = resolveActivePolicy(country, category, LocalDate.now());
        if (policy == null) return ListingDecision.APPROVED;
        
        // Basic validation logic
        return ListingDecision.APPROVED;
    }

    @Override
    public List<ListingPolicy> getAllActivePolicies() {
        return entityStore.findAllActivePolicies(LocalDate.now());
    }

    @Override
    public List<ListingPolicy> getAllDraftPolicies() {
        return entityStore.findAllDraftPolicies();
    }
}
