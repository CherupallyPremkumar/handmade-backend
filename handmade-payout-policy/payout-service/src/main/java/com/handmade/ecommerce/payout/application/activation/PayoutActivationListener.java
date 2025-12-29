package com.handmade.ecommerce.payout.application.activation;

import com.handmade.ecommerce.event.api.EventPublisher;
import com.handmade.ecommerce.orchestration.seller.command.InitPayoutProfileCommand;
import com.handmade.ecommerce.orchestration.seller.event.PayoutProfileInitializedEvent;
import com.handmade.ecommerce.payout.domain.entity.PayoutProfile;
import com.handmade.ecommerce.payout.repository.PayoutProfileRepository;
import org.chenile.http.annotation.ChenileController;
import org.chenile.http.annotation.EventsSubscribedTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Listener for Payout Activation commands.
 * Handles INIT_PAYOUT_PROFILE command emitted by the orchestrator.
 */
@Service("payoutActivationListener")
@ChenileController(value = "payoutActivationListener", serviceName = "payoutActivationListener")
public class PayoutActivationListener {

    @Autowired
    private PayoutProfileRepository payoutProfileRepository;

    @Autowired
    private EventPublisher eventPublisher;

    /**
     * Responds to INIT_PAYOUT_PROFILE command.
     * Initializes default payout settings for the new seller.
     */
    @EventsSubscribedTo({ "INIT_PAYOUT_PROFILE" })
    public void onInitPayoutProfile(InitPayoutProfileCommand command) {
        PayoutProfile profile = new PayoutProfile();
        profile.setSellerId(command.getSellerId());
        profile.setPreferredFrequency("WEEKLY"); // Default frequency
        profile.setPayoutMethod("BANK_TRANSFER"); // Default method

        payoutProfileRepository.save(profile);

        // Notify orchestrator of success
        PayoutProfileInitializedEvent event = new PayoutProfileInitializedEvent(
                command.getWorkflowId(),
                command.getSellerId());
        eventPublisher.publish("PAYOUT_PROFILE_INITIALIZED", event);
    }
}
