package com.example.carsharing.controller;

import com.example.carsharing.model.Car;
import com.example.carsharing.service.CarService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/cars")
public class AdminCarController {

    private final CarService carService;

    public AdminCarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public String listCars(Model model) {
        model.addAttribute("cars", carService.getAllCars());
        return "admin/car-list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("car", new Car());
        return "admin/car-form";
    }

    @PostMapping("/add")
    public String saveCar(@ModelAttribute Car car) {
        carService.saveCar(car);
        return "redirect:/admin/cars";
    }

    @GetMapping("/edit/{id}")
    public String editCar(@PathVariable Long id, Model model) {
        Car car = carService.getCarById(id).orElseThrow();
        model.addAttribute("car", car);
        return "admin/car-form";
    }

    @PostMapping("/delete/{id}")
    public String deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
        return "redirect:/admin/cars";
    }
}
