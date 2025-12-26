package com.handmade.ecommerce.orchestrator;

import com.handmade.ecommerce.command.WebhookRequest;
import com.handmade.ecommerce.command.WebhookPayload;
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

public class WebhookExchange implements ChainContextContainer<ChenileExchange>, WarningAware {

    private static final long serialVersionUID = 1L;

    private String request;
    private WebhookRequest convertedRequest;
    private WebhookPayload parsedPayload;
    private ErrorNumException exception;
    private List<ResponseMessage> responseMessages;

    public WebhookPayload getParsedPayload() {
        return parsedPayload;
    }

    public void setParsedPayload(WebhookPayload parsedPayload) {
        this.parsedPayload = parsedPayload;
    }

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
        if (this.exception == null)
            this.exception = ene;
        this.responseMessages.addAll(ene.getErrors());
    }

    private ErrorNumException createErrorNumExceptionIfRequired(Throwable e) {
        if (e instanceof ErrorNumException errorNumException)
            return errorNumException;
        return new ServerException(ErrorCodes.SERVICE_EXCEPTION.getSubError(),
                new Object[] { e.getMessage() }, e);
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public WebhookRequest getConvertedRequest() {
        return convertedRequest;
    }

    public void setConvertedRequest(WebhookRequest convertedRequest) {
        this.convertedRequest = convertedRequest;
    }
}
