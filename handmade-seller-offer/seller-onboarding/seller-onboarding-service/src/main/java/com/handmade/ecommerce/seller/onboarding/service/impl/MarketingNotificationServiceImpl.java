package com.handmade.ecommerce.seller.onboarding.service.impl;

import com.handmade.ecommerce.seller.onboarding.service.MarketingNotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MarketingNotificationServiceImpl implements MarketingNotificationService {
    private static final Logger logger = LoggerFactory.getLogger(MarketingNotificationServiceImpl.class);

    @Override
    public void sendWelcomeEmail(String sellerId) {
        logger.info("Sending Amazon-grade welcome email to seller: {}", sellerId);
    }
}
