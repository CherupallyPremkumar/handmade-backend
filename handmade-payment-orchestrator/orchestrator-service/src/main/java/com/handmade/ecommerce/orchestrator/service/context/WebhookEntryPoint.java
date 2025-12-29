package com.handmade.ecommerce.orchestrator.service.context;

import com.handmade.ecommerce.orchestrator.WebhookExchange;
import org.chenile.owiz.OrchExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WebhookEntryPoint {

    @Autowired
    private OrchExecutor<WebhookExchange> webhookOrchExecutor;

    public WebhookExchange execute(WebhookExchange webhookExchange) {
        try {
            webhookOrchExecutor.execute(webhookExchange);
        } catch (Exception e) {
            webhookExchange.setException(e);
        }
        return webhookExchange;
    }
}
