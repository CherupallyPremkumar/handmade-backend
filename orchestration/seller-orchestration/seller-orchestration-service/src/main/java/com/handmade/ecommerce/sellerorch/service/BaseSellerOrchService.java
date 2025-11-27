package com.handmade.ecommerce.sellerorch.service;

import org.chenile.owiz.Command;

public class BaseSellerOrchService implements Command<SellerContext> {

    @Override
    public void execute(SellerContext sellerContext) throws Exception {
        if (bypassSellerService(sellerContext)) {
            doContinue(sellerContext);
            return;
        }
        doPreProcessing(sellerContext);
        try {
            doProcess(sellerContext);
            doContinue(sellerContext);
        } catch (Throwable e) {
            sellerContext.setException(e);
        } finally {
            doPostProcessing(sellerContext);
        }
    }

    protected void doProcess(SellerContext sellerContext) {
    }

    protected void doPostProcessing(SellerContext sellerContext) {
    }

    protected void doPreProcessing(SellerContext sellerContext) {
    }

    protected void doContinue(SellerContext sellerContext) throws Exception {
        sellerContext.getChainContext().doContinue();
    }

    protected boolean bypassSellerService(SellerContext exchange) {
        return false;
    }
}
