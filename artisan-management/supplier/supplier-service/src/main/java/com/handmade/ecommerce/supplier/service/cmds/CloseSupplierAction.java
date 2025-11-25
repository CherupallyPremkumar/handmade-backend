package com.handmade.ecommerce.supplier.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;

import org.chenile.workflow.param.MinimalPayload;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.supplier.model.Supplier;

import org.chenile.base.exception.BadRequestException;
import org.chenile.workflow.service.activities.ActivityChecker;
import org.springframework.beans.factory.annotation.Autowired;

public class CloseSupplierAction extends AbstractSTMTransitionAction<Supplier,MinimalPayload>{
    @Autowired ActivityChecker activityChecker;
	@Override
	public void transitionTo(Supplier supplier, MinimalPayload payload, State startState, String eventId,
			State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        if(!activityChecker.areAllActivitiesComplete(supplier)){
            throw new BadRequestException(50000,"Invalid Transition to close. All activities are not complete yet");
        }
        supplier.closeComment = payload.getComment();
	}

}
