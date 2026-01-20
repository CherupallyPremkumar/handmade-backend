package com.handmade.ecommerce.contentpage.configuration.dao;


import com.handmade.ecommerce.cms.model.ContentPage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface ContentPageRepository extends JpaRepository<ContentPage,String> {}
