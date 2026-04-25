package com.flight.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.flight.dao.PassengerDao;
import com.flight.dto.ResponseStructure;
import com.flight.entity.Passenger;
import com.flight.exception.IdNotFoundException;
import com.flight.exception.NoRecordAvailableException;

@Service
public class PassengerService {

	@Autowired
	private PassengerDao passengerDao;

	// Add new passenger
	public ResponseEntity<ResponseStructure<Passenger>> savePassenger(Passenger passenger) {
		ResponseStructure<Passenger> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.CREATED.value());
		response.setMessage("Passenger record added successfully.");
		response.setData(passengerDao.savePassenger(passenger));

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	// Get all passengers
	public ResponseEntity<ResponseStructure<List<Passenger>>> getAllPassengers() {
		List<Passenger> passengers = passengerDao.getAllPassengers();

		if (passengers.isEmpty()) {
			throw new NoRecordAvailableException("No passenger records available.");
		}

		ResponseStructure<List<Passenger>> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("All passenger records retrieved successfully.");
		response.setData(passengers);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Get passenger by ID
	public ResponseEntity<ResponseStructure<Passenger>> getPassengerById(Integer id) {
		Optional<Passenger> opt = passengerDao.getPassengerById(id);

		if (opt.isEmpty()) {
			throw new IdNotFoundException("Passenger not found for ID: " + id);
		}

		ResponseStructure<Passenger> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Passenger record retrieved successfully for ID: " + id + ".");
		response.setData(opt.get());

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Update passenger
	public ResponseEntity<ResponseStructure<Passenger>> updatePassenger(Passenger passenger) {
		Optional<Passenger> opt = passengerDao.getPassengerById(passenger.getId());

		if (opt.isEmpty()) {
			throw new IdNotFoundException("Cannot update — Passenger not found for ID: " + passenger.getId());
		}

		Passenger updatedPassenger = passengerDao.updatePassenger(passenger);
		ResponseStructure<Passenger> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Passenger details updated successfully for ID: " + passenger.getId() + ".");
		response.setData(updatedPassenger);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Get passenger by contact number
	public ResponseEntity<ResponseStructure<Passenger>> getPassengerByContactNumber(Long number) {
		Passenger passenger = passengerDao.getPassengerByContactNumber(number);

		if (passenger == null) {
			throw new IdNotFoundException("Passenger not found for contact number: " + number);
		}

		ResponseStructure<Passenger> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Passenger record retrieved successfully for contact number: " + number + ".");
		response.setData(passenger);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Get passengers with pagination and sorting
	public ResponseEntity<ResponseStructure<Page<Passenger>>> getPassengerByPageAndSort(int pageNumber, int pageSize,
			String field) {

		Page<Passenger> passengers = passengerDao.getPassengerByPageAndSort(pageNumber, pageSize, field);

		if (passengers.isEmpty()) {
			throw new NoRecordAvailableException("No passenger records found.");
		}

		ResponseStructure<Page<Passenger>> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Passengers retrieved successfully (page: " + pageNumber + ", size: " + pageSize
				+ ", sorted by: " + field + ").");
		response.setData(passengers);

		return new ResponseEntity<ResponseStructure<Page<Passenger>>>(response, HttpStatus.OK);
	}
}
