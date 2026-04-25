package com.flight.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.flight.dao.BookingDao;
import com.flight.dao.FlightDao;
import com.flight.dto.ResponseStructure;
import com.flight.entity.Booking;
import com.flight.entity.Flight;
import com.flight.entity.Passenger;
import com.flight.entity.Payment;
import com.flight.exception.IdNotFoundException;
import com.flight.exception.NoRecordAvailableException;

@Service
public class BookingService {

	@Autowired
	private BookingDao bookingDao;

	@Autowired
	private FlightDao flightDao;

	// Add a booking
	public ResponseEntity<ResponseStructure<Booking>> saveBooking(Booking booking) {
		ResponseStructure<Booking> response = new ResponseStructure<>();

		if (booking.getFlight() == null || booking.getFlight().getId() == null) {
			throw new IdNotFoundException("Flight ID is required to create a booking.");
		}

		Optional<Flight> flightOpt = flightDao.getFlightById(booking.getFlight().getId());
		if (flightOpt.isEmpty()) {
			throw new IdNotFoundException("Flight not found for ID: " + booking.getFlight().getId());
		}

		booking.setFlight(flightOpt.get());

		if (booking.getPassengers() != null && !booking.getPassengers().isEmpty()) {
			for (Passenger passenger : booking.getPassengers()) {
				passenger.setBooking(booking);
			}
		}

		int numberOfPassenger = booking.getPassengers().size();
		double ticketPrice = booking.getFlight().getPrice();
		booking.getPayment().setAmount(numberOfPassenger * ticketPrice);
		booking.getPayment().setPaymentDate(LocalDateTime.now());
		booking.getPayment().setBooking(booking);

		response.setStatusCode(HttpStatus.CREATED.value());
		response.setMessage("Booking created successfully.");
		response.setData(bookingDao.saveBooking(booking));

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	// Add multiple bookings
	public ResponseEntity<ResponseStructure<List<Booking>>> saveAllBookings(List<Booking> bookings) {
		ResponseStructure<List<Booking>> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.CREATED.value());
		response.setMessage("Multiple bookings added successfully.");
		response.setData(bookingDao.saveAllBooking(bookings));
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	// Get all bookings
	public ResponseEntity<ResponseStructure<List<Booking>>> getAllBookings() {
		List<Booking> bookings = bookingDao.getAllBookings();

		if (bookings.isEmpty()) {
			throw new NoRecordAvailableException("No bookings available.");
		}

		ResponseStructure<List<Booking>> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("All bookings retrieved successfully.");
		response.setData(bookings);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Get booking by ID
	public ResponseEntity<ResponseStructure<Booking>> getBookingById(Integer id) {
		Optional<Booking> opt = bookingDao.getBookingById(id);

		if (opt.isEmpty()) {
			throw new IdNotFoundException("Booking not found for ID: " + id);
		}

		ResponseStructure<Booking> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Booking record retrieved successfully for ID: " + id + ".");
		response.setData(opt.get());

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Get bookings by flight ID
	public ResponseEntity<ResponseStructure<List<Booking>>> getBookingByFlightId(Integer flightId) {
		List<Booking> bookings = bookingDao.getBookingByFlightId(flightId);

		if (bookings.isEmpty()) {
			throw new NoRecordAvailableException("No bookings found for flight ID: " + flightId);
		}

		ResponseStructure<List<Booking>> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Bookings retrieved successfully for flight ID: " + flightId + ".");
		response.setData(bookings);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Get passengers for a booking
	public ResponseEntity<ResponseStructure<List<Passenger>>> getPassengersByBookingId(Integer id) {
		Optional<Booking> bookingOpt = bookingDao.getBookingById(id);

		if (bookingOpt.isEmpty()) {
			throw new IdNotFoundException("Booking not found for ID: " + id);
		}

		List<Passenger> passengers = bookingOpt.get().getPassengers();
		if (passengers.isEmpty()) {
			throw new NoRecordAvailableException("No passengers found for booking ID: " + id);
		}

		ResponseStructure<List<Passenger>> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Passengers retrieved successfully for booking ID: " + id + ".");
		response.setData(passengers);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Get payment details of a booking
	public ResponseEntity<ResponseStructure<Payment>> getPaymentDetail(Integer id) {
		Optional<Booking> bookingOpt = bookingDao.getBookingById(id);

		if (bookingOpt.isEmpty()) {
			throw new IdNotFoundException("Booking not found for ID: " + id);
		}

		Payment payment = bookingOpt.get().getPayment();

		if (payment == null) {
			throw new NoRecordAvailableException("No payment found for booking ID: " + id);
		}

		ResponseStructure<Payment> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Payment details retrieved successfully for booking ID: " + id + ".");
		response.setData(payment);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Get bookings by date
	public ResponseEntity<ResponseStructure<List<Booking>>> getBookingByDate(String dateTime) {
		LocalDateTime date = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		List<Booking> bookings = bookingDao.getBookingByDate(date);

		if (bookings.isEmpty()) {
			throw new NoRecordAvailableException("No bookings found for date: " + date);
		}

		ResponseStructure<List<Booking>> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Bookings retrieved successfully for date: " + date + ".");
		response.setData(bookings);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Update booking
	public ResponseEntity<ResponseStructure<Booking>> updateBooking(Booking booking) {
		Optional<Booking> opt = bookingDao.getBookingById(booking.getId());

		if (opt.isEmpty()) {
			throw new IdNotFoundException("Cannot update — Booking not found for ID: " + booking.getId());
		}

		Booking updated = bookingDao.updateBooking(booking);

		ResponseStructure<Booking> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Booking updated successfully for ID: " + booking.getId() + ".");
		response.setData(updated);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Get bookings by status
	public ResponseEntity<ResponseStructure<List<Booking>>> getBookingByStatus(String status) {
		List<Booking> bookings = bookingDao.getBookingByStatus(status.toUpperCase());

		if (bookings.isEmpty()) {
			throw new NoRecordAvailableException("No bookings found with status: " + status);
		}

		ResponseStructure<List<Booking>> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Bookings retrieved successfully with status: " + status + ".");
		response.setData(bookings);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Delete booking
	public ResponseEntity<ResponseStructure<String>> deleteBooking(Integer id) {
		Optional<Booking> opt = bookingDao.getBookingById(id);

		if (opt.isEmpty()) {
			throw new IdNotFoundException("Booking not found for ID: " + id);
		}

		bookingDao.deleteBooking(opt.get());

		ResponseStructure<String> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Booking deleted successfully.");
		response.setData("Booking with ID " + id + " was removed from the system.");

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Get paginated and sorted bookings
	public ResponseEntity<ResponseStructure<Page<Booking>>> getBookingByPageAndSort(int pageNumber, int pageSize,
			String field) {
		Page<Booking> bookings = bookingDao.getBookingPageAndSort(pageNumber, pageSize, field);

		if (bookings.isEmpty()) {
			throw new NoRecordAvailableException("No booking records found.");
		}

		ResponseStructure<Page<Booking>> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Bookings retrieved successfully (page: " + pageNumber + ", size: " + pageSize
				+ ", sorted by: " + field + ").");
		response.setData(bookings);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
