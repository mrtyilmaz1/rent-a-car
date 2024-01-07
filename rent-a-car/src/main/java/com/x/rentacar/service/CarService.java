package com.x.rentacar.service;

import com.x.rentacar.model.Car;
import com.x.rentacar.repository.CarRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional // Update işlemi başarısız olursa db'yi eski haline getirecek.
public class CarService {
    private CarRepository carRepository;
    private static final String UPLOAD_DIR = "uploads";

    public Car updateCar (MultipartFile file, Car car){ // MultipartFile file parametresi, bu metodun çağrıldığı yerden gelen dosya verilerini almak için kullanılır.
        if (Objects.nonNull(file)){
            String imagePath = saveFile(file, String.valueOf(car.getBrand()));
            car.setImage(imagePath);
        }
        return carRepository.save(car);
    }

    public Car saveCar(MultipartFile file, Car car) {
        String imagePath = saveFile(file, String.valueOf(car.getBrand()));
        car.setImage(imagePath);
        return carRepository.save(car);
    }

    public String saveFile (MultipartFile file, String brand){
        String fileName = brand + "." + StringUtils.getFilenameExtension(file.getOriginalFilename());
        Path uploadPath = Path.of(UPLOAD_DIR);
        Path filePath;
        try{
            Files.createDirectories(uploadPath);
            filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(),filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
        return filePath.toString();
    }

    public List<Car> getBrandList(Long brandId) {
        return carRepository.findCarListByBrandId(brandId);
    }

    public Car getCarById(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car bulunamadı."));
    }

    public Car updateCar(Car car){ return carRepository.save(car); }

    public void deactiveCar(Long id){ carRepository.updateStatusOfCarById(false,id); }

    public void activeCar(Long id){ carRepository.updateStatusOfCarById(true,id); }

    public void deleteCar(Long id) { carRepository.deleteById(id); }

    public List<Car> getAllCar() { return carRepository.findAll(); }

}
