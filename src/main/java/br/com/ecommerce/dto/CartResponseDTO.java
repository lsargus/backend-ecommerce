package br.com.ecommerce.dto;

import br.com.ecommerce.models.Cart;
import br.com.ecommerce.models.CartBook;
import lombok.Data;

import java.util.List;

@Data
public class CartResponseDTO {

    private Long cartId;
    private String userName;
    private String userMail;
    private Double total;
    private List<BookResponseDTO> books;

    public CartResponseDTO(Cart cart, List<CartBook> books) {
        this.cartId = cart.getId();
        this.userName = cart.getUser().getName();
        this.userMail = cart.getUser().getEmail();
        this.total = cart.getTotal();
        this.books = BookResponseDTO.convertList(books);
    }
}
