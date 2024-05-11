package br.com.ecommerce.models;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
@Table(name = "\"ORDER\"")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private List<Book> books;

    private Integer quantity;
    private Double totalPrice;
    private String status;

    public void placeOrder() {
        this.status = "Placed";
    }

    public void updateOrder() {
        this.status = "Updated";
    }

    public void cancelOrder() {
        this.status = "Cancelled";
    }
    
    public List<Book> getBooks() {
        return this.books;
    }
    
    

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getTotalPrice() {
        return this.totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }

    public void getOrderDetails() {
        // Implementação para obter os detalhes do pedido
    }

	
	
}