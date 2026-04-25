package com.flight.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.flight.dao.PaymentDao;
import com.flight.dto.PaymentStatus;
import com.flight.dto.ResponseStructure;
import com.flight.entity.Payment;
import com.flight.exception.IdNotFoundException;
import com.flight.exception.NoRecordAvailableException;

@Service
public class PaymentService {

	@Autowired
	private PaymentDao paymentDao;

	// Add a single payment
	public ResponseEntity<ResponseStructure<Payment>> savePayment(Payment payment) {
		ResponseStructure<Payment> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.CREATED.value());
		response.setMessage("Payment has been added successfully.");
		response.setData(paymentDao.savePayment(payment));
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	// Get all payments
	public ResponseEntity<ResponseStructure<List<Payment>>> getAllPayments() {
		List<Payment> payments = paymentDao.getAllPayments();

		if (payments.isEmpty()) {
			throw new NoRecordAvailableException("No payment records available.");
		}

		ResponseStructure<List<Payment>> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("All payment records retrieved successfully.");
		response.setData(payments);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Get payment by ID
	public ResponseEntity<ResponseStructure<Payment>> getPaymentById(Integer id) {
		Optional<Payment> opt = paymentDao.getPaymentById(id);

		if (opt.isEmpty()) {
			throw new IdNotFoundException("Payment not found for ID: " + id);
		}

		ResponseStructure<Payment> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Payment record retrieved successfully for ID: " + id + ".");
		response.setData(opt.get());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Get payments by status
	public ResponseEntity<ResponseStructure<List<Payment>>> getPaymentByStatus(String status) {
		List<Payment> payments = paymentDao.getPaymentByStatus(PaymentStatus.valueOf(status.toUpperCase()));

		if (payments.isEmpty()) {
			throw new NoRecordAvailableException("No payments found with status: " + status + ".");
		}

		ResponseStructure<List<Payment>> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Payments retrieved successfully with status: " + status + ".");
		response.setData(payments);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Get payments by mode
	public ResponseEntity<ResponseStructure<List<Payment>>> getPaymentByMode(String mode) {
		List<Payment> payments = paymentDao.getPaymentByMode(mode);

		if (payments.isEmpty()) {
			throw new NoRecordAvailableException("No payments found for mode: " + mode + ".");
		}

		ResponseStructure<List<Payment>> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Payments retrieved successfully with mode: " + mode + ".");
		response.setData(payments);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Get payments greater than amount
	public ResponseEntity<ResponseStructure<List<Payment>>> getPaymentAmountGreaterThan(Double amount) {
		List<Payment> payments = paymentDao.getPaymentAmountGreaterThan(amount);

		if (payments.isEmpty()) {
			throw new NoRecordAvailableException("No payments found with amount greater than: " + amount + ".");
		}

		ResponseStructure<List<Payment>> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Payments retrieved successfully with amount greater than: " + amount + ".");
		response.setData(payments);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Update payment status
	public ResponseEntity<ResponseStructure<Payment>> updatePaymentStatus(Integer id, String status) {
		Optional<Payment> optional = paymentDao.getPaymentById(id);

		if (optional.isEmpty()) {
			throw new IdNotFoundException("Payment not found for ID: " + id);
		}

		Payment payment = optional.get();
		payment.setStatus(Enum.valueOf(PaymentStatus.class, status.toUpperCase()));

		Payment updatedPayment = paymentDao.savePayment(payment);

		ResponseStructure<Payment> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Payment status updated successfully for ID: " + id + ".");
		response.setData(updatedPayment);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Get payments with pagination and sorting
	public ResponseEntity<ResponseStructure<Page<Payment>>> getPaymentByPageAndSort(int pageNumber, int pageSize,
			String field) {
		Page<Payment> payments = paymentDao.getPaymentByPageAndSort(pageNumber, pageSize, field);

		if (payments.isEmpty()) {
			throw new NoRecordAvailableException("No payment records found.");
		}

		ResponseStructure<Page<Payment>> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Payments retrieved successfully (page: " + pageNumber + ", size: " + pageSize
				+ ", sorted by: " + field + ").");
		response.setData(payments);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
