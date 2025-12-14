package com.handmade.ecommerce.seller.service.impl;

import com.handmade.ecommerce.seller.dto.SellerCreateRequest;
import com.handmade.ecommerce.seller.dto.command.AddSellerPaymentPayload;
import com.handmade.ecommerce.seller.dto.command.AddSellerTaxPayload;
import com.handmade.ecommerce.seller.dto.command.CreateSellerRequest;
import com.handmade.ecommerce.seller.model.Seller;
import com.handmade.ecommerce.seller.model.SellerPaymentInfo;
import com.handmade.ecommerce.seller.model.TaxInfo;
import com.handmade.ecommerce.seller.service.SellerSevice;
import com.handmade.ecommerce.seller.service.SellerValidationService;
import com.handmade.ecommerce.seller.service.errorcodes.ErrorCodes;
import org.chenile.base.exception.ServerException;
import org.chenile.stm.STM;
import org.chenile.stm.impl.STMActionsInfoProvider;
import org.chenile.utils.entity.service.EntityStore;
import org.chenile.workflow.dto.StateEntityServiceResponse;
import org.chenile.workflow.service.impl.StateEntityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

public class SellerServiceImpl extends StateEntityServiceImpl<Seller> implements SellerService {

    @Autowired
    SellerValidationService sellerValidationService;

    public SellerServiceImpl(STM<Seller> stm, STMActionsInfoProvider stmActionsInfoProvider,
            EntityStore<Seller> entityStore) {
        super(stm, stmActionsInfoProvider, entityStore);
    }

    @Override
    public StateEntityServiceResponse<Seller> create(Seller entity) {
        // valiadte seller details
        return super.create(entity);
    }

    @Override
    public StateEntityServiceResponse<Seller> process(Seller entity, String event, Object payload) {
        return super.process(entity, event, payload);
    }

    @Override
    public StateEntityServiceResponse<Seller> processById(String id, String event, Object payload) {
        if (payload == null) {
            throw new ServerException(ErrorCodes.SELLER_REQUEST_PAYLOAD_IS_NULL.getSubError(),
                    new Object[] { payload });
        }
        if (Objects.equals(id, "0")) {
            SellerCreateRequest sellerRequest = (SellerCreateRequest) payload;
            Seller seller = new Seller();
            makeSellerObject(seller, sellerRequest);
            StateEntityServiceResponse<Seller> createdSellerResponse = create(seller);
            Seller createdSeller = createdSellerResponse.getMutatedEntity();

            if (sellerRequest.getTaxInfos() != null && !sellerRequest.getTaxInfos().isEmpty()) {
                AddSellerTaxPayload addSellerTaxPayload = new AddSellerTaxPayload();
                addSellerTaxPayload.setTaxInfoRequests(sellerRequest.getTaxInfos());
                super.processById(createdSeller.getId(), "addTax", addSellerTaxPayload);
            }
            if (sellerRequest.getPaymentInfos() != null && !sellerRequest.getPaymentInfos().isEmpty()) {
                AddSellerPaymentPayload addSellerPaymentPayload = new AddSellerPaymentPayload();
                addSellerPaymentPayload.setPaymentInfoRequests(sellerRequest.getPaymentInfos());
                super.processById(createdSeller.getId(), "addPayment", addSellerPaymentPayload);
            }
            return super.process(createdSeller, null, null);
        }
        return super.processById(id, event, payload);
    }

    private void makeSellerObject(Seller seller, SellerCreateRequest req) {
        seller.setSellerCode(req.getSellerCode());
        seller.setSellerName(req.getSellerName());
        seller.setDisplayName(req.getDisplayName());
        seller.setUrlPath(req.getUrlPath());
        seller.setLogoUrl(req.getLogoUrl());
        seller.setCurrency(req.getCurrency());
        seller.setLocale(req.getLocale());
        seller.setTimezone(req.getTimezone());
        seller.setSupportEmail(req.getSupportEmail());
        seller.setSupportPhone(req.getSupportPhone());
        seller.setContactPerson(req.getContactPerson());
        seller.setContactEmail(req.getContactEmail());
        seller.setContactPhone(req.getContactPhone());
        seller.setBusinessType(req.getBusinessType());
    }

    @Override
    public void validate(CreateSellerRequest request) {
        sellerValidationService.validateSellerCreation(request);
    }
}
