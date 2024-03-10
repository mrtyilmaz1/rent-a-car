package com.x.rentacar;

import com.x.rentacar.enums.Brands;
import com.x.rentacar.model.Brand;
import com.x.rentacar.repository.BrandRepository;
import com.x.rentacar.service.BrandService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BrandServiceTest {

    @Mock
    private BrandRepository brandRepository;

    @InjectMocks
    private BrandService brandService;

    @Test
    public void testAddBrand() {
        // Test için kullanılacak örnek Brand nesnesi
        Brand testBrand = new Brand();
        testBrand.setId(1L);
        testBrand.setBrand(Brands.HONDA);

        // brandRepository.save metodu çağrıldığında testBrand nesnesini döndürmesini sağlar
        when(brandRepository.save(any(Brand.class))).thenReturn(testBrand);

        // addBrand metodunu çağırarak test işlemi gerçekleştirilir
        Brand result = brandService.addBrand(testBrand);

        // brandRepository.save metodu bir kez çağrıldı mı kontrol edilir
        verify(brandRepository, times(1)).save(any(Brand.class));

        // Test edilen metot tarafından dönen sonucun doğru olup olmadığı kontrol edilir
        assertEquals(1L, result.getId());
        assertEquals(Brands.HONDA, result.getBrand());
    }

    @Test
    public void testGetBrandList() {
        // Test için kullanılacak örnek markalar
        Brand brand1 = new Brand();
        brand1.setId(1L); brand1.setBrand(Brands.RENAULT);

        Brand brand2 = new Brand();
        brand2.setId(2L); brand2.setBrand(Brands.HONDA);

        Brand brand3 = new Brand();
        brand3.setId(3L); brand3.setBrand(Brands.AUDI);

        Brand brand4 = new Brand();
        brand4.setId(4L); brand4.setBrand(Brands.OPEL);

        Brand brand5 = new Brand();
        brand5.setId(5L); brand5.setBrand(Brands.TOYOTA);

        Brand brand6 = new Brand();
        brand6.setId(6L); brand6.setBrand(Brands.FIAT);

        List<Brand> expectedBrands = Arrays.asList(brand1, brand2);

        // brandRepository.findAll metodu çağrıldığında expectedBrands listesini döndürmesini sağlar
        when(brandRepository.findAll()).thenReturn(expectedBrands);

        // getBrandList metodunu çağırarak test işlemi gerçekleştirilir
        List<Brand> result = brandService.getBrandList();

        // brandRepository.findAll metodu bir kez çağrıldı mı kontrol edilir
        verify(brandRepository, times(1)).findAll();

        // Test edilen metot tarafından dönen sonucun doğru olup olmadığı kontrol edilir
        assertEquals(expectedBrands, result);
    }

    @Test
    public void testGetBrand() {
        // Test için kullanılacak örnek marka
        Brand expectedBrand = new Brand();
        expectedBrand.setId(1L);
        expectedBrand.setBrand(Brands.HONDA);

        // brandRepository.findById metodu çağrıldığında expectedBrand'i içeren bir Optional nesnesini döndürmesini sağlar
        when(brandRepository.findById(1L)).thenReturn(Optional.of(expectedBrand));

        // getBrand metodunu çağırarak test işlemi gerçekleştirilir
        Brand result = brandService.getBrand(1L);

        // brandRepository.findById metodu bir kez çağrıldı mı kontrol edilir
        verify(brandRepository, times(1)).findById(1L);

        // Test edilen metot tarafından dönen sonucun doğru olup olmadığı kontrol edilir
        assertEquals(expectedBrand, result);
    }

    @Test
    public void testDeleteBrand() {
        // deleteBrand metodunu çağırarak test işlemi gerçekleştirilir
        brandService.deleteBrand(1L);

        // brandRepository.deleteById metodu bir kez çağrıldı mı kontrol edilir
        verify(brandRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testUpdateBrand() {
        // Test için kullanılacak örnek marka
        Brand brandToUpdate = new Brand();
        brandToUpdate.setId(1L);
        brandToUpdate.setBrand(Brands.AUDI);

        // brandRepository.save metodu çağrıldığında brandToUpdate'u içeren bir Brand nesnesini döndürmesini sağlar
        when(brandRepository.save(brandToUpdate)).thenReturn(brandToUpdate);

        // updateBrand metodunu çağırarak test işlemi gerçekleştirilir
        Brand result = brandService.updateBrand(brandToUpdate);

        // brandRepository.save metodu bir kez çağrıldı mı kontrol edilir
        verify(brandRepository, times(1)).save(brandToUpdate);

        // Test edilen metot tarafından dönen sonucun doğru olup olmadığı kontrol edilir
        assertEquals(brandToUpdate, result);
    }

}
