package com.handmade.ecommerce.shippinglabel.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;

import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.logistics.model.ShippingLabel;
import com.handmade.ecommerce.logistics.dto.VoidShippingLabelPayload;

/**
 Contains customized logic for the transition. Common logic resides at {@link DefaultSTMTransitionAction}
 <p>Use this class if you want to augment the common logic for this specific transition</p>
 <p>Use a customized payload if required instead of MinimalPayload</p>
*/
public class VoidShippingLabelAction extends AbstractSTMTransitionAction<ShippingLabel,
               VoidShippingLabelPayload>{

	@Override
	public void transitionTo(ShippingLabel shippinglabel,
            VoidShippingLabelPayload payload,
            State startState, String eventId,
			State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
	}

}