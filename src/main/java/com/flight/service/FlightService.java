package com.flight.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.flight.dao.FlightDao;
import com.flight.dto.ResponseStructure;
import com.flight.entity.Flight;
import com.flight.exception.IdNotFoundException;
import com.flight.exception.NoRecordAvailableException;

@Service
public class FlightService {

	@Autowired
	private FlightDao flightDao;

	// Add single flight
	public ResponseEntity<ResponseStructure<Flight>> saveFlight(Flight flight) {
		ResponseStructure<Flight> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.CREATED.value());
		response.setMessage("Flight has been added successfully.");
		response.setData(flightDao.saveFlight(flight));

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	// Add multiple flights
	public ResponseEntity<ResponseStructure<List<Flight>>> saveAllFlight(List<Flight> flights) {
		ResponseStructure<List<Flight>> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.CREATED.value());
		response.setMessage("Multiple flight records have been added successfully.");
		response.setData(flightDao.saveAllFlight(flights));

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	// Get all flights
	public ResponseEntity<ResponseStructure<List<Flight>>> getAllFlight() {
		List<Flight> flights = flightDao.getAllFlights();
		ResponseStructure<List<Flight>> response = new ResponseStructure<>();

		if (!flights.isEmpty()) {
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("All flight records retrieved successfully.");
			response.setData(flights);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			throw new NoRecordAvailableException("No flight records available.");
		}
	}

	// Get flight by ID
	public ResponseEntity<ResponseStructure<Flight>> getFlightById(Integer id) {
		Optional<Flight> opt = flightDao.getFlightById(id);
		ResponseStructure<Flight> response = new ResponseStructure<>();

		if (opt.isPresent()) {
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Flight record retrieved successfully for ID: " + id);
			response.setData(opt.get());
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			throw new IdNotFoundException("Flight not found for ID: " + id);
		}
	}

	// Get flights by source and destination
	public ResponseEntity<ResponseStructure<List<Flight>>> getFlightBySourceAndDestination(String source,
			String destination) {
		List<Flight> flights = flightDao.getFlightBySourceAndDestination(source, destination);
		ResponseStructure<List<Flight>> response = new ResponseStructure<>();

		if (!flights.isEmpty()) {
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Flights retrieved successfully from " + source + " to " + destination + ".");
			response.setData(flights);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			throw new NoRecordAvailableException("No flights found between " + source + " and " + destination + ".");
		}
	}

	// Get flights by airline
	public ResponseEntity<ResponseStructure<List<Flight>>> getFlightByAirline(String airline) {
		List<Flight> flights = flightDao.getFlightByAirline(airline);
		ResponseStructure<List<Flight>> response = new ResponseStructure<>();

		if (!flights.isEmpty()) {
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Flights retrieved successfully for airline: " + airline + ".");
			response.setData(flights);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			throw new NoRecordAvailableException("No flights found for airline: " + airline + ".");
		}
	}

	// Update flight
	public ResponseEntity<ResponseStructure<Flight>> updateFlight(Flight flight) {
		Optional<Flight> opt = flightDao.getFlightById(flight.getId());
		ResponseStructure<Flight> response = new ResponseStructure<>();

		if (opt.isPresent()) {
			Flight updated = flightDao.saveFlight(flight);
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Flight details updated successfully for ID: " + flight.getId() + ".");
			response.setData(updated);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			throw new IdNotFoundException("Flight not found for ID: " + flight.getId());
		}
	}

	// Delete flight
	public ResponseEntity<ResponseStructure<String>> deleteFlight(Integer id) {
		Optional<Flight> opt = flightDao.getFlightById(id);
		ResponseStructure<String> response = new ResponseStructure<>();

		if (opt.isPresent()) {
			flightDao.deleteFlight(opt.get());
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Flight record deleted successfully.");
			response.setData("Flight with ID " + id + " was removed from the system.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			throw new IdNotFoundException("Flight not found for ID: " + id);
		}
	}

	// Get flight records with pagination and sorting
	public ResponseEntity<ResponseStructure<Page<Flight>>> getFlightByPageAndSort(int pageNumber, int pageSize,
			String field) {
		Page<Flight> flights = flightDao.getFlightByPageAndSort(pageNumber, pageSize, field);
		ResponseStructure<Page<Flight>> response = new ResponseStructure<>();

		if (!flights.isEmpty()) {
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Flights retrieved successfully (page: " + pageNumber + ", size: " + pageSize
					+ ", sorted by: " + field + ").");
			response.setData(flights);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			throw new NoRecordAvailableException("No flight records found.");
		}
	}
}
