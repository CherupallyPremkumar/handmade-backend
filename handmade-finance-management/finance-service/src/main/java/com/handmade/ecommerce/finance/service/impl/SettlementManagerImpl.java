package com.handmade.ecommerce.finance.service.impl;

import com.handmade.ecommerce.finance.api.SettlementManager;
import com.handmade.ecommerce.finance.domain.aggregate.SettlementAccount;
import com.handmade.ecommerce.finance.domain.entity.SettlementTransaction;
import com.handmade.ecommerce.finance.service.store.SettlementAccountEntityStore;
import org.chenile.stm.STM;
import org.chenile.stm.impl.STMActionsInfoProvider;
import org.chenile.workflow.service.impl.StateEntityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import java.math.BigDecimal;

public class SettlementManagerImpl extends StateEntityServiceImpl<SettlementAccount> implements SettlementManager {

    @Autowired
    private SettlementAccountEntityStore entityStore;

    public SettlementManagerImpl(STM<SettlementAccount> stm, 
                               STMActionsInfoProvider infoProvider,
                               SettlementAccountEntityStore entityStore) {
        super(stm, infoProvider, entityStore);
    }

    @Override
    public void settleOrder(String sellerId, String orderId, BigDecimal amount, String currency) {
        SettlementAccount account = entityStore.findBySellerId(sellerId)
                .orElseGet(() -> {
                    SettlementAccount newAcc = new SettlementAccount();
                    newAcc.setSellerId(sellerId);
                    return newAcc;
                });

        // Mock fee calculation (will be replaced by CommissionPolicy delegate)
        BigDecimal fees = amount.multiply(new BigDecimal("0.10")); 
        
        account.creditOrder(amount, fees);
        
        SettlementTransaction tx = new SettlementTransaction();
        tx.setAccount(account);
        tx.setAmount(amount.subtract(fees));
        tx.setType(SettlementTransaction.Type.ORDER_SETTLEMENT);
        tx.setReferenceId(orderId);
        processEntity(account,"SETTLE_ORDER",tx);
    }

    @Override
    public void releaseEscrowFunds(String sellerId, BigDecimal amount) {
        entityStore.findBySellerId(sellerId).ifPresent(account -> {
            account.releaseFunds(amount);
            entityStore.store(account);
        });
    }
}
