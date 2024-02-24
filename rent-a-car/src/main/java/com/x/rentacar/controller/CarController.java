package com.x.rentacar.controller;

import com.x.rentacar.model.Car;
import com.x.rentacar.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/car")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Car saveCar(@RequestPart("file")MultipartFile file,
                       @RequestPart("car") Car car){
        return carService.saveCar(file,car);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public Car getCar(@PathVariable(value = "id") Long id) {
        return carService.getCarById(id);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Car updateProduct(@RequestBody Car car) {
        return carService.updateCar(car);
    }


    @PutMapping("deactive/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deactiveCar(@PathVariable(value = "id") Long id) { carService.deactiveCar(id);}

    @PutMapping("active/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void activeCar(@PathVariable(value = "id") Long id) { carService.activeCar(id);}

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteCar(@PathVariable(value = "id") Long id) {
        carService.deleteCar(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<Car>> getAllCar(){
        return new ResponseEntity<>(carService.getAllCar(), HttpStatus.OK );
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public Car updateCar(@RequestPart(value = "file", required = false // dosya var mı yok mmu kontrolü yapmaz.
                                                   ) MultipartFile file,
                         @RequestPart("car") Car car){
        return carService.updateCar(file,car);
    }

    @GetMapping("/brand/{brandId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<List<Car>> getProductList(@PathVariable(value = "brandId") Long brandId) {
        return new ResponseEntity<>(carService.getBrandList(brandId), HttpStatus.OK);
    }

}
