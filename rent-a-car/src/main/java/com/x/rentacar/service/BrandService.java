package com.x.rentacar.service;

import com.x.rentacar.model.Brand;
import com.x.rentacar.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;

    public Brand addBrand (Brand brand) {return brandRepository.save(brand);}

    public List<Brand> getBrandList() { return brandRepository.findAll();}

    public Brand getBrand(Long id) { return brandRepository.findById(id).get(); }

    public void deleteBrand(Long id) {
        brandRepository.deleteById(id);
    }

    public Brand updateBrand(Brand brand) {
        return brandRepository.save(brand);
    }



}
