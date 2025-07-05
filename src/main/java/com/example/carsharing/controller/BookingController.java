package com.example.carsharing.controller;

import com.example.carsharing.model.Booking;
import com.example.carsharing.model.BookingStatus;
import com.example.carsharing.model.Car;
import com.example.carsharing.model.User;
import com.example.carsharing.service.BookingService;
import com.example.carsharing.service.CarService;
import com.example.carsharing.repository.UserRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/cars")
public class BookingController {

    private final CarService carService;
    private final BookingService bookingService;
    private final UserRepository userRepository;

    public BookingController(CarService carService, BookingService bookingService, UserRepository userRepository) {
        this.carService = carService;
        this.bookingService = bookingService;
        this.userRepository = userRepository;
    }

    @GetMapping("/{id}")
    public String viewCar(@PathVariable Long id, Model model) {
        Car car = carService.getCarById(id).orElseThrow();

        List<Booking> bookings = bookingService.getBookingsByCar(car).stream()
                .filter(b -> b.getStatus() == BookingStatus.ACTIVE)
                .toList();

        model.addAttribute("car", car);
        model.addAttribute("bookings", bookings);
        return "car-details";
    }

    @PostMapping("/{id}/book")
    public String bookCar(@PathVariable Long id,
                          @RequestParam("email") String email,
                          @RequestParam("phone") String phone,
                          @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                          @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                          @AuthenticationPrincipal UserDetails userDetails,
                          Model model) {

        Car car = carService.getCarById(id).orElseThrow();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();

        if (endDate.isBefore(startDate)) {
            model.addAttribute("car", car);
            model.addAttribute("bookings", bookingService.getBookingsByCar(car));
            model.addAttribute("error", "End date cannot be before start date.");
            return "car-details";
        }

        boolean available = bookingService.isCarAvailable(car, startDate, endDate);

        if (!available) {
            model.addAttribute("car", car);
            model.addAttribute("bookings", bookingService.getBookingsByCar(car));
            model.addAttribute("error", "Selected dates are not available!");
            return "car-details";
        }

        Booking booking = new Booking();
        booking.setCar(car);
        booking.setUser(user);
        booking.setEmail(email);
        booking.setPhone(phone);
        booking.setStartDate(startDate);
        booking.setEndDate(endDate);
        booking.setTimestamp(LocalDateTime.now());

        bookingService.saveBooking(booking);
        return "redirect:/cars/" + id;
    }


    @GetMapping("/my-bookings")
    public String myBookings(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        List<Booking> bookings = bookingService.getBookingsByUser(user); // ✅ всегда только свои
        model.addAttribute("bookings", bookings);
        model.addAttribute("username", user.getUsername());
        return "my-bookings";
    }



    @GetMapping
    public String listCars(@RequestParam(required = false) String query,
                           @RequestParam(required = false) BigDecimal maxPrice,
                           @RequestParam(required = false) Integer year,
                           Model model) {
        List<Car> cars = carService.searchCars(query, maxPrice, year);
        model.addAttribute("cars", cars);
        model.addAttribute("query", query);
        model.addAttribute("maxPrice", maxPrice);
        model.addAttribute("year", year);
        return "car-list";
    }

    @PostMapping("/bookings/{id}/cancel")
    public String cancelBooking(@PathVariable Long id,
                                @AuthenticationPrincipal UserDetails userDetails) {
        Booking booking = bookingService.getAllBookings().stream()
                .filter(b -> b.getId().equals(id))
                .findFirst()
                .orElseThrow();

        if (booking.getStatus() == BookingStatus.ACTIVE &&
                booking.getStartDate().isAfter(LocalDate.now().plusDays(1)) &&
                booking.getUser().getUsername().equals(userDetails.getUsername())) {
            bookingService.cancelBooking(id);
        }
        return "redirect:/cars/my-bookings";
    }






}
