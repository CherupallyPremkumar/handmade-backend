package com.handmade.ecommerce.cartline.service.cmds;

import com.handmade.ecommerce.cartline.model.Cartline;
import org.chenile.owiz.OrchExecutor;
import org.chenile.stm.impl.STMActionsInfoProvider;
import org.chenile.utils.entity.service.EntityStore;
import org.chenile.workflow.service.stmcmds.GenericEntryAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Entry action for Cartline creation
 * Handles: price fetching, inventory check, tax calculation
 */
@Component
public class CartLineEntryAction extends GenericEntryAction<Cartline>  {

    private static final Logger logger = LoggerFactory.getLogger(CartLineEntryAction.class);

    public CartLineEntryAction(EntityStore<Cartline> entityStore, 
                              STMActionsInfoProvider stmActionsInfoProvider) {
        super(entityStore, stmActionsInfoProvider);
    }

    @Autowired
    private OrchExecutor<Cartline> cartlineOrchExecutor;

    @Override
    public void execute(Cartline cartline) throws Exception {
        cartlineOrchExecutor.execute(cartline);
        super.execute(cartline);
    }
}
