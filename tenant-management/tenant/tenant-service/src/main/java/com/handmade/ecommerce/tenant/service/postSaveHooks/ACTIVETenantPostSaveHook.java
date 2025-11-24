package com.handmade.ecommerce.tenant.service.postSaveHooks;

import com.handmade.ecommerce.tenant.model.Tenant;
import org.chenile.workflow.model.TransientMap;
import org.chenile.workflow.service.stmcmds.PostSaveHook;

/**
 Contains customized post Save Hook for the State ID.
*/
public class ACTIVETenantPostSaveHook implements PostSaveHook<Tenant>{
	@Override
    public void execute(Tenant tenant, TransientMap map){
    }
}
