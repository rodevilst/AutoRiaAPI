package com.example.autoriaapi.controllers;

import com.example.autoriaapi.models.CarUser;
import com.example.autoriaapi.models.User;
import com.example.autoriaapi.pojo.AutoSellRequest;
import com.example.autoriaapi.pojo.MessageResponse;
import com.example.autoriaapi.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/car")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AutoController {
    @Autowired
    CarRepository carRepository;

    CarUser carUser;
    String price;

    @PostMapping("/seller")
    @PreAuthorize("hasRole('SELLER')")

    public ResponseEntity autoRegister(@RequestBody AutoSellRequest autoSellRequest) {

        Set<User> owner = new HashSet<>();
        CarUser carUser = new CarUser(autoSellRequest.getBrand(), autoSellRequest.getModel(), autoSellRequest.getPrice());

        carRepository.save(carUser);
//        carUser.setOwner(owner);
        return ResponseEntity.ok(new MessageResponse("Car CREATED"));
    }

    @GetMapping("/allcar")
    public ResponseEntity<List<CarUser>> getAllCar() {
        List<CarUser> all = carRepository.findAll();
        return new ResponseEntity<>(all, HttpStatus.valueOf(200));
    }

    @GetMapping("/{brand}")
    public ResponseEntity<List<CarUser>> getCarsByBrand(@PathVariable String brand) {
        return new ResponseEntity<>(carRepository.findByBrand(brand), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MODER')")
    public void deleteCar(@PathVariable long id) {
        carRepository.deleteById(id);
    }



    // for seller++
    @GetMapping("/{brand}/mid")
    public OptionalDouble getMiddlePrice(@PathVariable String brand) {
        int i = 0;
        List<CarUser> byBrand = carRepository.findByBrand(brand);
        ArrayList<Integer> integers = new ArrayList<>();
        for (i = 0; i < byBrand.size(); i++) {
            int price1 = byBrand.get(i).getPrice();
            integers.add(price1);
        }
        System.out.println(integers);
        OptionalDouble average = integers.stream().mapToInt(e -> e).average();
        System.out.println(average);
        return average;
    }



}




