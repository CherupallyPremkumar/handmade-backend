package com.handmade.ecommerce.price;

import com.handmade.ecommerce.command.CreatePriceCommand;
import com.handmade.ecommerce.price.model.Price;

public interface PriceService { ;

     Price createPrice(CreatePriceCommand price);
}
