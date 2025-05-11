package com.example.demo.repository;

import com.example.demo.api.model.Attraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AttractionRepository extends JpaRepository<Attraction, Integer>{
}
