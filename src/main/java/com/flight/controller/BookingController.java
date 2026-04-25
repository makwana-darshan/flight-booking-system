package com.flight.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.flight.dto.ResponseStructure;
import com.flight.entity.Booking;
import com.flight.entity.Passenger;
import com.flight.entity.Payment;
import com.flight.service.BookingService;

@RestController
@RequestMapping("/booking")
public class BookingController {

	@Autowired
	private BookingService bookingService;

	// Add single booking
	@PostMapping
	public ResponseEntity<ResponseStructure<Booking>> saveBooking(@RequestBody Booking booking) {
		return bookingService.saveBooking(booking);
	}

	// Add multiple bookings
	@PostMapping("/all")
	public ResponseEntity<ResponseStructure<List<Booking>>> saveAllBookings(@RequestBody List<Booking> bookings) {
		return bookingService.saveAllBookings(bookings);
	}

	// Get all bookings
	@GetMapping
	public ResponseEntity<ResponseStructure<List<Booking>>> getAllBookings() {
		return bookingService.getAllBookings();
	}

	// Get booking by ID
	@GetMapping("/{id}")//proper data not available
	public ResponseEntity<ResponseStructure<Booking>> getBookingById(@PathVariable Integer id) {
		return bookingService.getBookingById(id);
	}

	// Get bookings by flight ID
	@GetMapping("/flight/{flightId}")
	public ResponseEntity<ResponseStructure<List<Booking>>> getBookingByFlightId(@PathVariable Integer flightId) {
		return bookingService.getBookingByFlightId(flightId);
	}

	// Get bookings by date
	@GetMapping("/date/{date}")
	public ResponseEntity<ResponseStructure<List<Booking>>> getBookingByDate(@PathVariable String date) {
		return bookingService.getBookingByDate(date);
	}

	// Get bookings by status
	@GetMapping("/status/{status}")
	public ResponseEntity<ResponseStructure<List<Booking>>> getBookingByStatus(@PathVariable String status) {
		return bookingService.getBookingByStatus(status);
	}

	// Get passengers for a booking
	@GetMapping("/{id}/passengers")
	public ResponseEntity<ResponseStructure<List<Passenger>>> getPassengersByBooking(@PathVariable Integer id) {
		return bookingService.getPassengersByBookingId(id);
	}

	// Get payment details of booking
	@GetMapping("/{id}/payment")
	public ResponseEntity<ResponseStructure<Payment>> getPaymentDetail(@PathVariable Integer id) {
		return bookingService.getPaymentDetail(id);
	}

	// Update booking
	@PutMapping
	public ResponseEntity<ResponseStructure<Booking>> updateBooking(@RequestBody Booking booking) {
		return bookingService.updateBooking(booking);
	}

	// Delete booking
	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseStructure<String>> deleteBooking(@PathVariable Integer id) {
		return bookingService.deleteBooking(id);
	}

	// Pagination and sorting
	@GetMapping("/paging/{pageNumber}/{pageSize}/{field}")
	public ResponseEntity<ResponseStructure<Page<Booking>>> getBookingsByPageAndSort(@PathVariable int pageNumber,
			@PathVariable int pageSize, @PathVariable String field) {
		return bookingService.getBookingByPageAndSort(pageNumber, pageSize, field);
	}
}
