package com.handmade.ecommerce.payment.service.validation;

import com.handmade.ecommerce.payment.dto.BankAccountDetails;
import com.handmade.ecommerce.payment.service.PaymentService;
import com.handmade.ecommerce.seller.dto.command.CreateSellerRequest;
import com.handmade.ecommerce.seller.dto.validation.ValidationResult;
import com.handmade.ecommerce.seller.service.validation.SellerValidator;
import com.handmade.ecommerce.validation.BaseValidator;
import com.handmade.ecommerce.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Validates bank account details format (no penny drop in MVP).
 * Priority: 4
 */
@Component
@Order(4)
public class BankAccountValidator extends BaseValidator<BankAccountDetails> {

    @Autowired
    private PaymentService paymentService;

    @Override
    public void doValidate(BankAccountDetails request) {
        ValidationResult result = new ValidationResult();

        String accountNumber = request.getBankAccountNumber();
        String ifscCode = request.getIfscCode();

        // Bank details are optional in MVP
        if ((accountNumber == null || accountNumber.trim().isEmpty()) &&
                (ifscCode == null || ifscCode.trim().isEmpty())) {
            return result;
        }

        // If one is provided, both must be provided
        if (accountNumber != null && !accountNumber.trim().isEmpty() &&
                (ifscCode == null || ifscCode.trim().isEmpty())) {
            result.addError("ifscCode", "IFSC code is required when account number is provided");
        }

        if (ifscCode != null && !ifscCode.trim().isEmpty() &&
                (accountNumber == null || accountNumber.trim().isEmpty())) {
            result.addError("bankAccountNumber", "Account number is required when IFSC code is provided");
        }

        // Validate account number format
        if (accountNumber != null && !accountNumber.trim().isEmpty()) {
            if (!paymentService.validateAccountNumber(accountNumber)) {
                result.addError("bankAccountNumber", "Invalid account number. Must be 8-20 digits");
            }
        }

        // Validate IFSC code format
        if (ifscCode != null && !ifscCode.trim().isEmpty()) {
            if (!paymentService.validateIfscCode(ifscCode)) {
                result.addError("ifscCode", "Invalid IFSC code format. Expected: ABCD0123456");
            }
        }

        return result;
    }

    @Override
    public int getOrder() {
        return 4;
    }
}
