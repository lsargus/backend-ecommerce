package br.com.ecommerce.services;

import br.com.ecommerce.models.Book;
import br.com.ecommerce.models.Cart;
import br.com.ecommerce.repositories.CartRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    public Optional<Cart> addItem(Long cartId, Book book) {
        return cartRepository.findById(cartId).map(cart -> {
            cart.addItem(book);
            return cartRepository.save(cart);
        });
    }

    public Optional<Cart> removeItem(Long cartId, Book book) {
        return cartRepository.findById(cartId).map(cart -> {
            cart.removeItem(book);
            return cartRepository.save(cart);
        });
    }

    public Optional<Cart> updateQuantity(Long cartId, Book book, int quantity) {
        return cartRepository.findById(cartId).map(cart -> {
            cart.updateQuantity(book, quantity);
            return cartRepository.save(cart);
        });
    }

    public Optional<Cart> emptyCart(Long cartId) {
        return cartRepository.findById(cartId).map(cart -> {
            cart.emptyCart();
            return cartRepository.save(cart);
        });
    }

    public Optional<Cart> getCartById(Long id) {
        return cartRepository.findById(id);
    }

    public Iterable<Cart> getAllCarts() {
        return cartRepository.findAll();
    }
}
