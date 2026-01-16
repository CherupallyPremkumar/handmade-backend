package com.handmade.ecommerce.iam.configuration.dao;

import com.handmade.ecommerce.iam.model.Iam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface IamRepository extends JpaRepository<Iam,String> {}
