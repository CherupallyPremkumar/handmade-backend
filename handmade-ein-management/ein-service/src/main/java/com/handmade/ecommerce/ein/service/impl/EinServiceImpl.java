package com.handmade.ecommerce.ein.service.impl;

import com.handmade.ecommerce.tax.service.TaxCentricService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.handmade.ecommerce.ein.model.Ein;
import com.handmade.ecommerce.ein.service.EinService;
import com.handmade.ecommerce.ein.configuration.dao.EinRepository;
import org.chenile.base.exception.NotFoundException;
import org.chenile.base.exception.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.HashSet;

/**
 * EIN Service Implementation for USA
 * Implements both EinService (module-specific) and TaxCentricService (tax registry)
 */
@Service("US")
public class EinServiceImpl implements EinService, TaxCentricService {
    private static final Logger logger = LoggerFactory.getLogger(EinServiceImpl.class);
    
    @Autowired
    EinRepository einRepository;
    
    // Valid EIN campus codes (first 2 digits)
    // These are assigned by IRS based on geographic location
    private static final Set<String> VALID_CAMPUS_CODES = new HashSet<>();
    
    static {
        // Populate valid campus codes
        // 01-06: Andover, MA
        for (int i = 1; i <= 6; i++) {
            VALID_CAMPUS_CODES.add(String.format("%02d", i));
        }
        // 10-16: Atlanta, GA
        for (int i = 10; i <= 16; i++) {
            VALID_CAMPUS_CODES.add(String.format("%02d", i));
        }
        // 20-27: Brookhaven, NY
        for (int i = 20; i <= 27; i++) {
            VALID_CAMPUS_CODES.add(String.format("%02d", i));
        }
        // 30-39: Cincinnati, OH
        for (int i = 30; i <= 39; i++) {
            VALID_CAMPUS_CODES.add(String.format("%02d", i));
        }
        // 40-48: Fresno, CA
        for (int i = 40; i <= 48; i++) {
            VALID_CAMPUS_CODES.add(String.format("%02d", i));
        }
        // 50-59: Kansas City, MO
        for (int i = 50; i <= 59; i++) {
            VALID_CAMPUS_CODES.add(String.format("%02d", i));
        }
        // 60-67: Philadelphia, PA
        for (int i = 60; i <= 67; i++) {
            VALID_CAMPUS_CODES.add(String.format("%02d", i));
        }
        // 71-77: Memphis, TN
        for (int i = 71; i <= 77; i++) {
            VALID_CAMPUS_CODES.add(String.format("%02d", i));
        }
        // 80-88: Ogden, UT
        for (int i = 80; i <= 88; i++) {
            VALID_CAMPUS_CODES.add(String.format("%02d", i));
        }
        // 90-99: Internet/Online applications
        for (int i = 90; i <= 99; i++) {
            VALID_CAMPUS_CODES.add(String.format("%02d", i));
        }
    }

    @Override
    public Ein save(Ein entity) {
        entity = einRepository.save(entity);
        return entity;
    }

    @Override
    public Ein retrieve(String id) {
        Optional<Ein> entity = einRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500, "Unable to find EIN with ID " + id);
    }

    /**
     * Validates EIN format according to IRS rules
     * Format: XX-XXXXXXX (9 digits total)
     * - XX: Campus code (2 digits)
     * - XXXXXXX: Sequential number (7 digits)
     */
    @Override
    public boolean validateFormat(String ein) {
        if (ein == null || ein.trim().isEmpty()) {
            logger.warn("EIN is null or empty");
            return false;
        }
        
        // Remove whitespace and convert to uppercase
        ein = ein.trim().toUpperCase();
        
        // Remove hyphen if present for validation
        String cleanEin = ein.replace("-", "");
        
        // Length check (must be exactly 9 digits)
        if (cleanEin.length() != 9) {
            logger.warn("EIN length is invalid: {} (expected 9 digits)", cleanEin.length());
            return false;
        }
        
        // Format validation: must be all digits
        if (!cleanEin.matches("^[0-9]{9}$")) {
            logger.warn("EIN format is invalid: {} (must be 9 digits)", cleanEin);
            return false;
        }
        
        // Validate campus code (first 2 digits)
        String campusCode = cleanEin.substring(0, 2);
        if (!VALID_CAMPUS_CODES.contains(campusCode)) {
            logger.warn("EIN campus code is invalid: {}", campusCode);
            return false;
        }
        
        // EIN cannot be all zeros
        if (cleanEin.equals("000000000")) {
            logger.warn("EIN cannot be all zeros");
            return false;
        }
        
        logger.info("EIN validation successful: {}", formatEin(cleanEin));
        return true;
    }

    /**
     * Implementation of TaxCentricService interface
     * Validates tax ID and throws exception if invalid
     */
    @Override
    public void validateTaxId(String taxId) {
        logger.info("Validating EIN: {}", taxId);
        
        if (!validateFormat(taxId)) {
            String errorMessage = buildValidationErrorMessage(taxId);
            logger.error("EIN validation failed: {}", errorMessage);
            throw new ServerException(1503, errorMessage);
        }
        
        logger.info("EIN validation passed: {}", taxId);
    }

    /**
     * Verifies EIN with IRS or third-party service
     * Note: IRS does not provide a free public API for EIN verification
     */
    @Override
    public boolean verifyWithTaxPortal(String ein) {
        // TODO: Implement actual verification
        // Options:
        // 1. Third-party services (TaxBandits, Melissa Data, etc.)
        // 2. Manual verification via IRS Form SS-4
        // 3. IRS Business Master File (BMF) access (requires authorization)
        
        logger.warn("IRS verification not yet implemented for EIN: {}", ein);
        return false;
    }

    /**
     * Builds a detailed error message for EIN validation failures
     */
    private String buildValidationErrorMessage(String ein) {
        if (ein == null || ein.trim().isEmpty()) {
            return "EIN cannot be null or empty";
        }
        
        ein = ein.trim().replace("-", "");
        
        if (ein.length() != 9) {
            return String.format("EIN must be exactly 9 digits (provided: %d)", ein.length());
        }
        
        if (!ein.matches("^[0-9]{9}$")) {
            return "EIN must contain only numeric digits";
        }
        
        String campusCode = ein.substring(0, 2);
        if (!VALID_CAMPUS_CODES.contains(campusCode)) {
            return String.format("Invalid EIN campus code: %s. Valid ranges: 01-06, 10-16, 20-27, 30-39, 40-48, 50-59, 60-67, 71-77, 80-88, 90-99", campusCode);
        }
        
        if (ein.equals("000000000")) {
            return "EIN cannot be all zeros";
        }
        
        return String.format("Invalid EIN format: %s. Expected format: XX-XXXXXXX (9 digits)", formatEin(ein));
    }

    /**
     * Formats EIN with hyphen for display
     */
    private String formatEin(String ein) {
        if (ein == null || ein.length() != 9) {
            return ein;
        }
        return ein.substring(0, 2) + "-" + ein.substring(2);
    }

    /**
     * Checks if EIN already exists in the database
     */
    public boolean existsByEin(String ein) {
        logger.info("Checking existence for EIN: {}", ein);
        return einRepository.existsByEinNumber(ein.replace("-", ""));
    }

    /**
     * Gets the IRS campus location based on campus code
     */
    public String getCampusLocation(String ein) {
        String cleanEin = ein.replace("-", "");
        if (cleanEin.length() < 2) {
            return "Unknown";
        }
        
        String campusCode = cleanEin.substring(0, 2);
        int code = Integer.parseInt(campusCode);
        
        if (code >= 1 && code <= 6) return "Andover, MA";
        if (code >= 10 && code <= 16) return "Atlanta, GA";
        if (code >= 20 && code <= 27) return "Brookhaven, NY";
        if (code >= 30 && code <= 39) return "Cincinnati, OH";
        if (code >= 40 && code <= 48) return "Fresno, CA";
        if (code >= 50 && code <= 59) return "Kansas City, MO";
        if (code >= 60 && code <= 67) return "Philadelphia, PA";
        if (code >= 71 && code <= 77) return "Memphis, TN";
        if (code >= 80 && code <= 88) return "Ogden, UT";
        if (code >= 90 && code <= 99) return "Internet/Online";
        
        return "Unknown";
    }
}