package com.handmade.ecommerce.platform.application.activation;

import com.handmade.ecommerce.event.api.EventPublisher;
import com.handmade.ecommerce.orchestration.seller.command.CreateStoreCommand;
import com.handmade.ecommerce.orchestration.seller.event.StoreCreatedEvent;
import com.handmade.ecommerce.platform.configuration.dao.StoreRepository;
import com.handmade.ecommerce.platform.domain.aggregate.Store;
import org.chenile.http.annotation.ChenileController;
import org.chenile.http.annotation.EventsSubscribedTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Listener for Platform Activation commands.
 * Handles CREATE_STORE command emitted by the orchestrator.
 */
@Service("platformActivationListener")
@ChenileController(value = "platformActivationListener", serviceName = "platformActivationListener")
public class PlatformActivationListener {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private EventPublisher eventPublisher;

    /**
     * Responds to CREATE_STORE command.
     * Registers a new storefront in the platform directory.
     */
    @EventsSubscribedTo({ "CREATE_STORE" })
    public void onCreateStore(CreateStoreCommand command) {
        Store store = new Store();
        store.setSellerId(command.getSellerId());
        store.setStoreName("Store for " + command.getSellerId());
        store.setActive(true);
        store.setActivatedAt(LocalDateTime.now());

        storeRepository.save(store);

        // Notify orchestrator of success
        StoreCreatedEvent event = new StoreCreatedEvent(
                command.getWorkflowId(),
                command.getSellerId(),
                store.getId());
        eventPublisher.publish("STORE_CREATED", event);
    }
}
