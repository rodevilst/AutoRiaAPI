package com.example.autoriaapi.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "cars")
public class CarUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brand;
    private String model;
    private String price;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;



    public CarUser(String brand, String model, String price) {
        this.brand = brand;
        this.model = model;
        this.price = price;
    }

    public CarUser(User user) {
        this.user = user;
    }

    public CarUser() {

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    //    public Set<User> getOwner() {
//        return owner;
//    }
//
//    public void setOwner(Set<User> owner) {
//        this.owner = owner;
//    }
}
