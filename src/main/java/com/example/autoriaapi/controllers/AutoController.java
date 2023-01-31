package com.example.autoriaapi.controllers;

import com.example.autoriaapi.models.Car;
import com.example.autoriaapi.models.CarUser;
import com.example.autoriaapi.models.EBrand;
import com.example.autoriaapi.models.User;
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

//        Set<String> reqCars = autoSellRequest.getBrand();
        Set<User> owner = new HashSet<>();

        CarUser carUser = new CarUser(autoSellRequest.getBrand(), autoSellRequest.getModel(), autoSellRequest.getPrice());
//        if(reqCars == null) {
//            throw new RuntimeException("not car brand");
//        }else{
//
//            reqCars.forEach(r -> {
//                switch (r) {
//                    case "bmw":
//                        Car bmwcar = carRepository
//                                .findByBrand(EBrand.BRAND_BMW)
//                                .orElseThrow(() -> new RuntimeException("Error, BMW is not found"));
//                        carBrandSet.add(bmwcar);
//                        break;
//                }
//
//            });
//        }


        carRepository.save(carUser);
        carUser.setOwner(owner);
        return ResponseEntity.ok(new MessageResponse("Car CREATED"));
    }
}

