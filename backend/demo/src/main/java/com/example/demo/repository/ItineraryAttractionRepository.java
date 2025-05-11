package com.example.demo.repository;

import com.example.demo.api.model.ItineraryAttraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ItineraryAttractionRepository extends JpaRepository<ItineraryAttraction, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM ItineraryAttraction ia WHERE ia.attraction.id = :attractionId")
    void deleteByAttractionId(int attractionId);
}
