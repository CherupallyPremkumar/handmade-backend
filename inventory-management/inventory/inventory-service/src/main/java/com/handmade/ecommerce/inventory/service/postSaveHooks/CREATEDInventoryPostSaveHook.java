package com.handmade.ecommerce.inventory.service.postSaveHooks;

import com.handmade.ecommerce.inventory.model.Inventory;
import org.chenile.workflow.model.TransientMap;
import org.chenile.workflow.service.stmcmds.PostSaveHook;

/**
 Contains customized post Save Hook for the State ID.
*/
public class CREATEDInventoryPostSaveHook implements PostSaveHook<Inventory>{
	@Override
    public void execute(Inventory inventory, TransientMap map){
    }
}
