package com.handmade.ecommerce.catalog.service.listener;

import com.handmade.ecommerce.catalog.event.external.InventoryChangedEvent;
import com.handmade.ecommerce.catalog.event.external.ProductApprovedEvent;
import com.handmade.ecommerce.catalog.service.CatalogService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProductEventListener Tests")
class ProductEventListenerTest {

    @Mock
    private CatalogService catalogService;

    @InjectMocks
    private ProductEventListener listener;

    @Test
    @DisplayName("Should call catalog service when product approved event received")
    void onProductApproved_ValidEvent_CallsCatalogService() {
        // Arrange
        ProductApprovedEvent event = new ProductApprovedEvent("prod-001");

        // Act
        listener.onProductApproved(event);

        // Assert
        verify(catalogService).createOrUpdateCatalogItem("prod-001");
    }

    @Test
    @DisplayName("Should update visibility when inventory changed to in-stock")
    void onProductInventoryChanged_InStock_UpdatesVisibility() {
        // Arrange
        InventoryChangedEvent event = new InventoryChangedEvent("prod-001", 10);

        // Act
        listener.onProductInventoryChanged(event);

        // Assert
        verify(catalogService).updateVisibility("prod-001", 10);
    }

    @Test
    @DisplayName("Should update visibility when inventory changed to out-of-stock")
    void onProductInventoryChanged_OutOfStock_UpdatesVisibility() {
        // Arrange
        InventoryChangedEvent event = new InventoryChangedEvent("prod-001", 0);

        // Act
        listener.onProductInventoryChanged(event);

        // Assert
        verify(catalogService).updateVisibility("prod-001", 0);
    }

    @Test
    @DisplayName("Should handle null product ID gracefully")
    void onProductApproved_NullProductId_DoesNotCallService() {
        // Arrange
        ProductApprovedEvent event = new ProductApprovedEvent(null);

        // Act
        listener.onProductApproved(event);

        // Assert
        verify(catalogService, never()).createOrUpdateCatalogItem(any());
    }
}
