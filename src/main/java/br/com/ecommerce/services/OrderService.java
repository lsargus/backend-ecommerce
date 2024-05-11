package br.com.ecommerce.services;

import br.com.ecommerce.models.Book;
import br.com.ecommerce.models.Order;
import br.com.ecommerce.repositories.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public Order placeOrder(Order order) {
        order.setTotalPrice(calculateTotalPrice(order.getBooks()));
        order.setQuantity(order.getBooks().size());
        order.placeOrder();
        return orderRepository.save(order);
    }

    public Optional<Order> updateOrder(Long id, Order updatedOrder) {
        return orderRepository.findById(id).map(order -> {
            order.setBooks(updatedOrder.getBooks());
            order.setQuantity(updatedOrder.getBooks().size());
            order.setTotalPrice(calculateTotalPrice(updatedOrder.getBooks()));
            order.updateOrder();
            order.setStatus(updatedOrder.getStatus());
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
        return orderRepository.findById(id).map(Order::getBooks).orElse(null);
    }

    public Iterable<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    private Double calculateTotalPrice(List<Book> books) {
        return books.stream()
                .mapToDouble(Book::getPrice)
                .sum();
    }
}