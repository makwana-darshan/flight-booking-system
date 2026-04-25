package com.flight.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flight.dto.PaymentStatus;
import com.flight.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
	List<Payment> findByStatus(PaymentStatus status);

	List<Payment> findByMode(String mode);

	List<Payment> findByAmountGreaterThan(Double amount);

	
}
