package com.handmade.ecommerce.cartline.service.cmds;

import com.handmade.ecommerce.cartline.model.Cartline;
import org.chenile.core.context.ContextContainer;
import org.chenile.owiz.Command;
import org.springframework.beans.factory.annotation.Autowired;

public class SetCartLineRegionCommandService implements Command<Cartline> {

    @Autowired
    ContextContainer contextContainer;
    @Override
    public void execute(Cartline context) throws Exception {
        context.setRegion(contextContainer.getRegion());
        System.out.println(context.toString());
    }
}
