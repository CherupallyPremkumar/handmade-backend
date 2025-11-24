package com.handmade.ecommerce.user.service.postSaveHooks;

import com.handmade.ecommerce.user.model.User;
import org.chenile.workflow.model.TransientMap;
import org.chenile.workflow.service.stmcmds.PostSaveHook;

/**
 Contains customized post Save Hook for the State ID.
*/
public class ARCHIVEDUserPostSaveHook implements PostSaveHook<User>{
	@Override
    public void execute(User user, TransientMap map){
    }
}
