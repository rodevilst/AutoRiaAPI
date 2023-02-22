package com.example.autoriaapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
    @Column(name = "currency")
    private Currency currency;

    private String region;

    private int view;
    @Column(name = "last_view_time")
    private LocalDateTime lastViewTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    public CarUser(String brand, String model, int price, Currency currency, String region) {
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.currency = currency;
        this.region = region;
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

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public LocalDateTime getLastViewTime() {
        return lastViewTime;
    }

    public void setLastViewTime(LocalDateTime lastViewTime) {
        this.lastViewTime = lastViewTime;
    }
    public int getViewCountForDay() {
        if (lastViewTime == null || !lastViewTime.toLocalDate().equals(LocalDate.now())) {
            return 0;
        }
        return view;
    }
//    public int getViewCountForWeek() {
//        return IntStream.rangeClosed(1, 7)
//                .mapToObj(day -> LocalDateTime.now().minusDays(day))
//                .filter(date -> lastViewTime.isAfter(date))
//                .mapToInt(date -> 1)
//                .sum();
//    }
public int getViewCountForWeek() {
    if (lastViewTime == null || lastViewTime.isBefore(LocalDateTime.now().minusWeeks(1))) {
        return 0;
    }
    return view;
}
    public int getViewCountForMonth() {
        if (lastViewTime == null || !lastViewTime.toLocalDate().getMonth().equals(LocalDate.now().getMonth())) {
            return 0;
        }
        return view;
    }
    public int getViews() {
        return this.getView() + this.getViewCountForWeek() + this.getViewCountForMonth();
    }
}
