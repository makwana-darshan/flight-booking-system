package com.flight.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.flight.dto.ResponseStructure;
import com.flight.entity.Flight;
import com.flight.service.FlightService;

@RestController
@RequestMapping("/flight")
public class FlightController {

	@Autowired
	private FlightService flightService;

	// Add single flight
	@PostMapping
	public ResponseEntity<ResponseStructure<Flight>> saveFlight(@RequestBody Flight flight) {
		return flightService.saveFlight(flight);
	}

	// Add multiple flights
	@PostMapping("/all")
	public ResponseEntity<ResponseStructure<List<Flight>>> saveAllFlight(@RequestBody List<Flight> flights) {
		return flightService.saveAllFlight(flights);
	}

	// Get all flights
	@GetMapping
	public ResponseEntity<ResponseStructure<List<Flight>>> getAllFlights() {
		return flightService.getAllFlight();
	}

	// Get flight by ID
	@GetMapping("/id/{id}")
	public ResponseEntity<ResponseStructure<Flight>> getFlightById(@PathVariable Integer id) {
		return flightService.getFlightById(id);
	}

	// Get flights by source and destination
	@GetMapping("/sourceanddestination/{source}/{destination}")
	public ResponseEntity<ResponseStructure<List<Flight>>> getFlightBySourceAndDestination(@PathVariable String source,
			@PathVariable String destination) {
		return flightService.getFlightBySourceAndDestination(source, destination);
	}

	// Get flights by airline
	@GetMapping("/airline/{airline}")
	public ResponseEntity<ResponseStructure<List<Flight>>> getFlightByAirline(@PathVariable String airline) {
		return flightService.getFlightByAirline(airline);
	}

	// Update flight
	@PutMapping
	public ResponseEntity<ResponseStructure<Flight>> updateFlight(@RequestBody Flight flight) {
		return flightService.updateFlight(flight);
	}

	// Delete flight
	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseStructure<String>> deleteFlight(@PathVariable Integer id) {
		return flightService.deleteFlight(id);
	}

	// Get paginated and sorted flights
	@GetMapping("/paging/{pageNumber}/{pageSize}/{field}")
	public ResponseEntity<ResponseStructure<Page<Flight>>> getFlightsByPaging(@PathVariable int pageNumber,
			@PathVariable int pageSize, @PathVariable String field) {
		return flightService.getFlightByPageAndSort(pageNumber, pageSize, field);
	}
}
