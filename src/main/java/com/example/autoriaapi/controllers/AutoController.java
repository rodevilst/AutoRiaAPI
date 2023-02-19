package com.example.autoriaapi.controllers;

import com.example.autoriaapi.models.CarUser;
import com.example.autoriaapi.models.ERole;
import com.example.autoriaapi.models.User;
import com.example.autoriaapi.pojo.AutoSellRequest;
import com.example.autoriaapi.pojo.MessageResponse;
import com.example.autoriaapi.repository.CarRepository;
import com.example.autoriaapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/car")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AutoController {
    @Autowired
    CarRepository carRepository;
    @Autowired
    UserRepository userRepository;

    CarUser carUser;
    String price;

    @PostMapping("/{id}/seller")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> autoRegister(@PathVariable long id, @RequestBody AutoSellRequest autoSellRequest) {
//        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<CarUser> cars = user.getCars();
        System.out.println(cars);
        if (user.getRoles().contains(ERole.ROLE_SELLER) && cars.size() >= 1) {
            return ResponseEntity.badRequest().body(new MessageResponse("You are not allowed to sell more than 1 car"));
        } else if (user.getRoles().contains(ERole.ROLE_UP_SELLER) && cars.size() >= Integer.MAX_VALUE) {
            return ResponseEntity.badRequest().body(new MessageResponse("You have reached the maximum number of cars"));
        } else {
            CarUser carUser = new CarUser(autoSellRequest.getBrand(), autoSellRequest.getModel(), autoSellRequest.getPrice());
            carUser.setUser(user);
            user.getCars().add(carUser);
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }



    }

    @GetMapping("/allcar")
    public ResponseEntity<List<CarUser>> getAllCar() {
        return new ResponseEntity<>(carRepository.findAll(), HttpStatus.valueOf(200));
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




