package com.example.demo.service;

import com.example.demo.api.model.Attraction;
import com.example.demo.repository.AttractionRepository;
import com.example.demo.repository.ItineraryAttractionRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AttractionService {
    @Autowired
    private final AttractionRepository _attractionRepository;
    @Autowired
    private ItineraryAttractionRepository itineraryAttractionRepository;

    public Attraction create(Attraction attraction) {
        // TODO validari in cazul in care este necesar
        _attractionRepository.save(attraction);
        return attraction;
    }
    public List<Attraction> getAll(){
        return _attractionRepository.findAll();
    }

    public Optional<Attraction> getById(int attractionId) {
        return _attractionRepository.findById(attractionId);
    }

    public void update(int attractionId, Attraction updatedAttraction) {
        Optional<Attraction> existingAttraction = _attractionRepository.findById(attractionId);
        if (existingAttraction.isPresent()) {
            Attraction attractionToUpdate = existingAttraction.get();
            attractionToUpdate.setLocation(updatedAttraction.getLocation());
            attractionToUpdate.setName(updatedAttraction.getName());
            attractionToUpdate.setPrice(updatedAttraction.getPrice());
            _attractionRepository.save(attractionToUpdate);
        } else {
            // TODO Trebuie sa tratam cazul in care nu exista
            throw new RuntimeException("Attraction not found with id: " + attractionId);
        }
    }

    public void delete(int attractionId) {
        // TODO trebuie sa tratam si cazul in care nu exista userul pe care vrem sa il stergem

        itineraryAttractionRepository.deleteByAttractionId(attractionId);
        _attractionRepository.deleteById(attractionId);
    }

}

