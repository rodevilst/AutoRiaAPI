package com.example.autoriaapi.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EBrand brand;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EBrand getBrand() {
        return brand;
    }

    public void setBrand(EBrand brand) {
        this.brand = brand;
    }
}



