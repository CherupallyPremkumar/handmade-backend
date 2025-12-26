package com.handmade.ecommerce.payment.service.impl;

import com.handmade.ecommerce.payment.service.PaymentValidationService;
import org.springframework.stereotype.Service;

@Service
public class PaymentValidationServiceImpl implements PaymentValidationService {

    private static final String IFSC_REGEX = "^[A-Z]{4}0[A-Z0-9]{6}$";
    private static final String ACCOUNT_NUMBER_REGEX = "^[0-9]{8,20}$";

    @Override
    public boolean validateAccountNumber(String accountNumber) {
        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            return false;
        }
        return accountNumber.matches(ACCOUNT_NUMBER_REGEX);
    }

    @Override
    public boolean validateIfscCode(String ifscCode) {
        if (ifscCode == null || ifscCode.trim().isEmpty()) {
            return false;
        }
        return ifscCode.matches(IFSC_REGEX);
    }
}
