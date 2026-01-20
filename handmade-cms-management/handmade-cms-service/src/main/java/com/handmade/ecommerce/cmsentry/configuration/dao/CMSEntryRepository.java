package com.handmade.ecommerce.cmsentry.configuration.dao;

import com.handmade.ecommerce.cms.model.CMSEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface CMSEntryRepository extends JpaRepository<CMSEntry,String> {}
