package com.handmade.ecommerce.finance.service.impl;

import com.handmade.ecommerce.finance.api.PayoutManager;
import com.handmade.ecommerce.finance.domain.aggregate.PayoutInstruction;
import com.handmade.ecommerce.finance.service.store.PayoutInstructionEntityStore;
import org.chenile.stm.STM;
import org.chenile.stm.impl.STMActionsInfoProvider;
import org.chenile.workflow.service.impl.StateEntityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import java.math.BigDecimal;

public class PayoutManagerImpl extends StateEntityServiceImpl<PayoutInstruction> implements PayoutManager {

    @Autowired
    private PayoutInstructionEntityStore entityStore;

    public PayoutManagerImpl(STM<PayoutInstruction> stm, 
                           STMActionsInfoProvider infoProvider,
                           PayoutInstructionEntityStore entityStore) {
        super(stm, infoProvider, entityStore);
    }

    @Override
    public void requestPayout(String sellerId, BigDecimal amount) {
        PayoutInstruction payout = new PayoutInstruction();
        payout.setSellerId(sellerId);
        payout.setAmount(amount);
        
        // Save as DRAFT/REQUESTED
        entityStore.store(payout);
    }
}
