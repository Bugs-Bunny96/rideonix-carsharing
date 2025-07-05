package com.example.carsharing.service;

import com.example.carsharing.model.Car;
import com.example.carsharing.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CarService {
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public void saveCar(Car car) {
        carRepository.save(car);
    }

    public Optional<Car> getCarById(Long id) {
        return carRepository.findById(id);
    }

    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }

    public List<Car> searchCars(String query, BigDecimal maxPrice, Integer year) {
        if (query != null && !query.isEmpty() && maxPrice != null && year != null) {
            return carRepository.findByBrandContainingIgnoreCaseOrModelContainingIgnoreCaseAndPricePerDayLessThanEqualAndYear(
                    query, query, maxPrice, year);
        } else if (query != null && !query.isEmpty() && maxPrice != null) {
            return carRepository.findByBrandContainingIgnoreCaseAndPricePerDayLessThanEqual(query, maxPrice);
        } else if (query != null && !query.isEmpty()) {
            return carRepository.findByModelContainingIgnoreCaseOrBrandContainingIgnoreCase(query, query);
        } else if (maxPrice != null) {
            return carRepository.findByPricePerDayLessThanEqual(maxPrice);
        } else if (year != null) {
            return carRepository.findByYear(year); // ➕ нужен этот метод в репозитории
        } else {
            return carRepository.findAll();
        }
    }


}
