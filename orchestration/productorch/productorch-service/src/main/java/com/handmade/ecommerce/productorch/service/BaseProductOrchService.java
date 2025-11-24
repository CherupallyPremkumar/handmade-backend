package com.handmade.ecommerce.productorch.service;

import org.chenile.core.context.ChenileExchange;
import org.chenile.owiz.Command;

public class BaseProductOrchService implements Command<ProductContext> {

    @Override
    public void execute(ProductContext productContext) throws Exception {
        if (bypassProductService(productContext)) {
            doContinue(productContext);
            return;
        }
        doPreProcessing(productContext);
        try {
            doProcess(productContext);
            doContinue(productContext);
        }catch(Throwable e){
            productContext.setException(e);
        } finally {
            doPostProcessing(productContext);
        }
    }

    protected void doProcess(ProductContext productContext) {
    }

    protected void doPostProcessing(ProductContext productContext) {
    }

    protected void doPreProcessing(ProductContext productContext) {
    }

    protected void doContinue(ProductContext productContext) throws Exception {
        productContext.getChainContext().doContinue();
    }

    protected boolean bypassProductService(ProductContext exchange) {
        return false;
    }
}
