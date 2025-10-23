package com.homebase.ecom.service.price;

import com.homebase.ecom.command.price.ActivatePriceCommand;
import com.homebase.ecom.domain.Price;
import com.homebase.ecom.domain.PriceLine;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.action.STMTransitionAction;
import org.chenile.stm.model.Transition;
import org.springframework.stereotype.Service;

@Service
public class ActivatePriceInCreatedService implements STMTransitionAction<PriceLine> {

    @Override
    public void doTransition(Price price, Object transitionParam, State startState, String eventId, State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        if (!(transitionParam instanceof ActivatePriceCommand)) {
            throw new IllegalArgumentException("Invalid transition parameter for activating price");
        }

        ActivatePriceCommand command = (ActivatePriceCommand) transitionParam;
        if (!price.getProductVariantId().equals(command.getProductVariantId())) {
            throw new IllegalStateException("Product variant ID mismatch during price activation");
        }
        if (price.getAmount() == null || price.getAmount().signum() <= 0) {
            throw new IllegalStateException("Price amount must be greater than zero to activate");
        }
    }
}
