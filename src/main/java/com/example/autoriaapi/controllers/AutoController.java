package com.example.autoriaapi.controllers;

import com.example.autoriaapi.models.Car;
import com.example.autoriaapi.models.CarBrand;
import com.example.autoriaapi.pojo.AutoSellRequest;
import com.example.autoriaapi.pojo.MessageResponse;
import com.example.autoriaapi.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/car")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AutoController {
    @Autowired
    CarRepository carRepository;

    @PostMapping("/seller")
    @PreAuthorize("hasRole('SELLER')")

    public ResponseEntity autoRegister(@RequestBody AutoSellRequest autoSellRequest) {

        Set<String> reqCars = autoSellRequest.getBrand();
        Set<Car> carBrandSet = new HashSet<>();

        Car car = new Car(autoSellRequest.getBrand(), autoSellRequest.getModel(), autoSellRequest.getPrice());

        if (autoSellRequest.getBrand() == null) {
            Car carBrand = carRepository
                    .findByBrand(CarBrand.BRAND_AUDI)
                    .orElseThrow(() -> new RuntimeException("Error, Role USER is not found"));
            carBrandSet.add(carBrand);
        }
        else {
            reqCars.forEach(r -> {
                switch (r) {
                    case "bmw":
                        Car BMWcar = carRepository
                                .findByBrand(CarBrand.BRAND_BMW)
                                .orElseThrow(() -> new RuntimeException("Error, BMW is not found"));
                        carBrandSet.add(BMWcar);
                        break;
                }

            });
        }

        carRepository.save(car);
        return ResponseEntity.ok(new MessageResponse("Car CREATED"));
    }
}

