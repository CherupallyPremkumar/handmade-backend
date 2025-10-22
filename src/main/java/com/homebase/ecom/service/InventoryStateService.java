package com.homebase.ecom.service;

public interface InventoryStateService {

    public  boolean checkStock(String product,int quantity) throws Exception;
}
