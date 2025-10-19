package com.homebase.ecom.interceptors;

import com.homebase.ecom.constants.HeaderConstants;
import com.homebase.ecom.constants.OAuth2Constants;
import com.homebase.ecom.mybatis.customer.CustomerExists;
import com.homebase.ecom.service.CustomerValidationService;
import org.chenile.core.context.ChenileExchange;
import org.chenile.core.context.ContextContainer;
import org.chenile.core.interceptors.BaseChenileInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.DefaultOAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Interceptor that populates customer information in the Chenile exchange context.
 * Validates customer existence and stores user info for downstream processing.
 */
@Component
public class PopulateGuestOrCustomerInterceptor extends BaseChenileInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(PopulateGuestOrCustomerInterceptor.class);

    private final CustomerValidationService customerValidationService;
    @Autowired
    private ContextContainer contextContainer;

    @Autowired
    public PopulateGuestOrCustomerInterceptor(CustomerValidationService customerValidationService) {
        this.customerValidationService = customerValidationService;
    }

    @Override
    protected void doPreProcessing(ChenileExchange exchange) {
        logger.debug("Starting customer validation preprocessing");

        // Extract current user from security context
        Map<String, Object> userInfo = getCurrentUser()
                .orElseThrow(() -> {
                    logger.warn("Authentication failed: No valid user found in security context");
                    return new CustomerValidationException("Unauthenticated or invalid user");
                });

        String customerId = (String) userInfo.get(HeaderConstants.KEY_CUSTOMER_ID);
        logger.info("Processing request for customer: {}", customerId);

        // Validate customer existence using external service (enables @Cacheable)
        CustomerExists customerEntity = customerValidationService.validateCustomer(userInfo);

        if (customerEntity.isExists()) {
            logger.debug("Customer found in database: {}", customerId);
        } else {
            logger.info("Customer not found in database, may need registration: {}", customerId);
        }

        // Store in exchange for downstream use
        exchange.setHeader(HeaderConstants.HEADER_USER_INFO, userInfo);
        exchange.setHeader(HeaderConstants.HEADER_CUSTOMER_ENTITY, customerEntity);
        exchange.setHeader(HeaderConstants.HEADER_CUSTOMER_ID, customerId);
        contextContainer.setUserId(customerId);
        logger.debug("Customer validation preprocessing completed");
    }

    /**
     * Retrieves the current authenticated user from the security context.
     *
     * @return Optional containing user info map if authenticated, empty otherwise
     */
    protected Optional<Map<String, Object>> getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if (auth == null || !auth.isAuthenticated()) {
            logger.debug("No authenticated user found in security context");
            return Optional.empty();
        }

        Map<String, Object> userInfo = extractPrincipal(auth);
        return Optional.ofNullable(userInfo).filter(m -> !m.isEmpty());
    }

    /**
     * Extracts user information from various authentication principal types.
     * Supports OAuth2, OIDC, and JWT authentication.
     *
     * @param authentication Spring Security authentication object
     * @return Map containing user information (customerId, email, firstName)
     */
    private Map<String, Object> extractPrincipal(Authentication authentication) {
        Map<String, Object> userInfo = new HashMap<>();
        
        if (authentication == null || !authentication.isAuthenticated()) {
            return userInfo;
        }

        Object principal = authentication.getPrincipal();
        
        // Handle OAuth2 Resource Server (Opaque Token)
        if (principal instanceof DefaultOAuth2AuthenticatedPrincipal oauth2User) {
            String userId = oauth2User.getAttribute(OAuth2Constants.CLAIM_USER_ID);
            userInfo.put(HeaderConstants.KEY_CUSTOMER_ID, userId);
            userInfo.put(HeaderConstants.KEY_EMAIL, oauth2User.getAttribute(OAuth2Constants.CLAIM_EMAIL));
            userInfo.put(HeaderConstants.KEY_FIRST_NAME, oauth2User.getAttribute(OAuth2Constants.CLAIM_NAME));
            logger.debug("Extracted OAuth2 principal: {}", userId);
        }
        // Handle OIDC User (Keycloak with OIDC)
        else if (principal instanceof DefaultOidcUser oidcUser) {
            String sub = oidcUser.getAttribute(OAuth2Constants.CLAIM_SUB);
            userInfo.put(HeaderConstants.KEY_CUSTOMER_ID, sub);
            userInfo.put(HeaderConstants.KEY_EMAIL, oidcUser.getAttribute(OAuth2Constants.CLAIM_EMAIL));
            userInfo.put(HeaderConstants.KEY_FIRST_NAME, oidcUser.getAttribute(OAuth2Constants.CLAIM_GIVEN_NAME));
            logger.debug("Extracted OIDC principal: {}", sub);
        }
        // Handle JWT Token
        else if (principal instanceof Jwt jwt) {
            userInfo.put(HeaderConstants.KEY_CUSTOMER_ID, jwt.getClaimAsString(OAuth2Constants.CLAIM_SUB));
            userInfo.put(HeaderConstants.KEY_EMAIL, jwt.getClaimAsString(OAuth2Constants.CLAIM_EMAIL));
            userInfo.put(HeaderConstants.KEY_FIRST_NAME, jwt.getClaimAsString(OAuth2Constants.CLAIM_GIVEN_NAME));
            logger.debug("Extracted JWT principal: {}", jwt.getSubject());
        }
        else {
            logger.warn("Unsupported principal type: {}", principal.getClass().getName());
        }
        
        return userInfo;
    }

    /**
     * Custom exception thrown when customer validation fails.
     */
    public static class CustomerValidationException extends RuntimeException {
        public CustomerValidationException(String message) {
            super(message);
        }

        public CustomerValidationException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}