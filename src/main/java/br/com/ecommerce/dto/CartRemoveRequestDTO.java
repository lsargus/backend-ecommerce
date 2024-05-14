package br.com.ecommerce.dto;

import lombok.Data;

@Data
public class CartRemoveRequestDTO {
    private Long bookId;
    private Long quantity;
}
