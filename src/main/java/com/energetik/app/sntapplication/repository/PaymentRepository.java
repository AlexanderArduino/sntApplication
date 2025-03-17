package com.energetik.app.sntapplication.repository;


import com.energetik.app.sntapplication.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
