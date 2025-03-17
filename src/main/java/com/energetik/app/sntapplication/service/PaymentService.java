package com.energetik.app.sntapplication.service;


import com.energetik.app.sntapplication.entity.Payment;

import java.util.Optional;

public interface PaymentService {

    Optional<Payment> findPaymentById(Long id);

    boolean existPaymentById(Long id);

    Payment savePayment(Payment payment);

    Payment updatePayment(Payment payment);

    void deletePaymentById(Payment payment);
}
