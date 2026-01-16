package com.handmade.ecommerce.notification.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;

import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.notification.model.Notification;
import com.handmade.ecommerce.notification.model. MarkDeliveredNotificationPayload;

/**
 Contains customized logic for the transition. Common logic resides at {@link DefaultSTMTransitionAction}
 <p>Use this class if you want to augment the common logic for this specific transition</p>
 <p>Use a customized payload if required instead of MinimalPayload</p>
*/
public class MarkDeliveredNotificationAction extends AbstractSTMTransitionAction<Notification,
    MarkDeliveredNotificationPayload>{

	@Override
	public void transitionTo(Notification notification,
            MarkDeliveredNotificationPayload payload,
            State startState, String eventId,
			State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
	}

}
