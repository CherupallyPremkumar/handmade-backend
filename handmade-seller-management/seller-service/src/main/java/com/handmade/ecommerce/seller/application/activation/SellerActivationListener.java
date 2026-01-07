package com.handmade.ecommerce.seller.application.activation;

import com.handmade.ecommerce.event.api.EventPublisher;
import com.handmade.ecommerce.seller.account.command.CreateSellerCommand;
import com.handmade.ecommerce.seller.account.event.SellerCreatedEvent;
import com.handmade.ecommerce.seller.api.SellerService;
import com.handmade.ecommerce.seller.domain.aggregate.Seller;
import org.chenile.http.annotation.ChenileController;
import org.chenile.http.annotation.EventsSubscribedTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Listener for Seller Activation commands.
 * Handles CREATE_SELLER command emitted by the orchestrator.
 */
@Service("sellerActivationListener")
@ChenileController(value = "sellerActivationListener", serviceName = "sellerActivationListener")
public class SellerActivationListener {

    @Autowired
    private SellerService sellerService;

    @Autowired
    private EventPublisher eventPublisher;

    /**
     * Responds to CREATE_SELLER command.
     * Provisions a new Seller record in the operational database.
     */
    @EventsSubscribedTo({ "CREATE_SELLER" })
    public void onCreateSeller(CreateSellerCommand command) {
        Seller seller = new Seller();
        seller.setId(command.getSellerId());
        seller.setContactEmail(command.getEmail());
        seller.setSellerName("Seller " + command.getSellerId());
        seller.setSellerCode("SELL-" + command.getSellerId());
        seller.setCurrency(command.getCountry().equals("IN") ? "INR" : "USD");
        seller.setUrlPath("seller-" + command.getSellerId());

        // Initial state for a newly provisioned seller
        // In a real system, this would trigger an STM transition
        sellerService.create(seller);

        // Notify orchestrator of success
        SellerCreatedEvent event = new SellerCreatedEvent(
                command.getWorkflowId(),
                command.getSellerId());
        eventPublisher.publish("SELLER_CREATED", event);
    }
}
