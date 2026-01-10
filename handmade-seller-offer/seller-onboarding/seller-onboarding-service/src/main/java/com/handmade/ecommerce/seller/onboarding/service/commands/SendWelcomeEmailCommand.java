package com.handmade.ecommerce.seller.onboarding.service.commands;

import com.handmade.ecommerce.seller.onboarding.service.MarketingNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * OWIZ Command to send welcome email.
 */
@Component("SendWelcomeEmailCommand")
public class SendWelcomeEmailCommand extends BaseOrchCommand<OnboardingConfirmContext> {

    @Autowired
    private MarketingNotificationService marketingNotificationService;

    @Override
    protected void executeCommand(OnboardingConfirmContext context) throws Exception {
        marketingNotificationService.sendWelcomeEmail(context.getSellerId());
        context.addResult("sendWelcomeEmail", "SUCCESS");
    }
}
