package com.handmade.ecommerce.pricing.service.postSaveHooks;

import com.handmade.ecommerce.price.model.Price;
import org.chenile.workflow.model.TransientMap;
import org.chenile.workflow.service.stmcmds.PostSaveHook;

/**
 Contains customized post Save Hook for the State ID.
*/
public class CREATEDPricePostSaveHook implements PostSaveHook<Price>{
	@Override
    public void execute(Price price, TransientMap map){
    }
}
