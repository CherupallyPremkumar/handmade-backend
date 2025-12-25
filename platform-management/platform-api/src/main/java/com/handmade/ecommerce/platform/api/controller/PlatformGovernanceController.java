package com.handmade.ecommerce.platform.api.controller;

import com.handmade.ecommerce.platform.api.dto.request.*;
import com.handmade.ecommerce.platform.api.dto.response.ApiResponse;
import com.handmade.ecommerce.platform.application.command.*;
import com.handmade.ecommerce.platform.application.port.in.PlatformGovernanceUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

/**
 * REST Controller for Platform Governance operations.
 * Exposes HTTP endpoints for platform management use cases.
 */
@RestController
@RequestMapping("/api/v1/platform")
@Tag(name = "Platform Governance", description = "Platform management and governance operations")
public class PlatformGovernanceController {

    private static final Logger log = LoggerFactory.getLogger(PlatformGovernanceController.class);
    
    private final PlatformGovernanceUseCase platformGovernanceUseCase;

    public PlatformGovernanceController(PlatformGovernanceUseCase platformGovernanceUseCase) {
        this.platformGovernanceUseCase = platformGovernanceUseCase;
    }

    /**
     * Activate the platform (Day 0 operation).
     */
    @PostMapping("/activate")
    @Operation(summary = "Activate Platform", description = "Activate the platform from PROVISIONING to LIVE state")
    public ResponseEntity<ApiResponse<Void>> activatePlatform(@Valid @RequestBody ActivatePlatformRequest request) {
        log.info("Activating platform - operator: {}", request.getOperatorId());
        
        ActivatePlatformCommand command = new ActivatePlatformCommand(
            request.getOperatorId(),
            request.getReason()
        );
        
        platformGovernanceUseCase.activatePlatform(command);
        
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("Platform activated successfully"));
    }

    /**
     * Revise commission structure.
     */
    @PostMapping("/commission")
    @Operation(summary = "Revise Commission", description = "Update platform commission rates and fees")
    public ResponseEntity<ApiResponse<Void>> reviseCommission(@Valid @RequestBody ReviseCommissionRequest request) {
        log.info("Revising commission structure - operator: {}, takeRate: {}", 
                 request.getOperatorId(), request.getNewTakeRate());
        
        ReviseCommissionCommand command = new ReviseCommissionCommand(
            request.getOperatorId(),
            request.getNewTakeRate(),
            request.getFlatFee(),
            request.getCurrency(),
            request.getReason()
        );
        
        platformGovernanceUseCase.reviseCommission(command);
        
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("Commission structure revised successfully"));
    }

    /**
     * Lock/restrict the platform.
     */
    @PostMapping("/lock")
    @Operation(summary = "Lock Platform", description = "Impose sanctions and restrict platform operations")
    public ResponseEntity<ApiResponse<Void>> lockPlatform(@Valid @RequestBody LockPlatformRequest request) {
        log.warn("Locking platform - operator: {}, reason: {}", 
                 request.getOperatorId(), request.getReason());
        
        LockPlatformCommand command = new LockPlatformCommand(
            request.getOperatorId(),
            request.getReason()
        );
        
        platformGovernanceUseCase.lockPlatform(command);
        
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("Platform locked successfully"));
    }

    /**
     * Update brand identity.
     */
    @PutMapping("/brand")
    @Operation(summary = "Update Brand Identity", description = "Update platform visual identity (logo, colors, email)")
    public ResponseEntity<ApiResponse<Void>> updateBrandIdentity(@Valid @RequestBody UpdateBrandIdentityRequest request) {
        log.info("Updating brand identity - operator: {}", request.getOperatorId());
        
        UpdateBrandIdentityCommand command = new UpdateBrandIdentityCommand(
            request.getOperatorId(),
            URI.create(request.getLogoUrl()),
            request.getPrimaryColor(),
            request.getSupportEmail()
        );
        
        platformGovernanceUseCase.updateBrandIdentity(command);
        
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("Brand identity updated successfully"));
    }

    /**
     * Publish legal terms.
     */
    @PostMapping("/legal-terms")
    @Operation(summary = "Publish Legal Terms", description = "Publish or update terms of service and privacy policy")
    public ResponseEntity<ApiResponse<Void>> publishLegalTerms(@Valid @RequestBody PublishLegalTermsRequest request) {
        log.info("Publishing legal terms - operator: {}", request.getOperatorId());
        
        PublishLegalTermsCommand command = new PublishLegalTermsCommand(
            request.getOperatorId(),
            request.getTermsUrl(),
            request.getPrivacyUrl()
        );
        
        platformGovernanceUseCase.publishLegalTerms(command);
        
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("Legal terms published successfully"));
    }
}
