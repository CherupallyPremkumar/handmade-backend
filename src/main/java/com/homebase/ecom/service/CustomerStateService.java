package com.homebase.ecom.service;

import com.homebase.ecom.domain.Customer;
import org.chenile.workflow.dto.StateEntityServiceResponse;

public interface CustomerStateService<Customer> {

    StateEntityServiceResponse<Customer> create(Customer order);

    StateEntityServiceResponse<Customer> processById(String id, String event, Object payload);

    StateEntityServiceResponse<Customer> retrieve(String id);
}
