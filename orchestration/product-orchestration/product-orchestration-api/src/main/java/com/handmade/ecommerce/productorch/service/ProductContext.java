package com.handmade.ecommerce.productorch.service;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.handmade.ecommerce.command.CreateProductCommand;
import com.handmade.ecommerce.productorch.InvocationOrder;
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
 * ProductContext is the OWIZ workflow context for product orchestration.
 * It carries all product-related details, variants, prices, and workflow metadata.
 */
public class ProductContext extends HashMap<String,Object> implements InvocationOrder, ChainContextContainer<ProductContext>, WarningAware {
    @JsonIgnore
    private ChainContext<ProductContext> chainContext;
    public List<String> commandInvocationOrder = new ArrayList<>();
    private CreateProductCommand requestProduct;
    private ErrorNumException exception;
    private int httpResponseStatusCode;
    private List<ResponseMessage> responseMessages;

    private List<ResponseMessage> warningMessages = new ArrayList<>();
    private Map<String, Object> additionalData = new HashMap<>();

    @Override
    public void setChainContext(ChainContext<ProductContext> chainContext) {
        this.chainContext = chainContext;
    }

    @Override
    public ChainContext<ProductContext> getChainContext() {
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
        if (this.responseMessages == null){
            this.responseMessages = new ArrayList<>();
        }
        // use this as the exception only if an existing exception does not exist
        // do not replace the existing exception with this
        if (this.exception == null) this.exception = ene;
        // However, copy the errors from this exception to the current collection of errors
        this.responseMessages.addAll(ene.getErrors());
    }

    private ErrorNumException createErrorNumExceptionIfRequired(Throwable e){
        if ( e instanceof ErrorNumException errorNumException)  return errorNumException;
        return new ServerException(ErrorCodes.SERVICE_EXCEPTION.getSubError(),
                new Object[]{ e.getMessage()},e);
    }

    // ----- Additional Data -----
    public void putData(String key, Object value) { this.additionalData.put(key, value); }
    public Object getData(String key) { return this.additionalData.get(key); }

    public CreateProductCommand getRequestProduct() {
        return requestProduct;
    }

    public void setRequestProduct(CreateProductCommand requestProduct) {
        this.requestProduct = requestProduct;
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

    @Override
    public void addCommand(String commandId) {
        this.commandInvocationOrder.add(commandId);
    }

    @Override
    public List<String> getInvocationOrder() {
        return this.commandInvocationOrder;
    }

    // ----- Inner Variant Class -----

}