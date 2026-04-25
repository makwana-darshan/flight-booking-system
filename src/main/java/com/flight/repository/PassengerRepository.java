package com.flight.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flight.entity.Passenger;

public interface PassengerRepository extends JpaRepository<Passenger, Integer> {

	Passenger getByContactNumber(Long number);
}
