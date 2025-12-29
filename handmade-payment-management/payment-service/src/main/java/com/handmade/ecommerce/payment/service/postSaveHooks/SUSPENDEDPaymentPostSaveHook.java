package com.handmade.ecommerce.payment.service.postSaveHooks;

import com.handmade.ecommerce.payment.model.Payment;
import org.chenile.workflow.model.TransientMap;
import org.chenile.workflow.service.stmcmds.PostSaveHook;

/**
 Contains customized post Save Hook for the State ID.
*/
public class SUSPENDEDPaymentPostSaveHook implements PostSaveHook<Payment>{
	@Override
    public void execute(Payment payment, TransientMap map){
    }
}
