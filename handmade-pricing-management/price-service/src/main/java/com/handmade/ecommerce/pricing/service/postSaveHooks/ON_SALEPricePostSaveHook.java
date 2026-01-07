package com.handmade.ecommerce.pricing.service.postSaveHooks;

import com.handmade.ecommerce.pricing.entity.Price;
import org.chenile.workflow.model.TransientMap;
import org.chenile.workflow.service.stmcmds.PostSaveHook;

/**
 Contains customized post Save Hook for the State ID.
*/
public class ON_SALEPricePostSaveHook implements PostSaveHook<Price>{
	@Override
    public void execute(Price price, TransientMap map){
    }
}
