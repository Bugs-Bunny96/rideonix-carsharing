package com.example.carsharing.service;

import com.example.carsharing.model.Booking;
import com.example.carsharing.model.BookingStatus;
import com.example.carsharing.model.Car;
import com.example.carsharing.model.User;
import com.example.carsharing.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {
    private final BookingRepository bookingRepo;

    public BookingService(BookingRepository bookingRepo) {
        this.bookingRepo = bookingRepo;
    }

    public void saveBooking(Booking booking) {
        bookingRepo.save(booking);
    }

    public boolean isCarAvailable(Car car, LocalDate start, LocalDate end) {
        List<Booking> conflicts = bookingRepo
                .findByCarAndStartDateLessThanEqualAndEndDateGreaterThanEqualAndStatus(
                        car, end, start, BookingStatus.ACTIVE);
        return conflicts.isEmpty();
    }

    public List<Booking> getBookingsByCar(Car car) {
        return bookingRepo.findByCar(car);
    }

    public List<Booking> getBookingsByUser(User user) {
        List<Booking> bookings = bookingRepo.findByUser(user);
        updateBookingStatuses(bookings); // если используешь авто-обновление статуса
        return bookings;
    }

    public List<Booking> getAllBookings() {
        return bookingRepo.findAll();
    }

    public List<Booking> searchBookings(LocalDate from, LocalDate to, String username) {
        if (from != null && to != null && username != null && !username.isEmpty()) {
            return bookingRepo.findByStartDateBetweenAndUser_UsernameContainingIgnoreCase(from, to, username);
        } else if (from != null && to != null) {
            return bookingRepo.findByStartDateBetween(from, to);
        } else if (username != null && !username.isEmpty()) {
            return bookingRepo.findByUser_UsernameContainingIgnoreCase(username);
        } else {
            return bookingRepo.findAll();
        }
    }

    public void cancelBooking(Long id) {
        Booking booking = bookingRepo.findById(id).orElseThrow();
        booking.setStatus(BookingStatus.CANCELED);
        booking.setTimestamp(LocalDateTime.now());
        bookingRepo.save(booking);
    }

    public void updateBookingStatuses(List<Booking> bookings) {
        LocalDate today = LocalDate.now();
        for (Booking booking : bookings) {
            if (booking.getStatus() == BookingStatus.ACTIVE && booking.getEndDate().isBefore(today)) {
                booking.setStatus(BookingStatus.EXPIRED);
                booking.setTimestamp(LocalDateTime.now());
                bookingRepo.save(booking);
            }
        }
    }









}
