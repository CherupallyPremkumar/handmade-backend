package com.handmade.ecommerce.customer.configuration.dao;


import com.handmade.ecommerce.customer.model.CustomerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface CustomerProfileRepository extends JpaRepository<CustomerProfile,String> {}
