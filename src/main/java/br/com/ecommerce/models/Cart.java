package br.com.ecommerce.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

import java.util.ArrayList;

@Entity
@Data
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "cart_id")
    private List<Book> items = new ArrayList<>();

    private Double total = 0.0;

    public void addItem(Book book) {
        items.add(book);
        updateTotal();
    }

    public void removeItem(Book book) {
        items.remove(book);
        updateTotal();
    }

    public void updateQuantity(Book book, int quantity) {
        int index = items.indexOf(book);
        if (index >= 0) {
            Book existingBook = items.get(index);
            existingBook.setStock(quantity);
            updateTotal();
        }
    }

    public void emptyCart() {
        items.clear();
        updateTotal();
    }

    public List<Book> getItems() {
        return items;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    private void updateTotal() {
        this.total = items.stream()
                .mapToDouble(Book::getPrice)
                .sum();
    }
}