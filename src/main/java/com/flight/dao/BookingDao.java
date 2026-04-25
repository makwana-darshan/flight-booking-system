package com.flight.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.flight.entity.Booking;
import com.flight.repository.BookingRepository;

@Repository
public class BookingDao {

	@Autowired
	private BookingRepository bookingRepository;

	// Add a single booking
	public Booking saveBooking(Booking booking) {
		return bookingRepository.save(booking);
	}

	// Add multiple bookings
	public List<Booking> saveAllBooking(List<Booking> bookings) {
		return bookingRepository.saveAll(bookings);
	}

	// Get all bookings
	public List<Booking> getAllBookings() {
		return bookingRepository.findAll();
	}

	// Get booking by ID
	public Optional<Booking> getBookingById(Integer id) {
		return bookingRepository.findById(id);
	}

	// Delete booking
	public void deleteBooking(Booking booking) {
		bookingRepository.delete(booking);
	}

	// Get booking by flight ID
	public List<Booking> getBookingByFlightId(Integer flightId) {
		return bookingRepository.findByFlightId(flightId);
	}

	// Get booking by status
	public List<Booking> getBookingByStatus(String status) {
		return bookingRepository.findByStatus(status);
	}

	// Update booking
	public Booking updateBooking(Booking booking) {
		return bookingRepository.save(booking);
	}

	// Get booking by date
	public List<Booking> getBookingByDate(LocalDateTime date) {
		return bookingRepository.findByBookingDate(date);
	}

	// Get booking with pagination and sorting
	public Page<Booking> getBookingPageAndSort(Integer pageNumber, Integer pageSize, String field) {
		return bookingRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(field).ascending()));
	}
}
