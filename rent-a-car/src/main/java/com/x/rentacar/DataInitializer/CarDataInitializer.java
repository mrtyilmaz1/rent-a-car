package com.x.rentacar.dataInitializer;

import com.x.rentacar.enums.Brands;
import com.x.rentacar.enums.Colors;
import com.x.rentacar.enums.Gear;
import com.x.rentacar.model.Brand;
import com.x.rentacar.model.Car;
import com.x.rentacar.repository.BrandRepository;
import com.x.rentacar.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
//Bu anotasyon, bu sınıfın bir Spring bileşeni (component) olduğunu belirtir.
//Bu, sınıfın Spring tarafından yönetilen bir bileşen olduğu ve
//Spring tarafından otomatik olarak tespit edilip yönetileceği anlamına gelir.
public class CarDataInitializer implements CommandLineRunner {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Override
    public void run(String... args) throws Exception {

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

        brandRepository.save(brand1);
        brandRepository.save(brand2);
        brandRepository.save(brand3);
        brandRepository.save(brand4);
        brandRepository.save(brand5);
        brandRepository.save(brand6);

        Car car1 = new Car();
        car1.setId(1L); car1.setBrandId(1L);
        car1.setBrand(Brands.RENAULT); car1.setModel("Megan");
        car1.setColor(Colors.WHITE); car1.setGear(Gear.MANUAL);
        car1.setPrice(1000.0); car1.setYear(2016); car1.setTotalKm(115000);
        car1.setUnitsInStock(5); car1.setActive(true);
        car1.setImage("C://Users//murat//Desktop//HTML//rent-a-car-ui//SampleImages//WhiteHondaCivic");

        Car car2 = new Car();
        car2.setId(2L); car2.setBrandId(2L);
        car2.setBrand(Brands.HONDA); car2.setModel("CIVIC");
        car2.setColor(Colors.BLACK); car2.setGear(Gear.AUTOMATIC);
        car2.setPrice(1200.0); car2.setYear(2022); car2.setTotalKm(160000);
        car2.setUnitsInStock(2); car2.setActive(true);
        car2.setImage("C://Users//murat//Desktop//HTML//rent-a-car-ui//SampleImages//BlackFiatEgea");
        carRepository.save(car1);
        carRepository.save(car2);

    }
}
