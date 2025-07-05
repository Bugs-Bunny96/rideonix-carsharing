package com.example.carsharing.repository;

import com.example.carsharing.model.Booking;
import com.example.carsharing.model.BookingStatus;
import com.example.carsharing.model.Car;
import com.example.carsharing.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByCar(Car car);
    List<Booking> findByCarAndStartDateLessThanEqualAndEndDateGreaterThanEqual(Car car, LocalDate end, LocalDate start);
    List<Booking> findByStartDateBetween(LocalDate start, LocalDate end);
    List<Booking> findByUser_UsernameContainingIgnoreCase(String username);
    List<Booking> findByStartDateBetweenAndUser_UsernameContainingIgnoreCase(
            LocalDate start, LocalDate end, String username);
    List<Booking> findByUser(User user);
    List<Booking> findByCarAndStartDateLessThanEqualAndEndDateGreaterThanEqualAndStatus(
            Car car, LocalDate end, LocalDate start, BookingStatus status);



}
