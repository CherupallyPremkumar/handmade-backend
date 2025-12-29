package com.handmade.ecommerce.orderline.service.postSaveHooks;

import com.handmade.ecommerce.orderline.model.Orderline;
import org.chenile.workflow.model.TransientMap;
import org.chenile.workflow.service.stmcmds.PostSaveHook;

/**
 Contains customized post Save Hook for the State ID.
*/
public class PENDING_SHIPMENT_FULFILLMENTOrderlinePostSaveHook implements PostSaveHook<Orderline>{
	@Override
    public void execute(Orderline orderline, TransientMap map){
    }
}
