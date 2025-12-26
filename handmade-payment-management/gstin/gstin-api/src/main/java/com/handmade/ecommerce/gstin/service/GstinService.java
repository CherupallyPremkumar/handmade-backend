package com.handmade.ecommerce.gstin.service;

import com.handmade.ecommerce.gstin.model.Gstin;

public interface GstinService {
    public Gstin save(Gstin gstin);
    public Gstin retrieve(String id);
    
    /**
     * Validates the format and checksum of a GSTIN.
     * @param gstin The GSTIN string to validate
     * @return true if valid, false otherwise
     * @throws IllegalArgumentException if invalid format (optional)
     */
    boolean validateFormat(String gstin);
}
