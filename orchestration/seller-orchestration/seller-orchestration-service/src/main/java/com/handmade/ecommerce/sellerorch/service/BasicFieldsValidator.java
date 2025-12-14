package com.handmade.ecommerce.sellerorch.service;

import com.handmade.ecommerce.command.CreateSellerCommand;
import com.handmade.ecommerce.sellerorch.SellerContext;
import org.chenile.owiz.Command;
import org.springframework.stereotype.Component;
import org.apache.commons.lang3.StringUtils;

@Component
public class BasicFieldsValidator implements Command<SellerContext> {


    @Override
    public void execute(SellerContext context) throws Exception {

    }
}