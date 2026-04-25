package com.flight.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.flight.entity.Flight;
import com.flight.repository.FlightRepository;

@Repository
public class FlightDao {

    @Autowired
    private FlightRepository flightRepository;

    // Add single flight
    public Flight saveFlight(Flight flight) {
        return flightRepository.save(flight);
    }

    // Add multiple flights
    public List<Flight> saveAllFlight(List<Flight> flights) {
        return flightRepository.saveAll(flights);
    }

    // Get all flights
    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    // Get flight by ID
    public Optional<Flight> getFlightById(Integer id) {
        return flightRepository.findById(id);
    }

    // Get flights by source and destination
    public List<Flight> getFlightBySourceAndDestination(String source, String destination) {
        return flightRepository.findBySourceAndDestination(source, destination);
    }

    // Get flights by airline
    public List<Flight> getFlightByAirline(String airline) {
        return flightRepository.findByAirline(airline);
    }

    // Update flight
    public Flight updateFlight(Flight flight) {
        return flightRepository.save(flight);
    }

    // Delete flight
    public void deleteFlight(Flight flight) {
        flightRepository.delete(flight);
    }

    // Pagination and sorting
    public Page<Flight> getFlightByPageAndSort(int pageNumber, int pageSize, String field) {
        return flightRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(field).ascending()));
    }
}
