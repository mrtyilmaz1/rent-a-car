package com.x.rentacar.repository;

import com.x.rentacar.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    @Query("SELECT c FROM Car c WHERE c.brandId = :brandId")
    List<Car> findCarListByBrandId(@Param("brandId") Long brandId); // @Param brandId sorgudaki eşleştirmek için kullanılır.

    @Query("UPDATE Car c SET c.active = :active WHERE c.id = :id")
    @Modifying
    int updateStatusOfCarById(@Param("active") Boolean active, @Param("id") Long id);



}
