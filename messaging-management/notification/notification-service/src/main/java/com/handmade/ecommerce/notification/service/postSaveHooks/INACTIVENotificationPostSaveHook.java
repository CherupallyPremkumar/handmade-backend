package com.handmade.ecommerce.notification.service.postSaveHooks;

import com.handmade.ecommerce.notification.model.Notification;
import org.chenile.workflow.model.TransientMap;
import org.chenile.workflow.service.stmcmds.PostSaveHook;

/**
 Contains customized post Save Hook for the State ID.
*/
public class INACTIVENotificationPostSaveHook implements PostSaveHook<Notification>{
	@Override
    public void execute(Notification notification, TransientMap map){
    }
}
