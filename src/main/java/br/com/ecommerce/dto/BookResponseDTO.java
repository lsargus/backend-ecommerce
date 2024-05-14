package br.com.ecommerce.dto;

import br.com.ecommerce.models.CartBook;
import lombok.Data;

import java.util.List;

@Data
public class BookResponseDTO {
    private Long bookId;
    private String title;
    private String author;
    private Double price;
    private Long quantity;

    public BookResponseDTO(CartBook cartBook) {
        this.bookId = cartBook.getBook().getId();
        this.title = cartBook.getBook().getTitle();
        this.author = cartBook.getBook().getAuthor();
        this.price = cartBook.getBook().getPrice();
        this.quantity = cartBook.getQuantity();
    }

    public static List<BookResponseDTO> convertList(List<CartBook> books) {
        return books.stream().map(BookResponseDTO::new).toList();
    }
}
