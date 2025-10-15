package com.homebase.ecom.mybatis.customer;

import java.util.List;

public class CustomerDto {
    private String  id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String companyName;
    private String dateOfBirth;

    private String gender;
    private boolean indivisual;
    private String tenantId;
    private List<AddressDto> address;




    public String getFirstName() {
        return firstName;
    }

    public CustomerDto setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public CustomerDto setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public CustomerDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public CustomerDto setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getCompanyName() {
        return companyName;
    }

    public CustomerDto setCompanyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public CustomerDto setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public String getGender() {
        return gender;
    }

    public CustomerDto setGender(String gender) {
        this.gender = gender;
        return this;
    }



    public String getTenantId() {
        return tenantId;
    }

    public CustomerDto setTenantId(String tenantId) {
        this.tenantId = tenantId;
        return this;
    }





    public List<AddressDto> getAddress() {
        return address;
    }

    public CustomerDto setAddress(List<AddressDto> address) {
        this.address = address;
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isIndivisual() {
        return indivisual;
    }

    public void setIndivisual(boolean indivisual) {
        this.indivisual = indivisual;
    }
}
