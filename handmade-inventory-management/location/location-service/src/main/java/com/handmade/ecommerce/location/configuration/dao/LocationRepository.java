package com.handmade.ecommerce.location.configuration.dao;

import com.handmade.ecommerce.location.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface LocationRepository extends JpaRepository<Location,String> {}
