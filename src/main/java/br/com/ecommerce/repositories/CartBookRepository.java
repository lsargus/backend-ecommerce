package br.com.ecommerce.repositories;

import br.com.ecommerce.models.Book;
import br.com.ecommerce.models.Cart;
import br.com.ecommerce.models.CartBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartBookRepository extends JpaRepository<CartBook, Long>  {
    Optional<CartBook> findByCartAndBook(Cart cart, Book book);
    List<CartBook> findByCart(Cart cart);
    void deleteAllByCart(Cart cart);
}
