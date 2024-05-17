package br.com.ecommerce.domains;

import lombok.Data;

@Data
public class CartUpdateMessage {
    private Long productId;
    private int quantity;
}
