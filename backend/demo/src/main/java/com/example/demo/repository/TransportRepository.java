package com.example.demo.repository;

import com.example.demo.api.model.Transport;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TransportRepository  extends JpaRepository<Transport, Integer> {
    @Transactional
    @Modifying
    @Query("update Transport t set t.price = ?2, t.type = ?3 where t.id = ?1")
    void update(int id, float price, String type);
}
