package com.flight.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.flight.entity.Passenger;
import com.flight.repository.PassengerRepository;

@Repository
public class PassengerDao {

    @Autowired
    private PassengerRepository passengerRepository;

    // Add passenger
    public Passenger savePassenger(Passenger passenger) {
        return passengerRepository.save(passenger);
    }

    // Get all passengers
    public List<Passenger> getAllPassengers() {
        return passengerRepository.findAll();
    }

    // Get passenger by ID
    public Optional<Passenger> getPassengerById(Integer id) {
        return passengerRepository.findById(id);
    }

    // Update passenger
    public Passenger updatePassenger(Passenger passenger) {
        return passengerRepository.save(passenger);
    }

    // Get passenger by contact number
    public Passenger getPassengerByContactNumber(Long number) {
        return passengerRepository.getByContactNumber(number);
    }

    // Pagination and sorting
    public Page<Passenger> getPassengerByPageAndSort(int pageNumber, int pageSize, String field) {
        return passengerRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(field).ascending()));
    }
}
