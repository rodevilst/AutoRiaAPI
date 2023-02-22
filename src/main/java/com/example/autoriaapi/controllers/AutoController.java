package com.example.autoriaapi.controllers;

import com.example.autoriaapi.models.CarUser;
import com.example.autoriaapi.models.Role;
import com.example.autoriaapi.models.User;
import com.example.autoriaapi.pojo.AutoSellRequest;
import com.example.autoriaapi.pojo.MessageResponse;
import com.example.autoriaapi.repository.CarRepository;
import com.example.autoriaapi.repository.RoleRepository;
import com.example.autoriaapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/car")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AutoController {
    @Autowired
    CarRepository carRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;


    @PostMapping("/{id}/seller")
    @PreAuthorize("hasRole('SELLER') or hasRole('UP_SELLER')")
    public ResponseEntity<?> autoRegister(@PathVariable long id, @RequestBody AutoSellRequest autoSellRequest) {
        List<Role> allrole = roleRepository.findAll();
        Role sell = allrole.get(2);
        Role upSell = allrole.get(3);
        User one = userRepository.getOne(id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (one.getCars().size() >= 1 && user.getRoles().contains(sell)) {
            return ResponseEntity.badRequest().body(new MessageResponse("You have reached the maximum number of cars"));
        } else if (user.getRoles().contains(upSell)) {
            CarUser carUser = new CarUser(autoSellRequest.getBrand(), autoSellRequest.getModel(), autoSellRequest.getPrice(), autoSellRequest.getCurrency(), autoSellRequest.getRegion());
            carUser.setUser(user);
            user.getCars().add(carUser);
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            CarUser carUser = new CarUser(autoSellRequest.getBrand(), autoSellRequest.getModel(), autoSellRequest.getPrice(), autoSellRequest.getCurrency(), autoSellRequest.getRegion());
            carUser.setUser(user);
            user.getCars().add(carUser);
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
    }


    @GetMapping("/allcar")
    public ResponseEntity<List<Map<String, Object>>> getAllCar() {
        List<CarUser> carList = carRepository.findAll();
        List<Map<String, Object>> carInfoList = new ArrayList<>();

        for (CarUser car : carList) {
            Map<String, Object> carInfo = new HashMap<>();
            carInfo.put("price", car.getPrice());
            carInfo.put("model", car.getModel());
            carInfo.put("brand", car.getBrand());
            carInfo.put("currency", car.getCurrency());
            carInfoList.add(carInfo);
        }
        return new ResponseEntity<>(carInfoList, HttpStatus.OK);
    }

    @GetMapping("/{id}/getcars")
    public ResponseEntity<Map<String, Object>> getCarById(@PathVariable long id) {
        Optional<CarUser> optionalCarUser = carRepository.findById(id);
        if (optionalCarUser.isPresent()) {
            CarUser carUser = optionalCarUser.get();
            int view = carUser.getView();
            carUser.setLastViewTime(LocalDateTime.now());
            carUser.setView(++view);
            carRepository.save(carUser);

            Map<String, Object> response = new HashMap<>();
            response.put("brand", carUser.getBrand());
            response.put("model", carUser.getModel());
            response.put("price", carUser.getPrice());
            response.put("currency", carUser.getCurrency());
            System.out.println(response);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{brand}")
    public ResponseEntity<List<Map<String, Object>>> getCarsByBrand(@PathVariable String brand) {
        List<CarUser> carList = carRepository.findByBrand(brand);
        List<Map<String, Object>> carInfoList = new ArrayList<>();

        for (CarUser car : carList) {
            Map<String, Object> carInfo = new HashMap<>();
            carInfo.put("price", car.getPrice());
            carInfo.put("model", car.getModel());
            carInfo.put("brand", car.getBrand());
            carInfoList.add(carInfo);
        }
        return new ResponseEntity<>(carInfoList, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MODER')")
    public void deleteCar(@PathVariable long id) {
        carRepository.deleteById(id);
    }


    @GetMapping("/region/{region}")
    public ResponseEntity<List<Map<String, Object>>> getCarsByRegion(@PathVariable String region) {
        List<CarUser> carList = carRepository.getByRegion(region);

        List<Map<String, Object>> carInfoList = carList.stream()
                .map(car -> {
                    Map<String, Object> carInfo = new HashMap<>();
                    carInfo.put("price", car.getPrice());
                    carInfo.put("model", car.getModel());
                    carInfo.put("brand", car.getBrand());
                    carInfo.put("currency", car.getCurrency());
                    return carInfo;
                })
                .collect(Collectors.toList());

        return new ResponseEntity<>(carInfoList, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('UP_SELLER')")
    @GetMapping("/region/midprice/{region}/{brand}")
    public ResponseEntity<Map<String, Double>> getMidPriceByRegionAndBrand(@PathVariable String region, @PathVariable String brand) {
        List<CarUser> carList = carRepository.findByRegionAndBrand(region, brand);

        DoubleSummaryStatistics stats = carList.stream()
                .mapToDouble(CarUser::getPrice)
                .summaryStatistics();

        Map<String, Double> response = new HashMap<>();
        response.put("averagePrice", stats.getAverage());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('UP_SELLER')")
    @GetMapping("/statistic/{id}")
    public ResponseEntity<CarUser> getCarByid(@PathVariable long id) {
        Optional<CarUser> optionalCarUser = carRepository.findById(id);
        if (optionalCarUser.isPresent()) {
            CarUser carUser = optionalCarUser.get();
//            int view = carUser.getView();
//            carUser.setLastViewTime(LocalDateTime.now());
//            carUser.setView(++view);
//            carRepository.save(carUser);
            return new ResponseEntity<>(carUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('UP_SELLER')")
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

    @GetMapping("/testing/{id}")
    public void forRole(@PathVariable long id) {
        List<Role> allrole = roleRepository.findAll();
        Role adm = allrole.get(0);
        Role mod = allrole.get(1);
        Role sell = allrole.get(2);
        Role upSell = allrole.get(3);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Collection<Role> roles = user.getRoles();
        if (roles.contains(adm)) {
            System.out.println("its admin");
        } else if (roles.contains(mod)) {
            System.out.println("its moder");
        } else if (roles.contains(sell)) {
            System.out.println("its seller");
        } else if (roles.contains(upSell)) {
            System.out.println("its upSeller");
        } else {
            System.out.println("dont working");
        }
    }
}




