package com.handmade.ecommerce.sellerorch.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.handmade.ecommerce.command.CreateSellerCommand;
import org.chenile.base.exception.ErrorNumException;
import org.chenile.base.exception.ServerException;
import org.chenile.base.response.ResponseMessage;
import org.chenile.base.response.WarningAware;
import org.chenile.core.errorcodes.ErrorCodes;
import org.chenile.owiz.impl.ChainContext;
import org.chenile.owiz.impl.ChainContextContainer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SellerContext is the OWIZ workflow context for seller orchestration.
 * It carries all seller-related details and workflow metadata.
 */
public class SellerContext extends HashMap<String, Object> implements ChainContextContainer<SellerContext>, WarningAware {
    @JsonIgnore
    private ChainContext<SellerContext> chainContext;
    private CreateSellerCommand requestSeller;
    private ErrorNumException exception;
    private int httpResponseStatusCode;
    private List<ResponseMessage> responseMessages;
    private List<ResponseMessage> warningMessages = new ArrayList<>();
    private Map<String, Object> additionalData = new HashMap<>();

    @Override
    public void setChainContext(ChainContext<SellerContext> chainContext) {
        this.chainContext = chainContext;
    }

    @Override
    public ChainContext<SellerContext> getChainContext() {
        return this.chainContext;
    }

    @Override
    public List<ResponseMessage> getWarningMessages() {
        return warningMessages;
    }

    @Override
    public void addWarningMessage(ResponseMessage m) {
        this.warningMessages.add(m);
    }

    @Override
    public void removeAllWarnings() {
        this.warningMessages.clear();
    }

    public void setException(Throwable e) {
        if (e == null) {
            this.exception = null;
            this.responseMessages = new ArrayList<>();
            return;
        }
        ErrorNumException ene = createErrorNumExceptionIfRequired(e);
        if (this.responseMessages == null) {
            this.responseMessages = new ArrayList<>();
        }
        if (this.exception == null) this.exception = ene;
        this.responseMessages.addAll(ene.getErrors());
    }

    private ErrorNumException createErrorNumExceptionIfRequired(Throwable e) {
        if (e instanceof ErrorNumException errorNumException) return errorNumException;
        return new ServerException(ErrorCodes.SERVICE_EXCEPTION.getSubError(),
                new Object[]{e.getMessage()}, e);
    }

    public void putData(String key, Object value) {
        this.additionalData.put(key, value);
    }

    public Object getData(String key) {
        return this.additionalData.get(key);
    }

    public CreateSellerCommand getRequestSeller() {
        return requestSeller;
    }

    public void setRequestSeller(CreateSellerCommand requestSeller) {
        this.requestSeller = requestSeller;
    }

    public void setWarningMessages(List<ResponseMessage> warningMessages) {
        this.warningMessages = warningMessages;
    }

    public Map<String, Object> getAdditionalData() {
        return additionalData;
    }

    public void setAdditionalData(Map<String, Object> additionalData) {
        this.additionalData = additionalData;
    }
}
