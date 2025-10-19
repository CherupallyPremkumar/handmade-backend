package com.homebase.ecom.mybatis.login;

import java.util.List;

public class Store {
    private String locationId;
    private String locationName;

    private List<Register> registersData;


    public String getLocationId() {
        return locationId;
    }

    public Store setLocationId(String locationId) {
        this.locationId = locationId;
        return this;
    }

    public String getLocationName() {
        return locationName;
    }

    public Store setLocationName(String locationName) {
        this.locationName = locationName;
        return this;
    }

    public List<Register> getRegistersData() {
        return registersData;
    }

    public Store setRegistersData(List<Register> registersData) {
        this.registersData = registersData;
        return this;
    }
}
