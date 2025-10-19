package com.homebase.ecom.mybatis.customer;

public class AddressDto {
    private String id;
    private String address1;
    private String address2;
    private String city;
    private String companyName;
    private String zipCode;
    private String  countryId;
    private String countryName;

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    private String stateId;
    private String stateName;

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    private  Boolean isDefault;

    public Boolean getDefault() {
        return isDefault;
    }

    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    private String addressType;

    public String getId() {
        return id;
    }

    public AddressDto setId(String id) {
        this.id = id;
        return this;
    }

    public String getAddress1() {
        return address1;
    }

    public AddressDto setAddress1(String address1) {
        this.address1 = address1;
        return this;
    }

    public String getAddress2() {
        return address2;
    }

    public AddressDto setAddress2(String address2) {
        this.address2 = address2;
        return this;
    }

    public String getCity() {
        return city;
    }

    public AddressDto setCity(String city) {
        this.city = city;
        return this;
    }

    public String getCompanyName() {
        return companyName;
    }

    public AddressDto setCompanyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public String getZipCode() {
        return zipCode;
    }

    public AddressDto setZipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public String getCountryId() {
        return countryId;
    }

    public AddressDto setCountryId(String countryId) {
        this.countryId = countryId;
        return this;
    }
}
