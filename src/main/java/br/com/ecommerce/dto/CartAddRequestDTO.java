package br.com.ecommerce.dto;

import lombok.Data;

@Data
public class CartAddRequestDTO {
      private Long cartId;
      private Long userId;
      private Long bookId;
      private Long quantity;
}
