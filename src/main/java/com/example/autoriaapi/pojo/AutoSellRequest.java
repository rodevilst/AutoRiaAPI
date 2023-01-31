package com.example.autoriaapi.pojo;

import com.example.autoriaapi.models.Car;

import java.util.Set;

public class AutoSellRequest {
    private String brand;
    private String model;
    private String price;

    public String getBrand() {
        return brand;
    }


    public void setBrand(String brand) {
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
