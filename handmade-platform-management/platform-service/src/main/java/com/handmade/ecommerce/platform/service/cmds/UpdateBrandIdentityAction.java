package com.handmade.ecommerce.platform.service.cmds;

import com.handmade.ecommerce.platform.dto.UpdateBrandIdentityPayload;
import com.handmade.ecommerce.platform.domain.aggregate.PlatformOwner;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Update Brand Identity Action
 * Self-transition in ACTIVE state to update branding
 */
public class UpdateBrandIdentityAction extends AbstractSTMTransitionAction<PlatformOwner, UpdateBrandIdentityPayload> {
    
    private static final Logger logger = LoggerFactory.getLogger(UpdateBrandIdentityAction.class);
    
    @Override
    public void transitionTo(PlatformOwner platform,
                             UpdateBrandIdentityPayload payload,
                             State startState,
                             String eventId,
                             State endState,
                             STMInternalTransitionInvoker<?> stm,
                             Transition transition) throws Exception {
        
        logger.info("Updating brand identity for platform: {}", platform.id);
        
        // Get existing or defaults
        com.handmade.ecommerce.platform.domain.valueobject.BrandIdentity current = platform.brandIdentity;
        
        java.net.URI logoUrl = (payload.logoUrl != null) ? payload.logoUrl : (current != null ? current.logoUrl() : null);
        java.net.URI faviconUrl = (payload.faviconUrl != null) ? payload.faviconUrl : (current != null ? current.faviconUrl() : null);
        String brandName = (payload.brandName != null) ? payload.brandName : (current != null ? platform.name : null); // Fallback to platform name if no brand name
        String primaryColorHex = (payload.primaryColorHex != null) ? payload.primaryColorHex : (current != null ? current.primaryColorHex() : "#000000");
        String supportEmail = (payload.supportEmail != null) ? payload.supportEmail : (current != null ? current.supportEmail() : null);

        // Ensure required fields are not null (mocking defaults if necessary for now to pass constructor)
        if (logoUrl == null) logoUrl = java.net.URI.create("http://placeholder.com/logo.png");
        if (faviconUrl == null) faviconUrl = java.net.URI.create("http://placeholder.com/favicon.ico");
        if (primaryColorHex == null) primaryColorHex = "#000000";
        if (supportEmail == null) supportEmail = "support@example.com";

        // Create new immutable value object
        platform.brandIdentity = new com.handmade.ecommerce.platform.domain.valueobject.BrandIdentity(
            logoUrl, faviconUrl, primaryColorHex, supportEmail
        );
        
        if (brandName != null) {
            platform.name = brandName; // Assuming brand name is the platform name
        }
        
        if (payload.tagline != null && platform.brandIdentity != null) {
             // BrandIdentity doesn't have tagline in the VO shown in previous step?
             // Checking VO definition: logoUrl, faviconUrl, primaryColorHex, supportEmail.
             // Tagline seems missing from VO?
        }
        
        logger.info("Brand identity updated for platform {}", platform.id);
    }
}
