package com.homebase.ecom.mybatis.dashboard;

public class InventoryDetails {
    public String locationId;
    public  String quantityAvailable;
    public  String quantityOnHand;
    public  String location_name;


    public String getLocationId() {
        return locationId;
    }

    public InventoryDetails setLocationId(String locationId) {
        this.locationId = locationId;
        return this;
    }

    public String getQuantityAvailable() {
        return quantityAvailable;
    }

    public InventoryDetails setQuantityAvailable(String quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
        return this;
    }

    public String getQuantityOnHand() {
        return quantityOnHand;
    }

    public InventoryDetails setQuantityOnHand(String quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
        return this;
    }

    public String getLocation_name() {
        return location_name;
    }

    public InventoryDetails setLocation_name(String location_name) {
        this.location_name = location_name;
        return this;
    }
}
