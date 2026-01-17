package com.handmade.ecommerce.auditprocess.service.cmds;

import com.handmade.ecommerce.governance.dto.FailAuditProcessPayload;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;

import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.governance.model.AuditProcess;

/**
 Contains customized logic for the transition. Common logic resides at {@link DefaultSTMTransitionAction}
 <p>Use this class if you want to augment the common logic for this specific transition</p>
 <p>Use a customized payload if required instead of MinimalPayload</p>
*/
public class FailAuditProcessAction extends AbstractSTMTransitionAction<AuditProcess,
		FailAuditProcessPayload>{

	@Override
	public void transitionTo(AuditProcess auditprocess,
            FailAuditProcessPayload payload,
            State startState, String eventId,
			State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
	}

}
