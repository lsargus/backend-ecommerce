package br.com.ecommerce.domains;

import lombok.Data;

@Data
public class CartStatus {
    private Long productId;
    private String status;
    private String message;

    public CartStatus(Long productId, String status, String message) {
        this.productId = productId;
        this.status = status;
        this.message = message;
    }
}