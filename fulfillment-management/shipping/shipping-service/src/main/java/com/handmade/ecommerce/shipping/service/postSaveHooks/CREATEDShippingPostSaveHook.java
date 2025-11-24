package com.handmade.ecommerce.shipping.service.postSaveHooks;

import com.handmade.ecommerce.shipping.model.Shipping;
import org.chenile.workflow.model.TransientMap;
import org.chenile.workflow.service.stmcmds.PostSaveHook;

/**
 Contains customized post Save Hook for the State ID.
*/
public class CREATEDShippingPostSaveHook implements PostSaveHook<Shipping>{
	@Override
    public void execute(Shipping shipping, TransientMap map){
    }
}
