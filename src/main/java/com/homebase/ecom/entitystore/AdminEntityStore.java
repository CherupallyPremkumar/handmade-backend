package com.homebase.ecom.entitystore;

import com.homebase.ecom.domain.Admin;

import com.homebase.ecom.repository.AdminRepository;
import org.chenile.utils.entity.service.EntityStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


public class AdminEntityStore implements EntityStore<Admin> {

    private final AdminRepository adminRepository;

    public AdminEntityStore(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public void store(Admin entity) {

    }

    @Override
    public Admin retrieve(String id) {
        return null;
    }
}
