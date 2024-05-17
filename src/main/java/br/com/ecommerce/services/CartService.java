package br.com.ecommerce.services;

import br.com.ecommerce.dto.CartAddRequestDTO;
import br.com.ecommerce.dto.CartRemoveRequestDTO;
import br.com.ecommerce.dto.CartResponseDTO;
import br.com.ecommerce.models.Book;
import br.com.ecommerce.models.Cart;
import br.com.ecommerce.models.CartBook;
import br.com.ecommerce.models.User;
import br.com.ecommerce.repositories.CartBookRepository;
import br.com.ecommerce.repositories.CartRepository;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {

    public static final String CART_NOT_FOUND = "Cart not found";

    private final CartRepository cartRepository;
    private final CartBookRepository cartBookRepository;
    private final BookService bookService;
    private final UserService userService;

    public CartService(CartRepository cartRepository, CartBookRepository cartBookRepository, 
                       BookService bookService, UserService userService) {
        this.cartRepository = cartRepository;
        this.cartBookRepository = cartBookRepository;
        this.bookService = bookService;
        this.userService = userService;
    }

    public Cart createCart(User user) {
        var cart = new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);
    }

    public Optional<CartResponseDTO> addItem(CartAddRequestDTO request) {
        try {
            var book = bookService.getBookById(request.getBookId()).orElseThrow(() -> new IllegalArgumentException("Book not found"));
            var user = userService.getUserById(request.getUserId()).orElseThrow(() -> new IllegalArgumentException("User not found"));
            Cart cart;

            if (request.getCartId() == null) {
                cart = createCart(user);
            } else {
                cart = cartRepository.findById(request.getCartId()).orElse(createCart(user));
            }
            addItemToCart(cart, book, request.getQuantity());
            cart.setTotal(updateTotal(cart));
            return Optional.of(new CartResponseDTO(cartRepository.save(cart), cartBookRepository.findByCart(cart)));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    public Optional<CartResponseDTO> removeItem(Long cartId, CartRemoveRequestDTO request) {
        try {
            var book = bookService.getBookById(request.getBookId()).orElseThrow(() -> new IllegalArgumentException("Book not found"));
            var cart = cartRepository.findById(cartId).orElseThrow(() -> new IllegalArgumentException(CART_NOT_FOUND));
            removeItemFromCart(cart, book, request.getQuantity());
            cart.setTotal(updateTotal(cart));

            return Optional.of(new CartResponseDTO(cartRepository.save(cart), cartBookRepository.findByCart(cart)));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    public Optional<CartResponseDTO> updateQuantity(Long cartId, Book book, Long quantity) {
        try {
            var cart = cartRepository.findById(cartId).orElseThrow(() -> new IllegalArgumentException(CART_NOT_FOUND));
            cartBookRepository.findByCartAndBook(cart, book).ifPresent(cb -> {
                cb.setQuantity(quantity);
                cartBookRepository.save(cb);
            });
            cart.setTotal(updateTotal(cart));
            return Optional.of(new CartResponseDTO(cartRepository.save(cart), cartBookRepository.findByCart(cart)));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    @Transactional
    public Optional<CartResponseDTO> emptyCart(Long cartId) {
        try {
            var cart = cartRepository.findById(cartId).orElseThrow(() -> new IllegalArgumentException(CART_NOT_FOUND));
            cartBookRepository.deleteAllByCart(cart);
            cart.setTotal(updateTotal(cart));
            return Optional.of(new CartResponseDTO(cartRepository.save(cart), cartBookRepository.findByCart(cart)));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    public Optional<CartResponseDTO> getCartDTOById(Long id) {

        var cart = getCartById(id).orElse(new Cart(id));
        return Optional.of(new CartResponseDTO(cart, cartBookRepository.findByCart(cart)));
    }

    public Optional<Cart> getCartById(Long id) {
        try {
            var cart = cartRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(CART_NOT_FOUND));

            return Optional.of(cart);
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    public Iterable<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    private void addItemToCart(Cart cart, Book book, Long quantity) {
        var cartBook = new CartBook();
        cartBook.setBook(book);
        cartBook.setCart(cart);
        cartBook.setQuantity(quantity);
        
        cartBookRepository.save(cartBook);
    }

    private void removeItemFromCart(Cart cart, Book book, Long quantity) {

        cartBookRepository.findByCartAndBook(cart, book).ifPresent(cb -> {
            if (cb.getQuantity() > quantity) {
                cb.setQuantity(cb.getQuantity() - quantity);
                cartBookRepository.save(cb);
            } else {
                cartBookRepository.delete(cb);
            }
        });
    }

    private Double updateTotal(Cart cart) {

        return cartBookRepository.findByCart(cart)
                .stream().map(cb -> cb.getBook().getPrice() * cb.getQuantity()).reduce(0.0, Double::sum);
    }
}
