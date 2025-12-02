package com.handmade.ecommerce.sellerorch.service;

import com.handmade.ecommerce.seller.dto.command.CreateSellerRequest;
import com.handmade.ecommerce.seller.service.SellerService;
import com.handmade.ecommerce.command.CreateSellerCommand;
import com.handmade.ecommerce.sellerorch.SellerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Orchestration service for seller validation.
 * Delegates actual validation logic to the seller module.
 */
@Service
public class ValidateSellerService extends BaseSellerOrchService {

    @Autowired
    private SellerService sellerService;

    @Override
    protected void doProcess(SellerContext sellerContext) {
        CreateSellerCommand command = sellerContext.getRequestSeller();
        CreateSellerRequest request = mapToSellerRequest(command);
        sellerService.validate(request);
        System.out.println("Seller validation completed successfully");
        super.doProcess(sellerContext);
    }

    /**
     * Maps orchestration command to seller module request DTO.
     */
    private CreateSellerRequest mapToSellerRequest(CreateSellerCommand command) {
        CreateSellerRequest request = new CreateSellerRequest();
        request.setSellerName(command.getSellerName());
        request.setEmail(command.getEmail());
        request.setBusinessName(command.getBusinessName());
        request.setTaxId(command.getTaxId());
        request.setBankAccountNumber(command.getBankAccountNumber());
        request.setIfscCode(command.getIfscCode());
        request.setAccountHolderName(command.getAccountHolderName());
        request.setKycDocumentId(command.getKycDocumentId());
        return request;
    }
}
