package com.example.edts.edts.restfull.repository;

import com.example.edts.edts.restfull.model.Booking;
import com.example.edts.edts.restfull.model.Concert;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {

}
