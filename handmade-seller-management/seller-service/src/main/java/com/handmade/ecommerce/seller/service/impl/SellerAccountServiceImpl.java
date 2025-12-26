package com.handmade.ecommerce.seller.service.impl;

import com.handmade.ecommerce.seller.domain.aggregate.SellerAccount;
import com.handmade.ecommerce.seller.api.SellerAccountService;
import org.chenile.stm.STM;
import org.chenile.stm.impl.STMActionsInfoProvider;
import org.chenile.utils.entity.service.EntityStore;
import org.chenile.workflow.dto.StateEntityServiceResponse;
import org.chenile.workflow.service.impl.StateEntityServiceImpl;

/**
 * Service implementation for SellerAccount aggregate
 * Handles account lifecycle: registration, verification, activation, suspension
 */
public class SellerAccountServiceImpl extends StateEntityServiceImpl<SellerAccount> implements SellerAccountService {

    public SellerAccountServiceImpl(STM<SellerAccount> stm,
                                    STMActionsInfoProvider stmActionsInfoProvider,
                                    EntityStore<SellerAccount> entityStore) {
        super(stm, stmActionsInfoProvider, entityStore);
    }

    @Override
    public StateEntityServiceResponse<SellerAccount> create(SellerAccount sellerAccount) {
        // Framework's GenericEntryAction will call entityStore.store() automatically
        return super.create(sellerAccount);
    }

    @Override
    public StateEntityServiceResponse<SellerAccount> processById(String id, String eventId, Object payload) {
        // Process state transition events (verifyIdentity, completeTaxInterview, etc.)
        return super.processById(id, eventId, payload);
    }
}
