package br.com.ecommerce.controllers;

import br.com.ecommerce.models.Book;
import br.com.ecommerce.models.Cart;
import br.com.ecommerce.services.CartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/carts")
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("/{cartId}/items")
    public ResponseEntity<Cart> addItem(@PathVariable Long cartId, @RequestBody Book book) {
        Optional<Cart> cart = cartService.addItem(cartId, book);
        return cart.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{cartId}/items")
    public ResponseEntity<Cart> removeItem(@PathVariable Long cartId, @RequestBody Book book) {
        Optional<Cart> cart = cartService.removeItem(cartId, book);
        return cart.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{cartId}/items")
    public ResponseEntity<Cart> updateQuantity(@PathVariable Long cartId, @RequestBody Book book, @RequestParam int quantity) {
        Optional<Cart> cart = cartService.updateQuantity(cartId, book, quantity);
        return cart.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Cart> emptyCart(@PathVariable Long cartId) {
        Optional<Cart> cart = cartService.emptyCart(cartId);
        return cart.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cart> getCartById(@PathVariable Long id) {
        Optional<Cart> cart = cartService.getCartById(id);
        return cart.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<Iterable<Cart>> getAllCarts() {
        return new ResponseEntity<>(cartService.getAllCarts(), HttpStatus.OK);
    }
}