package com.example.demo.api.controller;


import com.example.demo.api.model.Transport;
import com.example.demo.service.TransportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class TransportController {
    @Autowired
    TransportService transportService;
    @PostMapping("/transport")
    public void create(@RequestBody Transport transport) {
        transportService.create(transport);
    }
    @GetMapping("/transport")
    public List<Transport> getAllTransports(){
        return transportService.getAll();
    }
    @PutMapping("/transport")
    public void update(@RequestBody Transport transport) {
        transportService.update(transport.getId(),transport);
    }

    @DeleteMapping("/transport/{id}")
    public void delete(@PathVariable int id) {
        transportService.delete(id);
    }

}
