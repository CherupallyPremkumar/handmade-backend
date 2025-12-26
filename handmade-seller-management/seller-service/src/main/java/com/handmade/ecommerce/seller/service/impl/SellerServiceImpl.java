package com.handmade.ecommerce.seller.service.impl;

import com.handmade.ecommerce.seller.domain.aggregate.Seller;
import com.handmade.ecommerce.seller.api.SellerService;
import org.chenile.stm.STM;
import org.chenile.stm.impl.STMActionsInfoProvider;
import org.chenile.utils.entity.service.EntityStore;
import org.chenile.workflow.dto.StateEntityServiceResponse;
import org.chenile.workflow.service.impl.StateEntityServiceImpl;

/**
 * Service implementation for Seller aggregate
 * Handles store operations: setup, product approval, vacation mode, restrictions
 */
public class SellerServiceImpl extends StateEntityServiceImpl<Seller> implements SellerService {

    public SellerServiceImpl(STM<Seller> stm,
                            STMActionsInfoProvider stmActionsInfoProvider,
                            EntityStore<Seller> entityStore) {
        super(stm, stmActionsInfoProvider, entityStore);
    }

    @Override
    public StateEntityServiceResponse<Seller> create(Seller seller) {
        // Framework's GenericEntryAction will call entityStore.store() automatically
        return super.create(seller);
    }

    @Override
    public StateEntityServiceResponse<Seller> processById(String id, String eventId, Object payload) {
        // Process state transition events (completeSetup, submitFirstProduct, etc.)
        return super.processById(id, eventId, payload);
    }
}
