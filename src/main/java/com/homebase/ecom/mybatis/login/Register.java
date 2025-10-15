package com.homebase.ecom.mybatis.login;

public class Register {
    private String registerId;
    private  String registerName;
    private String drawerId;
    private String locationId;

    public String getRegisterId() {
        return registerId;
    }

    public Register setRegisterId(String registerId) {
        this.registerId = registerId;
        return this;
    }

    public String getRegisterName() {
        return registerName;
    }

    public Register setRegisterName(String registerName) {
        this.registerName = registerName;
        return this;
    }

    public String getDrawerId() {
        return drawerId;
    }

    public Register setDrawerId(String drawerId) {
        this.drawerId = drawerId;
        return this;
    }

    public String getLocationId() {
        return locationId;
    }

    public Register setLocationId(String locationId) {
        this.locationId = locationId;
        return this;
    }
}
