package com.handmade.ecommerce.sellerorch.service;

import com.handmade.ecommerce.sellerorch.SellerContext;
import org.apache.commons.validator.routines.EmailValidator;
import org.chenile.owiz.Command;
import org.springframework.stereotype.Component;

@Component
public class EmailValidatorService implements Command<SellerContext> {

    @Override
    public void execute(SellerContext context) throws Exception {
        String email = context.getRequestSeller().getEmail();

       EmailValidator validator = EmailValidator.getInstance();
        if (!validator.isValid(email)) {
            throw new IllegalArgumentException("Invalid email address");
        }

        // Optionally call email verification service
        // emailVerificationService.verify(email);
    }
}