package com.example.autoriaapi.repository;




import com.example.autoriaapi.models.Car;
import com.example.autoriaapi.models.CarUser;
import com.example.autoriaapi.models.EBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<CarUser, Long> {
//    Optional<Car> findByBrand(EBrand brand);


}
