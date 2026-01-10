package com.handmade.ecommerce.platform.governance.service.impl;

import com.handmade.ecommerce.platform.governance.api.GdprRequestService;
import com.handmade.ecommerce.platform.governance.entity.GdprRequest;
import org.chenile.stm.STM;
import org.chenile.stm.impl.STMActionsInfoProvider;
import org.chenile.utils.entity.service.EntityStore;
import org.chenile.workflow.service.impl.StateEntityServiceImpl;

/**
 * Implementation of GdprRequestService.
 */
public class GdprRequestServiceImpl extends StateEntityServiceImpl<GdprRequest>
        implements GdprRequestService {

    public GdprRequestServiceImpl(STM<GdprRequest> stm,
            STMActionsInfoProvider stmActionsInfoProvider,
            EntityStore<GdprRequest> entityStore) {
        super(stm, stmActionsInfoProvider, entityStore);
    }
}
