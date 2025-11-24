package com.handmade.ecommerce.orderline.service.cmds;

import com.handmade.ecommerce.orderline.model.Orderline;
import com.homebase.ecom.domain.CartItem;
import com.homebase.ecom.service.price.PriceStateService;
import com.homebase.ecom.service.tax.TaxService;
import org.chenile.stm.action.STMAutomaticStateComputation;
import org.hibernate.query.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckIfOrderLineOnSaleService implements STMAutomaticStateComputation<Orderline> {

    @Autowired
    private PriceService priceStateService;


    @Override
    public String execute(Orderline stateEntity) throws Exception {
        return "";
    }
}