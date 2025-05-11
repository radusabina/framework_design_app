package com.example.demo.service;


import com.example.demo.api.model.Transport;
import com.example.demo.repository.TransportRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TransportService {
    @Autowired
    private final TransportRepository _transportRepository;

    public void create(Transport transport) {
        _transportRepository.save(transport);
    }

    public List<Transport> getAll() {
        return _transportRepository.findAll();
    }

    public Optional<Transport> getById(int transportId) {
        return _transportRepository.findById(transportId);
    }

    public void update(int transportId, Transport updatedTransport) {
        Optional<Transport> existingTransport = _transportRepository.findById(transportId);
        if (existingTransport.isPresent()) {
            _transportRepository.update(transportId, updatedTransport.getPrice(), updatedTransport.getType());
        } else {
            throw new RuntimeException("Transport not found with id: " + transportId);
        }
    }

    public void delete(int transportId) {
        _transportRepository.deleteById(transportId);
    }

    public Transport getTransportByDetails(Transport transport) {
        Transport transportToBeFound;
        for(Transport transport1: _transportRepository.findAll()){
            if(transport1 == transport){
                transportToBeFound = transport1;
                return transportToBeFound;
            }
        }
        throw new RuntimeException("Itinerary does not exist.");
    }
}
