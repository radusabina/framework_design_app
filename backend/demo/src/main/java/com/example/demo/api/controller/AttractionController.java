package com.example.demo.api.controller;

import com.example.demo.api.model.Attraction;
import com.example.demo.api.model.Itinerary;
import com.example.demo.api.model.ItineraryAttraction;
import com.example.demo.service.AttractionService;
import com.example.demo.service.ItineraryAttractionService;
import com.example.demo.service.ItineraryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AttractionController {
    @Autowired
    AttractionService attractionService;
    @Autowired
    ItineraryService itineraryService;
    @Autowired
    ItineraryAttractionService itineraryAttractionService;

    @PostMapping("/attraction")
    public ResponseEntity<?> create(@RequestBody Attraction attraction) {
        try {
            // Salvează entitatea Attraction în baza de date
            Attraction savedAttraction = attractionService.create(attraction);
            ItineraryAttraction newItineraryAttraction = null;

            // Verifică dacă itineraryId este diferit de null
            if (attraction.getItineraryId() != null) {
                // Găsește itinerariul folosind id-ul furnizat
                Optional<Itinerary> optionalItinerary = Optional.ofNullable(itineraryService.findById(attraction.getItineraryId()));

                // Verifică dacă itinerariul a fost găsit
                if (optionalItinerary.isPresent()) {
                    Itinerary itinerary = optionalItinerary.get();
                    // Creează și salvează legătura în ItineraryAttraction
                    newItineraryAttraction = new ItineraryAttraction(new ItineraryAttraction.ItineraryAttractionId(Math.toIntExact(itinerary.getId()), savedAttraction.getId()), itinerary, savedAttraction);
                    itineraryAttractionService.create(newItineraryAttraction);
                }
            }

            // Construiește răspunsul cu entitatea Attraction și detaliile legăturii ItineraryAttraction dacă există
            HashMap<String, Object> response = new HashMap<>();
            response.put("attraction", savedAttraction);


            // Returnează răspunsul complet
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException enfe) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(enfe.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/attraction")
    public List<Attraction> getAllAttractions() {
        return attractionService.getAll();
    }

    @PutMapping("/attraction")
    public void update(@RequestBody Attraction attraction) {
        attractionService.update(attraction.getId(), attraction);
    }

    @DeleteMapping("/attraction/{id}")
    public void delete(@PathVariable int id) {
        attractionService.delete(id);
    }
}
