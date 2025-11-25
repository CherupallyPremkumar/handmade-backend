package com.handmade.ecommerce.orderline.service.cmds;

import com.handmade.ecommerce.orderline.model.Orderline;

import org.chenile.stm.action.STMAutomaticStateComputation;
import org.springframework.stereotype.Service;

@Service
public class CheckIfOrderLineOnSaleService implements STMAutomaticStateComputation<Orderline> {

//    @Autowired
//    private PriceService priceStateService;


    @Override
    public String execute(Orderline stateEntity) throws Exception {
        return "";
    }
}