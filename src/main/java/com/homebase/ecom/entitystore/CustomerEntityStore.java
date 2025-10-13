package com.homebase.ecom.entitystore;

import com.homebase.ecom.domain.Customer;
import com.homebase.ecom.entity.CustomerEntity;
import com.homebase.ecom.repository.CustomerRepository;
import org.chenile.utils.entity.service.EntityStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerEntityStore implements EntityStore<Customer> {
    
    @Autowired
    private CustomerRepository customerRepository;
    
    @Override
    public void store(Customer domainCustomer) {
        CustomerEntity jpaCustomerEntity = convertToJpaEntity(domainCustomer);
        customerRepository.save(jpaCustomerEntity);
        
        if (domainCustomer.getId() == null) {
            domainCustomer.setId(jpaCustomerEntity.getId());
        }
    }

    @Override
    public Customer retrieve(String id) {
        return customerRepository.findById(id)
                .map(this::convertToDomainModel)
                .orElse(null);
    }
    
    private CustomerEntity convertToJpaEntity(Customer domain) {
        CustomerEntity jpa = new CustomerEntity();
        
        if (domain.getId() != null) {
            jpa.setId(domain.getId());
        }
        
        jpa.setName(domain.getName());
        jpa.setEmail(domain.getEmail());
        jpa.setPassword(domain.getPassword());
        jpa.setPhone(domain.getPhone());
        jpa.setOrderCount(domain.getOrderCount());
        jpa.setTotalSpent(domain.getTotalSpent());
        jpa.setRoles(domain.getRoles());
        
        return jpa;
    }
    
    private Customer convertToDomainModel(CustomerEntity jpa) {
        Customer domain = new Customer();
        
        domain.setId(jpa.getId());
        domain.setName(jpa.getName());
        domain.setEmail(jpa.getEmail());
        domain.setPassword(jpa.getPassword());
        domain.setPhone(jpa.getPhone());
        domain.setOrderCount(jpa.getOrderCount());
        domain.setTotalSpent(jpa.getTotalSpent());
        domain.setRoles(jpa.getRoles());
        
        return domain;
    }
}
