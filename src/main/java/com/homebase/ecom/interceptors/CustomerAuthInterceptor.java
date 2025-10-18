package com.homebase.ecom.interceptors;

import com.homebase.ecom.constants.HeaderConstants;
import com.homebase.ecom.domain.Customer;
import com.homebase.ecom.mybatis.customer.CustomerExists;
import com.homebase.ecom.service.CustomerStateService;
import com.homebase.ecom.service.CustomerValidationService;
import org.chenile.core.context.ChenileExchange;
import org.chenile.core.context.ContextContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CustomerAuthInterceptor extends PopulateGuestOrCustomerInterceptor {

    @Autowired
    @Qualifier("_customerStateEntityService_")
    private CustomerStateService customerStateService;



    @Autowired
    public CustomerAuthInterceptor(CustomerValidationService customerValidationService) {
        super(customerValidationService);
    }

    @Override
    protected void doPreProcessing(ChenileExchange exchange) {
        super.doPreProcessing(exchange);

        CustomerExists customerEntity = exchange.getHeader(
                HeaderConstants.HEADER_CUSTOMER_ENTITY,
                CustomerExists.class);

        Map<String, Object> customerMap = exchange.getHeader(
                HeaderConstants.HEADER_USER_INFO,
                (Class<Map<String, Object>>) (Class<?>) Map.class);

        if (!customerEntity.isExists() && customerMap != null && !customerMap.isEmpty()) {
            // Only create if customer doesn't exist
            createCustomer(customerMap);
        }
    }

    private void createCustomer(Map<String, Object> customerMap) {
        String customerId = (String) customerMap.get("x-customer-id");
        Customer newCustomer = new Customer();
        newCustomer.setId(customerId);
        newCustomer.setEmail((String) customerMap.get("email"));
        newCustomer.setName((String) customerMap.get("firstName"));
        customerStateService.create(newCustomer);
    }
}
