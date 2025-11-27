package com.handmade.ecommerce.sellerorch.service;

import com.handmade.ecommerce.seller.model.Seller;
import com.handmade.ecommerce.seller.dto.command.SubmitApplicationPayload;
import org.chenile.workflow.service.impl.StateEntityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class CreateSellerCommandService extends BaseSellerOrchService {

    @Autowired
    @Qualifier("_sellerStateEntityService_")
    private StateEntityServiceImpl sellerService;

    @Override
    protected void doProcess(SellerContext sellerContext) {
        System.out.println("Creating seller in CreateSellerCommandService");
        
        // Map CreateSellerCommand to Seller entity
        Seller seller = mapCommandToSeller(sellerContext);
        
        // Create seller in DRAFT state (initial state)
        Seller createdSeller = (Seller) sellerService.create(seller).getMutatedEntity();

        // Store created seller in context for downstream services
        sellerContext.putData("createdSeller", createdSeller);
        
        // Trigger submitApplication event to move to PENDING_VERIFICATION
        SubmitApplicationPayload payload = new SubmitApplicationPayload();
        sellerService.processById(createdSeller.getId(), "submitApplication", payload);
        System.out.println("Seller application submitted, transitioned to PENDING_VERIFICATION");
        
        super.doProcess(sellerContext);
    }

    private Seller mapCommandToSeller(SellerContext context) {
        var command = context.getRequestSeller();
        Seller seller = new Seller();
        
        // Map basic fields
        seller.setSellerName(command.getSellerName());
        seller.setContactEmail(command.getEmail());
        seller.setBusinessType(command.getBusinessName());
        
        // Generate unique seller code (in production, use proper ID generation)
        seller.setSellerCode("SELLER_" + System.currentTimeMillis());
        seller.setUrlPath(seller.getSellerCode().toLowerCase());
        
        // Set defaults
        seller.setCurrency("USD");
        seller.setLocale("en_US");
        
        return seller;
    }

    @Override
    protected void doPostProcessing(SellerContext sellerContext) {
        System.out.println("Post-processing seller creation");
        super.doPostProcessing(sellerContext);
    }

    @Override
    protected void doPreProcessing(SellerContext sellerContext) {
        System.out.println("Pre-processing seller creation");
        super.doPreProcessing(sellerContext);
    }
}
