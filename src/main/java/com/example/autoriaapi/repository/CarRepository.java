package com.example.autoriaapi.repository;




import com.example.autoriaapi.models.Car;
import com.example.autoriaapi.models.CarUser;
import com.example.autoriaapi.models.EBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<CarUser, Long> {
//    Optional<Car> findByBrand(EBrand brand);
//    @Query("select c from CarUser c where c.brand=:brand")
//    List<CarUser> getByBrand(@Param("brand") String brand);
    @Query("select c from CarUser c where c.brand=:brand")
    List<CarUser> findByBrand(@Param("brand") String brand);


}
