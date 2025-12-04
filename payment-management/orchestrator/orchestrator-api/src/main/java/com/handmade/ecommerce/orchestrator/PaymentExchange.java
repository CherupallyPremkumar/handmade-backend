package com.handmade.ecommerce.orchestrator;

import com.handmade.ecommerce.command.CheckoutSessionResponse;
import com.handmade.ecommerce.command.CreatePaymentRequest;
import com.handmade.ecommerce.command.PaymentResponse;
import org.chenile.base.exception.ErrorNumException;
import org.chenile.base.exception.ServerException;
import org.chenile.base.response.ResponseMessage;
import org.chenile.base.response.WarningAware;
import org.chenile.core.context.ChenileExchange;
import org.chenile.core.errorcodes.ErrorCodes;
import org.chenile.owiz.impl.ChainContext;
import org.chenile.owiz.impl.ChainContextContainer;

import java.util.ArrayList;
import java.util.List;

public class PaymentExchange implements ChainContextContainer<ChenileExchange>,
        WarningAware {

    private static final long serialVersionUID = 1L;

    private boolean idempotentRequest;

    private CreatePaymentRequest request;
    private PaymentResponse response;
    private CheckoutSessionResponse checkoutSessionResponse;
    private ErrorNumException exception;

    // Risk assessment fields
    private Double riskScore;
    private String riskLevel;
    private String riskReason;

    private int httpResponseStatusCode;
    /**
     * contains all errors and warnings
     */
    private List<ResponseMessage> responseMessages;

    @Override
    public List<ResponseMessage> getWarningMessages() {
        return List.of();
    }

    @Override
    public void addWarningMessage(ResponseMessage m) {

    }

    @Override
    public void removeAllWarnings() {

    }

    @Override
    public void setChainContext(ChainContext<ChenileExchange> chainContext) {

    }

    @Override
    public ChainContext<ChenileExchange> getChainContext() {
        return null;
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
        // use this as the exception only if an existing exception does not exist
        // do not replace the existing exception with this
        if (this.exception == null)
            this.exception = ene;
        // However, copy the errors from this exception to the current collection of
        // errors
        this.responseMessages.addAll(ene.getErrors());
    }

    private ErrorNumException createErrorNumExceptionIfRequired(Throwable e) {
        if (e instanceof ErrorNumException errorNumException)
            return errorNumException;
        return new ServerException(ErrorCodes.SERVICE_EXCEPTION.getSubError(),
                new Object[] { e.getMessage() }, e);
    }

    public CreatePaymentRequest getRequest() {
        return request;
    }

    public void setRequest(CreatePaymentRequest request) {
        this.request = request;
    }

    public PaymentResponse getResponse() {
        return response;
    }

    public void setResponse(PaymentResponse response) {
        this.response = response;
    }

    public boolean isIdempotentRequest() {
        return idempotentRequest;
    }

    public void setIdempotentRequest(boolean idempotentRequest) {
        this.idempotentRequest = idempotentRequest;
    }

    public Double getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(Double riskScore) {
        this.riskScore = riskScore;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public String getRiskReason() {
        return riskReason;
    }

    public void setRiskReason(String riskReason) {
        this.riskReason = riskReason;
    }

    public CheckoutSessionResponse getCheckoutSessionResponse() {
        return checkoutSessionResponse;
    }

    public void setCheckoutSessionResponse(CheckoutSessionResponse checkoutSessionResponse) {
        this.checkoutSessionResponse = checkoutSessionResponse;
    }
}
