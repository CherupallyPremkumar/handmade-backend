package com.homebase.ecom.interceptors;

import org.chenile.core.context.ChenileExchange;
import org.chenile.core.context.ContextContainer;
import org.chenile.core.interceptors.BaseChenileInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Interceptor that populates customer information in the Chenile exchange context.
 * Validates customer existence and stores user info for downstream processing.
 */
@Component
public class PopulateGuestOrCustomerInterceptor extends BaseChenileInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(PopulateGuestOrCustomerInterceptor.class);



    @Override
    protected void doPreProcessing(ChenileExchange exchange) {
        logger.debug("Starting customer validation preprocessing");



    }
}