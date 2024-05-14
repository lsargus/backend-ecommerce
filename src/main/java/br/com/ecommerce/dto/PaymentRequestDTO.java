package br.com.ecommerce.dto;

import lombok.Data;

@Data
public class PaymentRequestDTO {
    private Long orderId;
    private Double amount;
    private String paymentMethod;
}
