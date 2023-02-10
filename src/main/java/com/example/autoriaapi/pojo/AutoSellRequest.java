package com.example.autoriaapi.pojo;

import com.example.autoriaapi.models.Car;

import java.util.Set;

public class AutoSellRequest {
    private String brand;
    private String model;
    private int price;
    private Set<String> owner;

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Set<String> getOwner() {
        return owner;
    }

    public void setOwner(Set<String> owner) {
        this.owner = owner;
    }
//    https://api.privatbank.ua/p24api/pubinfo?exchange&json&coursid=11
}
