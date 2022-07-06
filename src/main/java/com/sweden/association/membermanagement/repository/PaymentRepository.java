package com.sweden.association.membermanagement.repository;

import com.sweden.association.membermanagement.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
