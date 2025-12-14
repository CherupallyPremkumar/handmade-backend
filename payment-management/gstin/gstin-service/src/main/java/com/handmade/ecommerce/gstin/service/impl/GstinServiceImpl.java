package com.handmade.ecommerce.gstin.service.impl;

import com.handmade.ecommerce.tax.TaxServiceRegistry;
import com.handmade.ecommerce.tax.service.TaxCentricService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.handmade.ecommerce.gstin.model.Gstin;
import com.handmade.ecommerce.gstin.service.GstinService;
import com.handmade.ecommerce.gstin.configuration.dao.GstinRepository;
import org.chenile.base.exception.NotFoundException;
import org.chenile.base.exception.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * GSTIN Service Implementation for India
 * Implements both GstinService (module-specific) and TaxCentricService (tax registry)
 */
@Service("IN")
public class GstinServiceImpl implements GstinService, TaxCentricService {
    private static final Logger logger = LoggerFactory.getLogger(GstinServiceImpl.class);


    @Autowired
    GstinRepository gstinRepository;


    @Override
    public Gstin save(Gstin entity) {
        entity = gstinRepository.save(entity);
        return entity;
    }

    @Override
    public Gstin retrieve(String id) {
        Optional<Gstin> entity = gstinRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500, "Unable to find gstin with ID " + id);
    }

    /**
     * Validates GSTIN format according to Indian GST rules
     * Format: XXAAAAA9999A1Z9
     * - XX: State code (01-37)
     * - AAAAA: First 5 characters of PAN
     * - 9999: Registration number
     * - A: Entity type
     * - 1: Default 'Z'
     * - Z: Checksum digit
     * - 9: Additional digit
     */
    @Override
    public boolean validateFormat(String gstin) {
        if (gstin == null || gstin.trim().isEmpty()) {
            logger.warn("GSTIN is null or empty");
            return false;
        }
        
        // Remove whitespace and convert to uppercase
        gstin = gstin.trim().toUpperCase();
        
        // Length check (must be exactly 15 characters)
        if (gstin.length() != 15) {
            logger.warn("GSTIN length is invalid: {} (expected 15)", gstin.length());
            return false;
        }
        
        // Format validation: 22AAAAA0000A1Z5
        String gstinRegex = "^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$";
        if (!gstin.matches(gstinRegex)) {
            logger.warn("GSTIN format is invalid: {}", gstin);
            return false;
        }
        
        // Validate state code (01-37, representing Indian states and union territories)
        try {
            int stateCode = Integer.parseInt(gstin.substring(0, 2));
            if (stateCode < 1 || stateCode > 37) {
                logger.warn("GSTIN state code is invalid: {}", stateCode);
                return false;
            }
        } catch (NumberFormatException e) {
            logger.error("Failed to parse GSTIN state code", e);
            return false;
        }
        
        // Validate PAN embedded in GSTIN (characters 3-12)
        // PAN format: AAAAA9999A
        // - First 3 chars: Alphabetic series
        // - 4th char: Entity type (P=Person, C=Company, H=HUF, F=Firm, etc.)
        // - 5th char: First letter of surname/name
        // - Next 4 chars: Sequential number
        // - Last char: Alphabetic check digit
        String pan = gstin.substring(2, 12);
        String panRegex = "^[A-Z]{3}[ABCFGHLJPTK]{1}[A-Z]{1}[0-9]{4}[A-Z]{1}$";
        if (!pan.matches(panRegex)) {
            logger.warn("Embedded PAN in GSTIN is invalid: {}", pan);
            return false;
        }
        
        // Additional validation: 13th character should be 'Z' (already validated in regex)
        // 14th character is the checksum digit (optional advanced validation)
        
        logger.info("GSTIN validation successful: {}", gstin);
        return true;
    }

    /**
     * Implementation of TaxCentricService interface
     * Validates tax ID and throws exception if invalid
     */
    @Override
    public void validateTaxId(String taxId) {
        logger.info("Validating GSTIN: {}", taxId);
        
        if (!validateFormat(taxId)) {
            String errorMessage = buildValidationErrorMessage(taxId);
            logger.error("GSTIN validation failed: {}", errorMessage);
            throw new ServerException(1501, errorMessage);
        }
        
        logger.info("GSTIN validation passed: {}", taxId);
    }

    @Override
    public boolean verifyWithTaxPortal(String taxId) {
        return false;
    }

    /**
     * Builds a detailed error message for GSTIN validation failures
     */
    private String buildValidationErrorMessage(String gstin) {
        if (gstin == null || gstin.trim().isEmpty()) {
            return "GSTIN cannot be null or empty";
        }
        
        gstin = gstin.trim().toUpperCase();
        
        if (gstin.length() != 15) {
            return String.format("GSTIN must be exactly 15 characters (provided: %d)", gstin.length());
        }
        
        try {
            int stateCode = Integer.parseInt(gstin.substring(0, 2));
            if (stateCode < 1 || stateCode > 37) {
                return String.format("Invalid state code: %02d (must be between 01-37)", stateCode);
            }
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            return "Invalid GSTIN format: state code must be numeric";
        }
        
        String pan = gstin.substring(2, 12);
        String panRegex = "^[A-Z]{3}[ABCFGHLJPTK]{1}[A-Z]{1}[0-9]{4}[A-Z]{1}$";
        if (!pan.matches(panRegex)) {
            return String.format("Invalid PAN embedded in GSTIN: %s", pan);
        }
        
        return String.format("Invalid GSTIN format: %s. Expected format: XXAAAAA9999A1Z9", gstin);
    }

    /**
     * Verifies GSTIN with GST Portal API (placeholder for future implementation)
     * This would call the actual GST Portal API or third-party service
     */
    public boolean verifyWithGstPortal(String gstin) {
        // TODO: Implement actual API call to GST Portal
        // Options:
        // 1. Direct GST Portal API (requires authentication)
        // 2. Third-party services like ClearTax, MasterIndia
        // 3. Government GSTN API
        
        logger.warn("GST Portal verification not yet implemented for GSTIN: {}", gstin);
        return false;
    }

    /**
     * Checks if GSTIN already exists in the database
     */
    public boolean existsByGstin(String gstin) {
        logger.info("Checking existence for GSTIN: {}", gstin);
        return gstinRepository.existsByGstinNumber(gstin);
    }
}