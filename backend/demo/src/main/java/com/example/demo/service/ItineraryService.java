package com.example.demo.service;

import com.example.demo.api.model.Itinerary;
import com.example.demo.repository.ItineraryRepository;
import com.example.demo.repository.LocationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ItineraryService {
    @Autowired
    private final ItineraryRepository itineraryRepository;

    @Autowired
    private final LocationRepository locationRepository;

    public void create(Itinerary itinerary) {
        itineraryRepository.save(itinerary);
    }
    public List<Itinerary> getAll(){
        return itineraryRepository.findAll();
    }

    public Optional<Itinerary> getById(int itineraryId) {
        return itineraryRepository.findById(itineraryId);
    }

    public void update(int itineraryId, Itinerary updatedItinerary) {
        Optional<Itinerary> existingItinerary = itineraryRepository.findById(itineraryId);
        if (existingItinerary.isPresent()) {
            itineraryRepository.update(itineraryId,  updatedItinerary.getName(), updatedItinerary.getBudget(),
                    updatedItinerary.getArrival_date(), updatedItinerary.getDeparture_date(),
                    updatedItinerary.getDeparture_location().getId(), updatedItinerary.getDestination_location().getId(),
                    updatedItinerary.getPersons());
        } else {
            // TODO Trebuie sa tratam cazul in care nu exista
            throw new RuntimeException("Itinerary not found with id: " + itineraryId);
        }
    }

    public void delete(int itineraryId) {
        // TODO trebuie sa tratam si cazul in care nu exista userul pe care vrem sa il stergem
        itineraryRepository.deleteById(itineraryId);
    }

    public List<Itinerary> getItinerariesByUserId(int userId) {
        return itineraryRepository.findByUser_Id(userId);
    }
    public Itinerary getItineraryByDetails(Itinerary itinerary) {
        Itinerary itineraryToBeFound;
        for(Itinerary itinerary1: itineraryRepository.findAll()){
            if(itinerary1 == itinerary){
                itineraryToBeFound = itinerary1;
                return itineraryToBeFound;
            }
        }
        throw new RuntimeException("Itinerary does not exist.");
    }

    public Itinerary findById(Integer id) {
        // Folosește repository-ul pentru a căuta itinerariul
        return itineraryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Itinerary not found with id: " + id));
    }
}
