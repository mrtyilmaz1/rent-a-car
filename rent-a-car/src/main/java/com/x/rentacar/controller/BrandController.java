package com.x.rentacar.controller;

import com.x.rentacar.model.Brand;
import com.x.rentacar.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/brand")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @PostMapping // bu metod sadece HTTP POST istekleri tarafından çağrılabilir.
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')") // Bu durumda, sadece "ROLE_ADMIN" yetkisine sahip kullanıcılar tarafından bu metodun çağrılmasına izin verilir.
    public ResponseEntity<Brand> addBrand (@RequestBody //Spring Framework'ün bir HTTP isteği sırasında gelen veriyi bir Java nesnesine dönüştürmesini sağlar.
                                               Brand brand){
        return new ResponseEntity<>(brandService.addBrand(brand), HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<List<Brand>> getBrandList(){
        return new ResponseEntity<>(brandService.getBrandList(), HttpStatus.OK);
    }

    @GetMapping("/{brandId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Brand> getBrand(@PathVariable(value = "brandId")Long brandId){
        return new ResponseEntity<>(brandService.getBrand(brandId), HttpStatus.OK);
    }

    @DeleteMapping("/brandId")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteBrand(@PathVariable(value = "brandId")Long brandId){
        brandService.deleteBrand(brandId); // nesne silineceği için return içinde değil öncesinde yapılır. Null dönme durumundan dolayı.
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Brand> updateBrand(@RequestBody Brand brand) {
        return new ResponseEntity<>(brandService.updateBrand(brand), HttpStatus.OK);
    }

}
