package com.example.autoriaapi.pojo;

import java.util.Set;

public class AutoSellRequest {
    private Set<String> brand;
    private String model;
    private String price;

    public Set<String> getBrand() {
        return brand;
    }


    public void setBrand(Set<String> brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
