package com.example.demo.repository;

import com.example.demo.api.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, Integer>{
    Optional<User> findByEmail(String email);
    void deleteByEmail(String email);
    Optional<User> findByEmailAndPassword(String email, String password);
    @Query(value="select * from public.user u where u.phone_number = :phone_number",nativeQuery=true)
    Optional<User> findByPhone_number(String phone_number);

    @Transactional
    @Modifying
    @Query("update User u set u.first_name = ?2, u.last_name = ?3," +
            " u.password = ?4, u.phone_number = ?5 where u.email = ?1")
    void update(String email, String first_name, String last_name, String password, String phone_number);
}
