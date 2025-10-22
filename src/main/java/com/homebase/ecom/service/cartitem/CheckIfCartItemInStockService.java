package com.homebase.ecom.service.cartitem;

import com.homebase.ecom.domain.CartItem;
import com.homebase.ecom.service.InventoryStateService;
import org.chenile.stm.action.STMAutomaticStateComputation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckIfCartItemInStockService implements STMAutomaticStateComputation<CartItem> {

    @Autowired
    InventoryStateService inventoryStateService;

    @Override
    @SuppressWarnings("unchecked")
    public String execute(CartItem cartItem) throws Exception {
        if(cartItem.isOutOfQuanity()) {
            return "outOfQuantity";
        }
        if (inventoryStateService.checkStock(cartItem.getProductVariantId(), cartItem.getQuantity())) {
            return "inStock";
        } else {
            return "outOfStock";
        }
    }

}
