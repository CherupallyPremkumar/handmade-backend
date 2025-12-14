package com.handmade.ecommerce.sellerorch.service;

import com.handmade.ecommerce.sellerorch.SellerContext;
import com.handmade.ecommerce.tax.service.TaxService;
import org.chenile.owiz.Command;
import org.springframework.beans.factory.annotation.Autowired;

public class EinValidator implements Command<SellerContext> {
    @Autowired
    private TaxService taxService; // call external GSTIN API

    @Override
    public void execute(SellerContext context) throws Exception {

        String ein = context.getRequestSeller().getTaxId();
        String code = context.getRequestSeller().getCountry();
        if (!taxService.validateTaxId(code,ein)) {
            throw new IllegalArgumentException("Invalid Ein");
        }
    }
}
