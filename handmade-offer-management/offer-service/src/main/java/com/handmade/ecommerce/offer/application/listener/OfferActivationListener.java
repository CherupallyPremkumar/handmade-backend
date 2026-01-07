package com.handmade.ecommerce.offer.application.listener;

import com.handmade.ecommerce.compliance.event.ComplianceApprovedEvent;
import com.handmade.ecommerce.event.api.EventsSubscribedTo;
import com.handmade.ecommerce.inventory.event.InventoryAvailableEvent;
import com.handmade.ecommerce.offer.domain.aggregate.Offer;
import com.handmade.ecommerce.offer.domain.repository.OfferRepository;
import org.chenile.stm.STM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Signal Splicer for Offer Activation.
 * Reacts to Inventory and Compliance signals to move Offers through 
 * the "Buy Box Gate".
 */
@Component
public class OfferActivationListener {

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private STM<Offer> offerStm;

    @Transactional
    @EventsSubscribedTo({ "INVENTORY_AVAILABLE" })
    public void onInventoryAvailable(InventoryAvailableEvent event) {
        // Find all offers for this variant and seller
        List<Offer> offers = offerRepository.findByVariantId(event.getVariantId());
        
        for (Offer offer : offers) {
            if (offer.getSellerId().equals(event.getSellerId())) {
                // Signal the state machine that inventory is ready
                try {
                    // Record activity for auditability
                    offer.addActivity("INVENTORY_READY", "Inventory received: " + event.getAvailableQuantity());
                    
                    // We directly use the STM to process the signal
                    // The "Buy Box Gate" reconciliation logic will be in the action classes
                    offerStm.processById(offer.getId(), "INVENTORY_READY", null);
                } catch (Exception e) {
                    // Log error and continue with other offers
                    System.err.println("Failed to signal offer: " + offer.getOfferCode() + " Error: " + e.getMessage());
                }
            }
        }
    }

    @Transactional
    @EventsSubscribedTo({ "COMPLIANCE_APPROVED" })
    public void onComplianceApproved(ComplianceApprovedEvent event) {
        // Find all offers for this seller
        List<Offer> offers = offerRepository.findBySellerId(event.getSellerId());
        
        for (Offer offer : offers) {
            try {
                // Record activity for auditability
                offer.addActivity("COMPLIANCE_READY", "Seller compliance approved. Status: " + event.getStatus());
                
                // Signal the state machine that compliance is ready
                offerStm.processById(offer.getId(), "COMPLIANCE_READY", null);
            } catch (Exception e) {
                // Log error and continue
                System.err.println("Failed to signal offer: " + offer.getOfferCode() + " Error: " + e.getMessage());
            }
        }
    }
}
