package com.example.demo.repository;


import com.example.demo.api.model.*;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface AccommodationRepository extends JpaRepository<Accommodation, Integer>{
    Optional<Accommodation> findByName(String name);

    @Transactional
    @Modifying
    @Query("update Accommodation a set a.name = ?2, a.address = ?3, a.price = ?4 where a.id = ?1")
    void update(int id, String name, String address, float price);
}
