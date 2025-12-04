package com.handmade.ecommerce.paymentorder.service.postSaveHooks;

import com.handmade.ecommerce.paymentorder.model.Paymentorder;
import org.chenile.workflow.model.TransientMap;
import org.chenile.workflow.service.stmcmds.PostSaveHook;

/**
 Contains customized post Save Hook for the State ID.
*/
public class RESOLVEDPaymentorderPostSaveHook implements PostSaveHook<Paymentorder>{
	@Override
    public void execute(Paymentorder paymentorder, TransientMap map){
    }
}
