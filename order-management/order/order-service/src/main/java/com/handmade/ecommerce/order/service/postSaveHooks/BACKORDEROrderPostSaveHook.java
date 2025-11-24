package com.handmade.ecommerce.order.service.postSaveHooks;

import com.handmade.ecommerce.order.model.Order;
import org.chenile.workflow.model.TransientMap;
import org.chenile.workflow.service.stmcmds.PostSaveHook;

/**
 Contains customized post Save Hook for the State ID.
*/
public class BACKORDEROrderPostSaveHook implements PostSaveHook<Order>{
	@Override
    public void execute(Order order, TransientMap map){
    }
}
