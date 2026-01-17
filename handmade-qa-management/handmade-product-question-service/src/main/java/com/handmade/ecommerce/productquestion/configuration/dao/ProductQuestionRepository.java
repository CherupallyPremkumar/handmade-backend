package com.handmade.ecommerce.productquestion.configuration.dao;

import com.handmade.ecommerce.qa.model.ProductQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface ProductQuestionRepository extends JpaRepository<ProductQuestion,String> {}
