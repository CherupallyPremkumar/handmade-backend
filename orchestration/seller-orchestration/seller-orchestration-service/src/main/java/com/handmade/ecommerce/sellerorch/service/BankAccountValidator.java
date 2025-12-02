package com.handmade.ecommerce.sellerorch.service;

import com.handmade.ecommerce.payment.service.PaymentService;
import com.handmade.ecommerce.sellerorch.SellerContext;
import org.chenile.owiz.Command;
import org.springframework.beans.factory.annotation.Autowired;

public class BankAccountValidator implements Command<SellerContext> {


    @Autowired
    PaymentService paymentService;
    @Override
    public void execute(SellerContext context) throws Exception {

        paymentService.validateAccountNumber();
        paymentService.validateIfscCode()
    }
}
