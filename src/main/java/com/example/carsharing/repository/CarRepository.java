package com.example.carsharing.repository;

import com.example.carsharing.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import java.math.BigDecimal;
import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByModelContainingIgnoreCaseOrBrandContainingIgnoreCase(String model, String brand);
    List<Car> findByPricePerDayLessThanEqual(BigDecimal maxPrice);
    List<Car> findByBrandContainingIgnoreCaseAndPricePerDayLessThanEqual(String brand, BigDecimal maxPrice);
    List<Car> findByBrandContainingIgnoreCaseOrModelContainingIgnoreCaseAndPricePerDayLessThanEqualAndYear(
            String brand, String model, BigDecimal price, Integer year);
    List<Car> findByYear(Integer year);


}
