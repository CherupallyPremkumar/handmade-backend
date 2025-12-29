package com.handmade.ecommerce.seller.service.postSaveHooks;

import com.handmade.ecommerce.seller.model.Seller;
import org.chenile.workflow.model.TransientMap;
import org.chenile.workflow.service.stmcmds.PostSaveHook;

/**
 Contains customized post Save Hook for the State ID.
*/
public class DELETEDSellerPostSaveHook implements PostSaveHook<Seller>{
	@Override
    public void execute(Seller seller, TransientMap map){
    }
}
