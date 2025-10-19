package com.homebase.ecom.domain;

import com.homebase.ecom.entity.MultiTenantStateEntity;
import org.chenile.utils.entity.model.AbstractExtendedStateEntity;

public class Artisan extends MultiTenantExtendedStateEntity {

    private String name;
    private String email;
    private String phoneNumber;
    private String specialty;

    public Artisan() {
    }

    public Artisan(String name, String email, String phoneNumber, String specialty) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.specialty = specialty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }
}
