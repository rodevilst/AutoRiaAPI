package com.example.autoriaapi.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user_cars")
public class CarUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//@Column(name = "brand")
    private String brand;
//@Column(name = "model")
    private String model;
    private String price;
//    @OneToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "car_owner",
//            joinColumns = @JoinColumn(name = "car_id"),
//            inverseJoinColumns = @JoinColumn(name = "user_id")
////            JoinColumns = @JoinColumn(name = "user_id")
//            )
//    @JoinColumn(name = "user_id")
//    private Set<User> owner = new HashSet<>();

    public CarUser(String brand, String model, String price) {
        this.brand = brand;
        this.model = model;
        this.price = price;
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

//    public Set<User> getOwner() {
//        return owner;
//    }
//
//    public void setOwner(Set<User> owner) {
//        this.owner = owner;
//    }
}
