package br.com.ecommerce.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

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

    @OneToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    private Long quantity;
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

    public void getOrderDetails() {
        // Implementação para obter os detalhes do pedido
    }

	
	
}