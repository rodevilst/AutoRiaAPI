package com.example.autoriaapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;
@Data
@Entity
@Table(name = "cars")
public class CarUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brand;
    private String model;
    private int price;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;



    public CarUser(String brand, String model, int price) {
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
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
