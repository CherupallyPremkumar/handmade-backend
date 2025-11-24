package com.handmade.ecommerce.common.configuration.dao;

import com.handmade.ecommerce.common.model.Common;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface CommonRepository extends JpaRepository<Common,String> {}
