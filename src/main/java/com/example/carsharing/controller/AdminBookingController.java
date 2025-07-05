package com.example.carsharing.controller;

import com.example.carsharing.model.Booking;
import com.example.carsharing.service.BookingService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/admin/bookings")
public class AdminBookingController {

    private final BookingService bookingService;

    public AdminBookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public String listBookings(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                               @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
                               @RequestParam(required = false) String username,
                               Model model) {
        List<Booking> bookings = bookingService.searchBookings(from, to, username);
        model.addAttribute("bookings", bookings);
        model.addAttribute("from", from);
        model.addAttribute("to", to);
        model.addAttribute("username", username);
        return "admin/booking-list";
    }
}
