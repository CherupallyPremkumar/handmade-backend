package com.handmade.ecommerce.productanswer.configuration.dao;

import com.handmade.ecommerce.qa.model.ProductAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface ProductAnswerRepository extends JpaRepository<ProductAnswer,String> {}
