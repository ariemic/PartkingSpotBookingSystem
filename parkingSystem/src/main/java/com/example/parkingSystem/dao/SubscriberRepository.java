package com.example.parkingSystem.dao;

import com.example.parkingSystem.entity.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;


public interface SubscriberRepository extends JpaRepository<Subscriber, String> {
    @Query("SELECT s FROM Subscriber s WHERE s.endDate > :date")
    List<Subscriber> findAllByEndDateAfter(@Param("date") Date date);


}
