package com.handmade.ecommerce.customer;

import com.handmade.ecommerce.customer.entity.CustomerAddress;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for CustomerAddress
 * Generated from entity file
 */
@Repository
public interface CustomerAddressRepository extends JpaRepository<CustomerAddress, String> {
    
    List<CustomerAddress> findByCustomerId(String customerId);
    List<CustomerAddress> findByAddressType(String addressType);
    List<CustomerAddress> findByFullName(String fullName);
    List<CustomerAddress> findByPostalCode(String postalCode);
    List<CustomerAddress> findByCountryCode(String countryCode);
    List<CustomerAddress> findByPhoneNumber(String phoneNumber);
}
