package com.flight.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.flight.dto.PaymentStatus;
import com.flight.entity.Payment;
import com.flight.repository.PaymentRepository;

@Repository
public class PaymentDao {

	@Autowired
	private PaymentRepository paymentRepository;

	// Save single payment
	public Payment savePayment(Payment payment) {
		return paymentRepository.save(payment);
	}

	// Get all payments
	public List<Payment> getAllPayments() {
		return paymentRepository.findAll();
	}

	// Get payment by ID
	public Optional<Payment> getPaymentById(Integer id) {
		return paymentRepository.findById(id);
	}

	// Get payments by status
	public List<Payment> getPaymentByStatus(PaymentStatus status) {
		return paymentRepository.findByStatus(status);
	}

	// Get payments by mode
	public List<Payment> getPaymentByMode(String mode) {
		return paymentRepository.findByMode(mode);
	}

	// Get payments greater than a specific amount
	public List<Payment> getPaymentAmountGreaterThan(Double amount) {
		return paymentRepository.findByAmountGreaterThan(amount);
	}

	// Update payment
	public Payment updatePayment(Payment payment) {
		return paymentRepository.save(payment);
	}

	// Pagination and sorting
	public Page<Payment> getPaymentByPageAndSort(int pageNumber, int pageSize, String field) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(field));
		return paymentRepository.findAll(pageable);
	}
}
