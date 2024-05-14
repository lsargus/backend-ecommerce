package br.com.ecommerce.services;

import br.com.ecommerce.models.Book;
import br.com.ecommerce.models.CartBook;
import br.com.ecommerce.models.Order;
import br.com.ecommerce.repositories.CartBookRepository;
import br.com.ecommerce.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final CartBookRepository cartBookRepository;

    public OrderService(OrderRepository orderRepository, CartBookRepository cartBookRepository, CartService cartService) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
        this.cartBookRepository = cartBookRepository;
    }

    public Optional<Order> placeOrder(Long cartId) {
        try {
            var cart = cartService.getCartById(cartId).orElseThrow(() -> new IllegalArgumentException("Cart not found"));
            var books = cartBookRepository.findByCart(cart);
            var total = books.stream().map(cb -> cb.getBook().getPrice() * cb.getQuantity()).reduce(0.0, Double::sum);
            var quantity = books.stream().map(CartBook::getQuantity).reduce(0L, Long::sum);

            var order = new Order();
            order.setTotalPrice(total);
            order.setQuantity(quantity);
            order.setCart(cart);
            order.placeOrder();
            return Optional.of(orderRepository.save(order));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    public Optional<Order> updateOrder(Long id) {
        return orderRepository.findById(id).map(order -> {
            order.updateOrder();
            return orderRepository.save(order);
        });
    }

    public boolean cancelOrder(Long id) {
        return orderRepository.findById(id).map(order -> {
            order.cancelOrder();
            orderRepository.save(order);
            return true;
        }).orElse(false);
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public List<Book> getBooksByOrderId(Long id) {
        try {
            var order = orderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Order not found"));
            var cart = cartService.getCartById(order.getCart().getId()).orElseThrow(() -> new IllegalArgumentException("Cart not found"));
            return cartBookRepository.findByCart(cart).stream().map(CartBook::getBook).toList();
        } catch (IllegalArgumentException e) {
            return Collections.emptyList();
        }
    }

    public Iterable<Order> getAllOrders() {
        return orderRepository.findAll();
    }

}