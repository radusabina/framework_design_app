package com.example.demo.service;

import com.example.demo.api.model.*;
import com.example.demo.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccommodationService {
    @Autowired
    private final AccommodationRepository accommodationRepository;

    public void create(Accommodation accm) {
        accommodationRepository.save(accm);
    }
    public List<Accommodation> getAll(){
        return accommodationRepository.findAll();
    }

    public Optional<Accommodation> getById(int accId) {
        return accommodationRepository.findById(accId);
    }

    public void update(int accmId, Accommodation updatedAccm) {
        Optional<Accommodation> existingAccm = accommodationRepository.findById(accmId);
        if (existingAccm.isPresent()) {
            String name = updatedAccm.getName();
            String address = updatedAccm.getAddress();
            float price = updatedAccm.getPrice();
            accommodationRepository.update(accmId, name, address, price);
        } else {
            // TODO Trebuie sa tratam cazul in care nu exista
            throw new RuntimeException("Accommodation not found with id: " + accmId);
        }
    }

    public Optional<Accommodation> getByName(String name) {
        return accommodationRepository.findByName(name);
    }

    public void delete(int accId) {
        // TODO trebuie sa tratam si cazul in care nu exista userul pe care vrem sa il stergem
        accommodationRepository.deleteById(accId);
    }

}

