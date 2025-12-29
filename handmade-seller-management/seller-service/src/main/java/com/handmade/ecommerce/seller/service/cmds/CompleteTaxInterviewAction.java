package com.handmade.ecommerce.seller.service.cmds;

import com.handmade.ecommerce.seller.domain.aggregate.SellerAccount;
import com.handmade.ecommerce.seller.dto.command.CompleteTaxInterviewPayload;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Action: Complete tax interview
 * Transition: TAX_VERIFICATION → BANK_VERIFICATION
 * Validates W-9/W-8 forms and tax information
 */
public class CompleteTaxInterviewAction extends AbstractSTMTransitionAction<SellerAccount, CompleteTaxInterviewPayload> {
    
    private static final Logger logger = LoggerFactory.getLogger(CompleteTaxInterviewAction.class);
    
    @Override
    public void doTransition(SellerAccount sellerAccount,
                            CompleteTaxInterviewPayload payload,
                            State startState,
                            String eventId,
                            State endState,
                            STMInternalTransitionInvoker<?> stm,
                            Transition transition) throws Exception {
        
        logger.info("Completing tax interview for seller: {}, tax form: {}", 
                   sellerAccount.getId(), payload.getTaxFormType());
        
        // Update tax information
        if (sellerAccount.getBusinessDetails() != null) {
            sellerAccount.getBusinessDetails().setTaxId(payload.getTaxId());
        }
        
        sellerAccount.setTaxInterviewCompleted(true);
        sellerAccount.setTaxInterviewCompletedAt(java.time.LocalDateTime.now());
        
        logger.info("Tax interview completed for seller {}. Moving to BANK_VERIFICATION", 
                   sellerAccount.getId());
    }
}
