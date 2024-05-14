package br.com.ecommerce.services;

import br.com.ecommerce.dto.PaymentRequestDTO;
import br.com.ecommerce.models.Payment;
import br.com.ecommerce.repositories.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderService orderService;

    public PaymentService(PaymentRepository paymentRepository, OrderService orderService) {
        this.paymentRepository = paymentRepository;
        this.orderService = orderService;
    }

    public Optional<Payment> processPayment(PaymentRequestDTO request) {
        try {
            var order = orderService.getOrderById(request.getOrderId()).orElseThrow(() -> new IllegalArgumentException("Order not found"));
            var payment = new Payment();
            payment.setOrder(order);
            payment.processPayment(request.getAmount(), request.getPaymentMethod());

            return Optional.of(paymentRepository.save(payment));

        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
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