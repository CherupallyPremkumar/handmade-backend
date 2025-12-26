package com.handmade.ecommerce.variant.service.postSaveHooks;

import com.handmade.ecommerce.variant.model.Variant;
import org.chenile.workflow.model.TransientMap;
import org.chenile.workflow.service.stmcmds.PostSaveHook;

/**
 Contains customized post Save Hook for the State ID.
*/
public class DRAFTVariantPostSaveHook implements PostSaveHook<Variant>{
	@Override
    public void execute(Variant variant, TransientMap map){
    }
}
