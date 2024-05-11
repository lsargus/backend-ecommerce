package br.com.ecommerce.services;

import br.com.ecommerce.models.Payment;
import br.com.ecommerce.repositories.PaymentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    public Payment processPayment(Payment payment) {
        payment.processPayment(payment.getAmount(), payment.getPaymentMethod());
        return paymentRepository.save(payment);
    }

    public Optional<Payment> refundPayment(Long id) {
        return paymentRepository.findById(id).map(payment -> {
            payment.refundPayment();
            return paymentRepository.save(payment);
        });
    }

    public Optional<Payment> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }

    public Iterable<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Optional<String> getPaymentStatus(Long id) {
        return paymentRepository.findById(id).map(Payment::getStatus);
    }
}