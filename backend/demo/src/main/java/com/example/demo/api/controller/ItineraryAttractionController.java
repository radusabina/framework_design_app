package com.example.demo.api.controller;


import com.example.demo.api.model.ItineraryAttraction;
import com.example.demo.service.ItineraryAttractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ItineraryAttractionController {
    @Autowired
    ItineraryAttractionService itineraryAttractionService;



    @GetMapping("/itat")
    public List<ItineraryAttraction> getAll() {
        return itineraryAttractionService.getAll();
    }

}
