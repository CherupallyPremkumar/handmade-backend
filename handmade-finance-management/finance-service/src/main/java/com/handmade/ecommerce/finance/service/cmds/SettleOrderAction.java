package com.handmade.ecommerce.finance.service.cmds;

import com.handmade.ecommerce.finance.domain.aggregate.SettlementAccount;
import com.handmade.ecommerce.finance.domain.entity.SettlementTransaction;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;

/**
 * Action triggered when an order is settled into an account.
 * This can be used for extra auditing or event emission.
 */
public class SettleOrderAction extends AbstractSTMTransitionAction<SettlementAccount, SettlementTransaction> {
    @Override
    public void transitionTo(SettlementAccount account, SettlementTransaction tx, State startState, 
                             String eventId, State endState, 
                             STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        // The balance update is already done in the manager for now
        // This action can be used for secondary side effects
    }
}
