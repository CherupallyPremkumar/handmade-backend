package com.handmade.ecommerce.product.service.postSaveHooks;

import com.handmade.ecommerce.product.model.Product;
import org.chenile.workflow.model.TransientMap;
import org.chenile.workflow.service.stmcmds.PostSaveHook;

/**
 Contains customized post Save Hook for the State ID.
*/
public class ACTIVEProductPostSaveHook implements PostSaveHook<Product>{
	@Override
    public void execute(Product product, TransientMap map){
    }
}
