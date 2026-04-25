package com.flight.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.flight.dto.ResponseStructure;
import com.flight.entity.Passenger;
import com.flight.service.PassengerService;

@RestController
@RequestMapping("/passenger")
public class PassengerController {

	@Autowired
	private PassengerService passengerService;

	// Add passenger
	@PostMapping
	public ResponseEntity<ResponseStructure<Passenger>> savePassenger(@RequestBody Passenger passenger) {
		return passengerService.savePassenger(passenger);
	}

	// Get all passengers
	@GetMapping
	public ResponseEntity<ResponseStructure<List<Passenger>>> getAllPassengers() {
		return passengerService.getAllPassengers();
	}

	// Get passenger by ID
	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<Passenger>> getPassengerById(@PathVariable Integer id) {
		return passengerService.getPassengerById(id);
	}

	// Update passenger
	@PutMapping
	public ResponseEntity<ResponseStructure<Passenger>> updatePassenger(@RequestBody Passenger passenger) {
		return passengerService.updatePassenger(passenger);
	}

	// Get passenger by contact number
	@GetMapping("/contact/{number}")//not working properly
	public ResponseEntity<ResponseStructure<Passenger>> getPassengerByContactNumber(@PathVariable Long number) {
		return passengerService.getPassengerByContactNumber(number);
	}

	// Get passengers with pagination and sorting
	@GetMapping("/paging/{pageNumber}/{pageSize}/{field}")
	public ResponseEntity<ResponseStructure<Page<Passenger>>> getPassengersByPagingAndSort(@PathVariable int pageNumber,
			@PathVariable int pageSize, @PathVariable String field) {
		return passengerService.getPassengerByPageAndSort(pageNumber, pageSize, field);
	}
}
