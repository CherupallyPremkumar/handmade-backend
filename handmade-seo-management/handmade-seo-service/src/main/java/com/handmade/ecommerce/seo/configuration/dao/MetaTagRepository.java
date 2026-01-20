package com.handmade.ecommerce.seo.configuration.dao;

import com.handmade.ecommerce.seo.model.MetaTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface MetaTagRepository extends JpaRepository<MetaTag,String> {}
