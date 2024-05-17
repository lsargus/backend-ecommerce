package br.com.ecommerce.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private Double amount; // valor
    private String paymentMethod;
    private String status;

    public void processPayment(Double amount, String paymentMethod) {
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.status = "Processed";
    }

    public void refundPayment() {
        this.status = "Refunded";
    }
}