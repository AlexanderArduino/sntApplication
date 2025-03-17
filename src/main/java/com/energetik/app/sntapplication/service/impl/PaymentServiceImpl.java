package com.energetik.app.sntapplication.service.impl;

import com.energetik.app.sntapplication.entity.Payment;
import com.energetik.app.sntapplication.repository.PaymentRepository;
import com.energetik.app.sntapplication.service.PaymentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Payment> findPaymentById(Long id) {
        if(id == null) {
            throw new IllegalArgumentException("Id must not be null");
        }
        return paymentRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existPaymentById(Long id) {
        if(id == null) {
            throw new IllegalArgumentException("Id must not be null");
        }
        return paymentRepository.existsById(id);
    }

    @Override
    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public Payment updatePayment(Payment payment) {
        if(!paymentRepository.existsById(payment.getId())){
            throw new IllegalArgumentException("Payment with id:" + payment.getId() + "not exist");
        }
        Payment updatePayment = paymentRepository.findById(payment.getId())
                .orElseThrow(() -> new IllegalArgumentException("Платеж с ИД:" + payment.getId() + "не найден"));
        updatePayment.setSumma(payment.getSumma());
        updatePayment.setDateOfPayment(LocalDateTime.now());
        updatePayment.setPeriodFrom(payment.getPeriodFrom());
        updatePayment.setPeriodTo(payment.getPeriodTo());
        updatePayment.setTargetPay(payment.isTargetPay());
        updatePayment.setElectricityPay(payment.isElectricityPay());
        updatePayment.setCash(payment.isCash());
        updatePayment.setGardener(payment.getGardener());
        return paymentRepository.save(updatePayment);
    }

    @Override
    public void deletePaymentById(Payment payment) {
        if(payment == null || payment.getId() == null) {
            throw new IllegalArgumentException("Объект Payment не может быть пустым и должен иметь id");
        }
        Payment deletePayment = paymentRepository.findById(payment.getId())
                .orElseThrow(() -> new RuntimeException("Платеж с id:" + payment.getId() + " не найден"));
        paymentRepository.delete(deletePayment);
    }
}
