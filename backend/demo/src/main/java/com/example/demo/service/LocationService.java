package com.example.demo.service;

import com.example.demo.api.model.Location;
import com.example.demo.repository.LocationRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LocationService {
    @Autowired
    private final LocationRepository _locationRepository;

    public void create(Location location) {
        // TODO validari in cazul in care este necesar
        _locationRepository.save(location);
    }
    public List<Location> getAll(){
        return _locationRepository.findAll();
    }

    public Optional<Location> getById(int locationId) {
        return _locationRepository.findById(locationId);
    }

    public Optional<Location> getByCountryAndCity(String country, String city) {
        return _locationRepository.findByCountryAndCity(country, city);
    }

    public void update(int locationId, Location updatedLocation) {
        Optional<Location> existingLocation = _locationRepository.findById(locationId);
        if (existingLocation.isPresent()) {
            Location locationToUpdate = existingLocation.get();
            locationToUpdate.setCountry(updatedLocation.getCountry());
            locationToUpdate.setCity(updatedLocation.getCity());
            _locationRepository.save(locationToUpdate);
        } else {
            // TODO Trebuie sa tratam cazul in care nu exista
            throw new RuntimeException("Location not found with id: " + locationId);
        }
    }

    public void delete(int locationId) {
        // TODO trebuie sa tratam si cazul in care nu exista userul pe care vrem sa il stergem
        _locationRepository.deleteById(locationId);
    }

}

