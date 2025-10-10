package com.homebase.admin.service;

import com.homebase.admin.entity.Cart;
import com.homebase.admin.entity.CartItem;
import com.homebase.admin.entity.Product;
import com.homebase.admin.repository.ProductRepository;
import com.homebase.admin.strategy.PricingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CartDomainService {

    private final ProductRepository productRepository;
    private final PricingStrategy pricingStrategy;

    @Autowired
    public CartDomainService(ProductRepository productRepository, @Qualifier("defaultPricingStrategy") PricingStrategy pricingStrategy) {
        this.productRepository = productRepository;
        this.pricingStrategy = pricingStrategy;
    }

    public void addItemToCart(Cart cart, Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        BigDecimal itemPrice = pricingStrategy.calculatePrice(product, quantity);

        CartItem item = new CartItem();
        item.setCart(cart);
        item.setQuantity(quantity);
        item.setProduct(product);
        item.setSnapshotPrice(itemPrice); // save current snapshot
        cart.getCartItems().add(item);

        recalculateCartTotal(cart);
    }

    public void recalculateCartTotal(Cart cart) {
        BigDecimal total = cart.getCartItems().stream()
                .map(item -> {
                    Product currentProduct = item.getProduct();
                    return pricingStrategy.calculatePrice(currentProduct, item.getQuantity());
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotal(total);
    }
}
