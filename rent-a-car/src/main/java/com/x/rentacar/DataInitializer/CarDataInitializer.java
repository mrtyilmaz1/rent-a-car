package com.x.rentacar.dataInitializer;

import com.x.rentacar.enums.Colors;
import com.x.rentacar.enums.Gear;
import com.x.rentacar.model.Cars;
import com.x.rentacar.repository.CarsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
//Bu anotasyon, bu sınıfın bir Spring bileşeni (component) olduğunu belirtir.
//Bu, sınıfın Spring tarafından yönetilen bir bileşen olduğu ve
//Spring tarafından otomatik olarak tespit edilip yönetileceği anlamına gelir.
public class CarDataInitializer implements CommandLineRunner {

    @Autowired
    private CarsRepository carsRepository;

    @Override
    public void run(String... args) throws Exception {
        Cars car1 = new Cars();
        car1.setId(1L); car1.setBrand("Honda"); car1.setModel("Civic");
        car1.setColor(Colors.WHITE); car1.setGear(Gear.MANUAL);
        car1.setPrice(525000.0); car1.setYear(2006); car1.setTotalKm(115000);
        car1.setUnitsInStock(5); car1.setActive(true);
        car1.setImage("C://Users//murat//Desktop//HTML//rent-a-car-ui//SampleImages//WhiteHondaCivic");

        Cars car2 = new Cars();
        car2.setId(2L); car2.setBrand("Fiat"); car2.setModel("Egea");
        car2.setColor(Colors.BLACK); car2.setGear(Gear.AUTOMATIC);
        car2.setPrice(650000.0); car2.setYear(2015); car2.setTotalKm(160000);
        car2.setUnitsInStock(2); car2.setActive(true);
        car2.setImage("C://Users//murat//Desktop//HTML//rent-a-car-ui//SampleImages//BlackFiatEgea");
        carsRepository.save(car1);
        carsRepository.save(car2);

    }
}
