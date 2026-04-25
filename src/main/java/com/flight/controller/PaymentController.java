package com.flight.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.flight.dto.ResponseStructure;
import com.flight.entity.Payment;
import com.flight.service.PaymentService;

@RestController
@RequestMapping("/payment")
public class PaymentController {

	@Autowired
	private PaymentService paymentService;

	// Add new payment
	@PostMapping
	public ResponseEntity<ResponseStructure<Payment>> savePayment(@RequestBody Payment payment) {
		return paymentService.savePayment(payment);
	}

	// Get all payments
	@GetMapping
	public ResponseEntity<ResponseStructure<List<Payment>>> getAllPayments() {
		return paymentService.getAllPayments();
	}

	// Get payment by ID
	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<Payment>> getPaymentById(@PathVariable Integer id) {
		return paymentService.getPaymentById(id);
	}

	// Get payments by status
	@GetMapping("/status/{status}")
	public ResponseEntity<ResponseStructure<List<Payment>>> getPaymentByStatus(@PathVariable String status) {
		return paymentService.getPaymentByStatus(status);
	}

	// Get payments by mode
	@GetMapping("/mode/{mode}")
	public ResponseEntity<ResponseStructure<List<Payment>>> getPaymentByMode(@PathVariable String mode) {
		return paymentService.getPaymentByMode(mode);
	}

	// Get payments greater than a given amount
	@GetMapping("/amount/{amount}")
	public ResponseEntity<ResponseStructure<List<Payment>>> getPaymentAmountGreaterThan(@PathVariable Double amount) {
		return paymentService.getPaymentAmountGreaterThan(amount);
	}

	// Update payment status
	@PutMapping("/{id}/status/{status}")
	public ResponseEntity<ResponseStructure<Payment>> updatePaymentStatus(@PathVariable Integer id,
			@PathVariable String status) {
		return paymentService.updatePaymentStatus(id, status);
	}

	// Pagination and sorting
	@GetMapping("/paging/{pageNumber}/{pageSize}/{field}")
	public ResponseEntity<ResponseStructure<Page<Payment>>> getPaymentsByPaging(@PathVariable int pageNumber,
			@PathVariable int pageSize, @PathVariable String field) {
		return paymentService.getPaymentByPageAndSort(pageNumber, pageSize, field);
	}
}
