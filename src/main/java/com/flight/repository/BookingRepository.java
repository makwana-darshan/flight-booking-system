package com.flight.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flight.entity.Booking;
import com.flight.entity.Payment;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

	List<Booking> findByFlightId(Integer flightId);

	List<Booking> findByStatus(String status);

	List<Booking> findByBookingDate(LocalDateTime bookingDate);

	List<Booking> findByPayment(Payment payment);

}
