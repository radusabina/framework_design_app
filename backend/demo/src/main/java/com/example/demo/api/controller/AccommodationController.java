package com.example.demo.api.controller;


import com.example.demo.api.model.Accommodation;
import com.example.demo.service.AccommodationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AccommodationController {
    @Autowired
    AccommodationService accommodationService;

    @PostMapping("/accommodation")
    public void create(@RequestBody Accommodation accommodation) {
        accommodationService.create(accommodation);
    }

    @PutMapping("/accommodation")
    public void update(@RequestBody Accommodation accommodation) {
        accommodationService.update(accommodation.getId(), accommodation);
    }

    @DeleteMapping("/accommodation/{id}")
    public void delete(@PathVariable int id) {
        accommodationService.delete(id);
    }

    @GetMapping("/accommodation")
    public List<Accommodation> getAll() {
        return accommodationService.getAll();
    }

}
