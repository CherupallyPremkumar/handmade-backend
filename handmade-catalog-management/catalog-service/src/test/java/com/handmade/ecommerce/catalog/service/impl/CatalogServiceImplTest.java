package com.handmade.ecommerce.catalog.service.impl;

import com.handmade.ecommerce.catalog.model.CatalogItem;
import com.handmade.ecommerce.catalog.repository.CatalogItemRepository;
import com.handmade.ecommerce.product.dto.ExternalProductDto;
import com.handmade.ecommerce.product.service.ProductServiceClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CatalogServiceImpl Tests")
class CatalogServiceImplTest {

    @Mock
    private CatalogItemRepository catalogItemRepository;

    @Mock
    private ProductServiceClient productServiceClient;

    @InjectMocks
    private CatalogServiceImpl catalogService;

    private ExternalProductDto sampleProduct;
    private CatalogItem sampleCatalogItem;

    @BeforeEach
    void setUp() {
        sampleProduct = new ExternalProductDto();
        sampleProduct.setId("prod-001");
        sampleProduct.setName("Handwoven Basket");
        sampleProduct.setPrice(new BigDecimal("35.00"));
        sampleProduct.setActive(true);

        sampleCatalogItem = new CatalogItem();
        sampleCatalogItem.setProductId("prod-001");
        sampleCatalogItem.setName("Handwoven Basket");
        sampleCatalogItem.setPrice(new BigDecimal("35.00"));
        sampleCatalogItem.setFeatured(false);
        sampleCatalogItem.setActive(true);
    }

    @Test
    @DisplayName("Should create new catalog item when product does not exist")
    void createOrUpdateCatalogItem_NewProduct_CreatesCatalogItem() {
        // Arrange
        when(productServiceClient.getProduct("prod-001")).thenReturn(Optional.of(sampleProduct));
        when(catalogItemRepository.findByProductId("prod-001")).thenReturn(Optional.empty());
        when(catalogItemRepository.save(any(CatalogItem.class))).thenReturn(sampleCatalogItem);

        // Act
        catalogService.createOrUpdateCatalogItem("prod-001");

        // Assert
        verify(catalogItemRepository).findByProductId("prod-001");
        verify(catalogItemRepository).save(argThat(item -> item.getProductId().equals("prod-001") &&
                item.getName().equals("Handwoven Basket") &&
                item.getPrice().compareTo(new BigDecimal("35.00")) == 0));
    }

    @Test
    @DisplayName("Should update existing catalog item when product exists")
    void createOrUpdateCatalogItem_ExistingProduct_UpdatesCatalogItem() {
        // Arrange
        CatalogItem existingItem = new CatalogItem();
        existingItem.setProductId("prod-001");
        existingItem.setName("Old Name");
        existingItem.setPrice(new BigDecimal("25.00"));
        existingItem.setFeatured(true);
        existingItem.setDisplayOrder(1);

        when(productServiceClient.getProduct("prod-001")).thenReturn(Optional.of(sampleProduct));
        when(catalogItemRepository.findByProductId("prod-001")).thenReturn(Optional.of(existingItem));
        when(catalogItemRepository.save(any(CatalogItem.class))).thenReturn(existingItem);

        // Act
        catalogService.createOrUpdateCatalogItem("prod-001");

        // Assert
        verify(catalogItemRepository).save(argThat(item -> item.getName().equals("Handwoven Basket") &&
                item.getPrice().compareTo(new BigDecimal("35.00")) == 0 &&
                item.getFeatured() == true && // Preserved
                item.getDisplayOrder() == 1 // Preserved
        ));
    }

    @Test
    @DisplayName("Should preserve merchandising overrides when updating")
    void createOrUpdateCatalogItem_PreservesMerchandisingOverrides() {
        // Arrange
        CatalogItem existingItem = new CatalogItem();
        existingItem.setProductId("prod-001");
        existingItem.setFeatured(true);
        existingItem.setDisplayOrder(5);
        existingItem.getTags().add("bestseller");

        when(productServiceClient.getProduct("prod-001")).thenReturn(Optional.of(sampleProduct));
        when(catalogItemRepository.findByProductId("prod-001")).thenReturn(Optional.of(existingItem));
        when(catalogItemRepository.save(any(CatalogItem.class))).thenReturn(existingItem);

        // Act
        catalogService.createOrUpdateCatalogItem("prod-001");

        // Assert
        verify(catalogItemRepository).save(argThat(item -> item.getFeatured() == true &&
                item.getDisplayOrder() == 5 &&
                item.getTags().contains("bestseller")));
    }

    @Test
    @DisplayName("Should throw exception when product not found")
    void createOrUpdateCatalogItem_ProductNotFound_ThrowsException() {
        // Arrange
        when(productServiceClient.getProduct("prod-999")).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> catalogService.createOrUpdateCatalogItem("prod-999"))
                .isInstanceOf(RuntimeException.class);

        verify(catalogItemRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should not change active when product is in stock")
    void updateVisibility_InStock_DoesNotChangeActive() {
        // Arrange
        CatalogItem item = new CatalogItem();
        item.setProductId("prod-001");
        item.setActive(true);

        when(catalogItemRepository.findByProductId("prod-001")).thenReturn(Optional.of(item));
        when(catalogItemRepository.save(any(CatalogItem.class))).thenReturn(item);

        // Act
        catalogService.updateVisibility("prod-001", 10);

        // Assert
        // Current implementation only sets active=false when quantity<=0
        // It does NOT set active=true when quantity>0
        verify(catalogItemRepository).save(item);
    }

    @Test
    @DisplayName("Should set active to false when product is out of stock")
    void updateVisibility_OutOfStock_SetsActiveFalse() {
        // Arrange
        CatalogItem item = new CatalogItem();
        item.setProductId("prod-001");
        item.setActive(true);

        when(catalogItemRepository.findByProductId("prod-001")).thenReturn(Optional.of(item));
        when(catalogItemRepository.save(any(CatalogItem.class))).thenReturn(item);

        // Act
        catalogService.updateVisibility("prod-001", 0);

        // Assert
        verify(catalogItemRepository).save(argThat(catalogItem -> catalogItem.getActive() == false));
    }

    @Test
    @DisplayName("Should handle updateVisibility when catalog item does not exist")
    void updateVisibility_ItemNotFound_DoesNotThrowException() {
        // Arrange
        when(catalogItemRepository.findByProductId("prod-999")).thenReturn(Optional.empty());

        // Act & Assert
        assertThatCode(() -> catalogService.updateVisibility("prod-999", 10))
                .doesNotThrowAnyException();

        verify(catalogItemRepository, never()).save(any());
    }
}
