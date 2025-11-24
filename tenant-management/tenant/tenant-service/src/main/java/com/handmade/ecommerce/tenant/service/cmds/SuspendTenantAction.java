package com.handmade.ecommerce.tenant.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;

import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.tenant.model.Tenant;
import com.handmade.ecommerce.tenant.dto.SuspendTenantPayload;

/**
 Contains customized logic for the transition. Common logic resides at {@link DefaultSTMTransitionAction}
 <p>Use this class if you want to augment the common logic for this specific transition</p>
 <p>Use a customized payload if required instead of MinimalPayload</p>
*/
public class SuspendTenantAction extends AbstractSTMTransitionAction<Tenant,

    SuspendTenantPayload>{


	@Override
	public void transitionTo(Tenant tenant,
            SuspendTenantPayload payload,
            State startState, String eventId,
			State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
            tenant.transientMap.previousPayload = payload;
	}

}
