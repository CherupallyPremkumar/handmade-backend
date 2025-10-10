package com.homebase.admin.service;

import com.homebase.admin.dto.CartItemDTO;
import com.homebase.admin.entity.*;
import com.homebase.admin.repository.CartItemRepository;
import com.homebase.admin.repository.CartRepository;
import com.homebase.admin.repository.CustomerRepository;
import com.homebase.admin.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final PriceCalculationService priceCalculationService;

    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository,
                       ProductRepository productRepository, CustomerRepository customerRepository,
                       PriceCalculationService priceCalculationService) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.priceCalculationService = priceCalculationService;
    }

    @Transactional(readOnly = true)
    public List<CartItemDTO> getCartItems(Long customerId, String tenantId) {
        Cart cart = getOrCreateCart(customerId, tenantId);
        return cart.getCartItems().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public CartItemDTO addToCart(Long customerId, Long productId, int quantity, String tenantId) {
        Cart cart = getOrCreateCart(customerId, tenantId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        // Check if item already exists in cart
        CartItem existingItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            // Update snapshot with current effective price
            updatePriceSnapshot(existingItem, product);
            cartItemRepository.save(existingItem);
            recalculateCartTotal(cart);
            return convertToDTO(existingItem);
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            // Capture price snapshot at time of adding
            updatePriceSnapshot(newItem, product);
            newItem.setTenantId(tenantId);
            
            cart.getCartItems().add(newItem);
            cartItemRepository.save(newItem);
            recalculateCartTotal(cart);
            return convertToDTO(newItem);
        }
    }
    
    /**
     * Update price snapshot for cart item
     * Captures current price, sale status, and original price
     */
    private void updatePriceSnapshot(CartItem item, Product product) {
        BigDecimal effectivePrice = priceCalculationService.getEffectivePrice(product);
        item.setSnapshotPrice(effectivePrice);
        item.setWasOnSale(product.isOnSale());
        item.setOriginalPrice(product.getPrice());
    }

    @Transactional
    public CartItemDTO updateCartItem(Long itemId, int quantity, String tenantId) {
        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Cart item not found"));
        
        if (!item.getTenantId().equals(tenantId)) {
            throw new IllegalArgumentException("Unauthorized access to cart item");
        }

        item.setQuantity(quantity);
        cartItemRepository.save(item);
        recalculateCartTotal(item.getCart());
        return convertToDTO(item);
    }

    @Transactional
    public void removeFromCart(Long itemId, String tenantId) {
        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Cart item not found"));
        
        if (!item.getTenantId().equals(tenantId)) {
            throw new IllegalArgumentException("Unauthorized access to cart item");
        }

        Cart cart = item.getCart();
        cart.getCartItems().remove(item);
        cartItemRepository.delete(item);
        recalculateCartTotal(cart);
    }

    @Transactional
    public void clearCart(Long customerId, String tenantId) {
        Cart cart = cartRepository.findByCustomerIdAndTenantIdAndStatus(
                customerId, tenantId, Cart.CartStatus.OPEN
        ).orElse(null);

        if (cart != null) {
            cartItemRepository.deleteAll(cart.getCartItems());
            cart.getCartItems().clear();
            cart.setTotal(BigDecimal.ZERO);
            cart.setQuantity(0);
            cartRepository.save(cart);
        }
    }
    
    /**
     * Recalculate cart item prices when product price changes
     * This is called by the Observer pattern when a ProductPriceChangedEvent is fired
     */
    @Transactional
    public void recalculateCartItemPrices(Long productId, String tenantId) {
        List<CartItem> affectedItems = cartItemRepository.findCartItemsContainingProduct(productId, tenantId);
        
        for (CartItem item : affectedItems) {
            Product product = item.getProduct();
            BigDecimal oldSnapshotPrice = item.getSnapshotPrice();
            
            // Update to current effective price
            updatePriceSnapshot(item, product);
            
            BigDecimal newSnapshotPrice = item.getSnapshotPrice();
            
            // Log price change for audit
            if (priceCalculationService.hasPriceChanged(oldSnapshotPrice, newSnapshotPrice)) {
                System.out.println(String.format(
                    "Cart item %d: Price updated from %s to %s for product %s (Sale: %s)",
                    item.getId(), oldSnapshotPrice, newSnapshotPrice, 
                    product.getName(), product.isOnSale()
                ));
            }
            
            cartItemRepository.save(item);
            recalculateCartTotal(item.getCart());
        }
    }

    private Cart getOrCreateCart(Long customerId, String tenantId) {
        return cartRepository.findByCustomerIdAndTenantIdAndStatus(
                customerId, tenantId, Cart.CartStatus.OPEN
        ).orElseGet(() -> {
            Customer customer = customerRepository.findById(customerId)
                    .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
            
            Cart newCart = new Cart();
            newCart.setCustomer(customer);
            newCart.setStatus(Cart.CartStatus.OPEN);
            newCart.setTenantId(tenantId);
            newCart.setTotal(BigDecimal.ZERO);
            newCart.setQuantity(0);
            return cartRepository.save(newCart);
        });
    }

    private void recalculateCartTotal(Cart cart) {
        BigDecimal total = cart.getCartItems().stream()
                .map(item -> item.getSnapshotPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        int totalQuantity = cart.getCartItems().stream()
                .mapToInt(CartItem::getQuantity)
                .sum();

        cart.setTotal(total);
        cart.setQuantity(totalQuantity);
        cartRepository.save(cart);
    }

    private CartItemDTO convertToDTO(CartItem item) {
        CartItemDTO dto = new CartItemDTO();
        dto.setId(item.getId());
        dto.setProductId(item.getProduct().getId());
        dto.setProductName(item.getProduct().getName());
        dto.setProductImage(item.getProduct().getImageUrl());
        dto.setPrice(item.getSnapshotPrice());
        dto.setQuantity(item.getQuantity());
        dto.setSubtotal(item.getSnapshotPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        return dto;
    }
}
