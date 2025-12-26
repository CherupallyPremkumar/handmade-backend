package com.handmade.ecommerce.cartline.bdd;


import com.handmade.ecommerce.cartline.SpringTestConfig;
import com.handmade.ecommerce.cartline.model.Cartline;
import com.handmade.ecommerce.cartline.service.CartlineService;
import com.handmade.ecommerce.command.cart.AddCartLinePayload;
import com.handmade.ecommerce.command.cart.CartLineDecrementQtyPayLoad;
import com.handmade.ecommerce.command.cart.CartLineIncrementQtyPayLoad;
import org.chenile.core.context.ContextContainer;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.shaded.org.checkerframework.checker.units.qual.A;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = SpringTestConfig.class)
@ActiveProfiles("unittest")
public class CartlineServiceTest {

    @Autowired
    @Qualifier("_cartlineStateEntityService_")
    private CartlineService cartlineService;

    @Autowired
    ContextContainer contextContainer;
    @BeforeEach
    void setup() {
        contextContainer.setRegion("INR");
    }

    @Test
    @Order(1)
    void testCreateAndGetCartLine() {
        AddCartLinePayload payload = new AddCartLinePayload("cart1", "var1", "seller1", 2);
        cartlineService.createCartLine(payload);

        List<Cartline> cartLines = cartlineService.getCartLines("cart1");
        assertNotNull(cartLines);
        assertFalse(cartLines.isEmpty());

        Cartline line = cartLines.get(0);
        assertEquals("cart1", line.getCartId());
        assertEquals("var1", line.getVariantId());
        assertEquals(2, line.getQuantity());
    }

    @Test
    @Order(2)
    void testIncrementAndDecrementCartLine() {
        // create a line first
        AddCartLinePayload payload = new AddCartLinePayload("cart2", "var2", "seller1", 1);
        cartlineService.createCartLine(payload);
        Cartline line = cartlineService.getCartLines("cart2").get(0);

        // increment
        CartLineIncrementQtyPayLoad incPayload = new CartLineIncrementQtyPayLoad();
        incPayload.setCartLineId(line.getId());
        cartlineService.incrementCartLine(incPayload);
        Cartline updated = cartlineService.getCartLine(line.getId());
        assertEquals(2, updated.getQuantity());

        // decrement
        CartLineDecrementQtyPayLoad decPayload = new CartLineDecrementQtyPayLoad();
        decPayload.setCartLineId(line.getId());
        cartlineService.decrementCartLine(decPayload);
        updated = cartlineService.getCartLine(line.getId());
        assertEquals(1, updated.getQuantity());
    }

    @Test
    @Order(3)
    void testDeleteCartLine() {
        AddCartLinePayload payload = new AddCartLinePayload("cart3", "var3", "seller1", 1);
        cartlineService.createCartLine(payload);
        Cartline line = cartlineService.getCartLines("cart3").get(0);

        cartlineService.deleteCartItem(line.getId());

        List<Cartline> cartLines = cartlineService.getCartLines("cart3");
        assertTrue(cartLines.isEmpty());
    }

    @Test
    @Order(4)
    void testMergeCart() {
        // create lines in oldCart
        cartlineService.createCartLine(new AddCartLinePayload("oldCart", "var1", "seller1", 1));
        cartlineService.createCartLine(new AddCartLinePayload("oldCart", "var2", "seller1", 2));

        cartlineService.mergeCart("oldCart", "newCart");

        List<Cartline> newCartLines = cartlineService.getCartLines("newCart");
        assertEquals(2, newCartLines.size());
    }
}
