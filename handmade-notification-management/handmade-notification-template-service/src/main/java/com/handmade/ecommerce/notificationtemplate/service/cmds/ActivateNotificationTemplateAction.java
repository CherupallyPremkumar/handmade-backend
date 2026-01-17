package com.handmade.ecommerce.notificationtemplate.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;

import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.notification.model.NotificationTemplate;
import com.handmade.ecommerce.notification.model. ActivateNotificationTemplatePayload;

/**
 Contains customized logic for the transition. Common logic resides at {@link DefaultSTMTransitionAction}
 <p>Use this class if you want to augment the common logic for this specific transition</p>
 <p>Use a customized payload if required instead of MinimalPayload</p>
*/
public class ActivateNotificationTemplateAction extends AbstractSTMTransitionAction<NotificationTemplate,
    ActivateNotificationTemplatePayload>{

	@Override
	public void transitionTo(NotificationTemplate notificationtemplate,
            ActivateNotificationTemplatePayload payload,
            State startState, String eventId,
			State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
	}

}
package com.handmade.ecommerce.notificationtemplate.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;

import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.notification.model.NotificationTemplate;
import com.handmade.ecommerce.notification.model. ActivateNotificationTemplatePayload;

/**
 Contains customized logic for the transition. Common logic resides at {@link DefaultSTMTransitionAction}
 <p>Use this class if you want to augment the common logic for this specific transition</p>
 <p>Use a customized payload if required instead of MinimalPayload</p>
*/
public class ActivateNotificationTemplateAction extends AbstractSTMTransitionAction<NotificationTemplate,
    ActivateNotificationTemplatePayload>{

	@Override
	public void transitionTo(NotificationTemplate notificationtemplate,
            ActivateNotificationTemplatePayload payload,
            State startState, String eventId,
			State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
	}

}
