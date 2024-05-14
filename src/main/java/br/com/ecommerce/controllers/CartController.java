package br.com.ecommerce.controllers;

import br.com.ecommerce.dto.CartAddRequestDTO;
import br.com.ecommerce.dto.CartRemoveRequestDTO;
import br.com.ecommerce.dto.CartResponseDTO;
import br.com.ecommerce.models.Book;
import br.com.ecommerce.models.Cart;
import br.com.ecommerce.services.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/items")
    public ResponseEntity<CartResponseDTO> addItem(@RequestBody CartAddRequestDTO request) {
        Optional<CartResponseDTO> cart = cartService.addItem(request);
        return cart.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{cartId}/items")
    public ResponseEntity<CartResponseDTO> removeItem(@PathVariable Long cartId, @RequestBody CartRemoveRequestDTO request) {
        Optional<CartResponseDTO> cart = cartService.removeItem(cartId, request);
        return cart.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{cartId}/items")
    public ResponseEntity<CartResponseDTO> updateQuantity(@PathVariable Long cartId, @RequestBody Book book, @RequestParam Long quantity) {
        Optional<CartResponseDTO> cart = cartService.updateQuantity(cartId, book, quantity);
        return cart.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<CartResponseDTO> emptyCart(@PathVariable Long cartId) {
        Optional<CartResponseDTO> cart = cartService.emptyCart(cartId);
        return cart.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartResponseDTO> getCartById(@PathVariable Long id) {
        Optional<CartResponseDTO> cart = cartService.getCartDTOById(id);
        return cart.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<Iterable<Cart>> getAllCarts() {
        return new ResponseEntity<>(cartService.getAllCarts(), HttpStatus.OK);
    }
}