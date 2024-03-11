package com.x.rentacar;

import com.x.rentacar.model.Car;
import com.x.rentacar.repository.CarRepository;
import com.x.rentacar.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarService carService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void updateCarWithFile() {
        // Arrange
        Car car = createTestCar();
        MockMultipartFile file = new MockMultipartFile("file", "test-image.jpg", "image/jpeg", "some image".getBytes());

        // Repository davranışını taklit et
        when(carRepository.save(any(Car.class))).thenReturn(car);

        // Act
        Car result = carService.updateCar(file, car);

        // Assert
        verify(carRepository, times(1)).save(any(Car.class));
    }

    @Test
    void saveCar() {
        // Arrange
        Car car = createTestCar();
        MockMultipartFile file = new MockMultipartFile("file", "test-image.jpg", "image/jpeg", "some image".getBytes());

        // Repository davranışını taklit et
        when(carRepository.save(any(Car.class))).thenReturn(car);

        // Act
        Car result = carService.saveCar(file, car);

        // Assert
        verify(carRepository, times(1)).save(any(Car.class));
    }

    @Test
    void getCarById() {
        // Arrange
        Car car = createTestCar();

        // Repository davranışını taklit et
        when(carRepository.findById(1L)).thenReturn(Optional.of(car));


        Car result = carService.getCarById(1L);


    }

    @Test
    void saveFile() {
        // Arrange
        String brand = "Toyota";
        MockMultipartFile file = new MockMultipartFile("file", "test-image.jpg", "image/jpeg", "some image".getBytes());

        // Act
        String result = carService.saveFile(file, brand);

        // Assert
        assertNotNull(result);
        assertTrue(result.endsWith(".jpg"));
    }

    @Test
    void getBrandList() {
        // Arrange
        Long brandId = 1L;
        when(carRepository.findCarListByBrandId(brandId)).thenReturn(Collections.emptyList());

        // Act
        List<Car> result = carService.getBrandList(brandId);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void updateCar() {
        // Arrange
        Car car = new Car();
        when(carRepository.save(any(Car.class))).thenReturn(car);

        // Act
        Car result = carService.updateCar(car);

        // Assert
        assertNotNull(result);
        assertSame(car, result);
    }

    @Test
    void deleteCar() {
        // Arrange
        Long carId = 1L;
        // Mock repository behavior
        doNothing().when(carRepository).deleteById(carId);

        // Act
        carService.deleteCar(carId);

        // Assert
        // Verify that the deleteById method was called with the expected parameters
        verify(carRepository, times(1)).deleteById(carId);
    }

    @Test
    void getAllCar() {
        // Arrange
        List<Car> mockCarList = Arrays.asList(new Car(), new Car(), new Car());
        when(carRepository.findAll()).thenReturn(mockCarList);

        // Act
        List<Car> result = carService.getAllCar();

        // Assert
        assertNotNull(result);
        assertEquals(mockCarList.size(), result.size());
    }




    private Car createTestCar() {

        Car car = new Car();
        car.setId(1L);

        return car;
    }



}

