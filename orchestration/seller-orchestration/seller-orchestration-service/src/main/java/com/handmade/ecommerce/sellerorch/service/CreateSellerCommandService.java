package com.handmade.ecommerce.sellerorch.service;

import com.handmade.ecommerce.seller.model.Seller;
import com.handmade.ecommerce.seller.model.SellerPaymentInfo;
import com.handmade.ecommerce.seller.model.PaymentMethodd;
import com.handmade.ecommerce.seller.dto.command.SubmitApplicationPayload;
import java.util.ArrayList;
import java.util.List;
import com.handmade.ecommerce.sellerorch.SellerContext;
import com.handmade.ecommerce.seller.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class CreateSellerCommandService extends BaseSellerOrchService {

    @Autowired
    @Qualifier("_sellerStateEntityService_")
    private SellerService sellerService;

    @Override
    protected void doProcess(SellerContext sellerContext) {
        System.out.println("Creating seller in CreateSellerCommandService");

        // Map CreateSellerCommand to Seller entity
        Seller seller = mapCommandToSeller(sellerContext);

        // Create seller in DRAFT state (initial state)
        // Using SellerService which extends StateEntityService
        Seller createdSeller = sellerService.create(seller).getMutatedEntity();

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

        // Map Payment Info if bank details are present
        if (command.getBankAccountNumber() != null) {
            SellerPaymentInfo paymentInfo = new SellerPaymentInfo();
            // We set sellerId after the seller is persisted usually, but here we might need
            // to rely on cascading or set it if we have the code.
            // Since we generated sellerCode above, we can use it.
            paymentInfo.setSellerId(seller.getSellerCode());
            paymentInfo.setPreferredMethod("BANK_TRANSFER");
            paymentInfo.setCurrency(seller.getCurrency());

            PaymentMethodd method = new PaymentMethodd();
            method.setPaymentInfo(paymentInfo);
            method.setExternalProvider("BANK_TRANSFER");
            method.setAccountNumber(command.getBankAccountNumber());
            method.setIfscCode(command.getIfscCode());
            method.setAccountHolderName(command.getAccountHolderName());
            method.setBankName("Unknown");
            method.setPrimary(true);
            method.setStatus("PENDING_VERIFICATION");

            List<PaymentMethodd> methods = new ArrayList<>();
            methods.add(method);
            paymentInfo.setPaymentMethods(methods);

            List<SellerPaymentInfo> paymentInfos = new ArrayList<>();
            paymentInfos.add(paymentInfo);
            seller.setPaymentInfos(paymentInfos);
        }

        return seller;
    }

}
