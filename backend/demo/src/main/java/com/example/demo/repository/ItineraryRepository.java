package com.example.demo.repository;

import com.example.demo.api.model.Itinerary;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDate;
import java.util.List;

@RepositoryRestResource
public interface ItineraryRepository extends JpaRepository<Itinerary, Integer> {
    List<Itinerary> findByUser_Id(int userId);

    @Transactional
    @Modifying
    @Query("update Itinerary i set i.name = ?2, i.budget = ?3," +
            "i.arrival_date = ?4, i.departure_date = ?5, i.departure_location.id = ?6, " +
            "i.destination_location.id = ?7, i.persons = ?8 where i.id = ?1")
    void update(int id, String name, int budget, LocalDate arrival_date, LocalDate departure_date,
                int id_departure_location, int id_destination_location, int persons);

}
