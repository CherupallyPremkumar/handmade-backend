package com.handmade.ecommerce.productorch.service;

import com.handmade.ecommerce.command.CreateProductCommand;
import com.handmade.ecommerce.command.CreateVariantCommand;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class ValidateProductService extends BaseProductOrchService {


    @Override
    protected void doProcess(ProductContext productContext) {

    }
}