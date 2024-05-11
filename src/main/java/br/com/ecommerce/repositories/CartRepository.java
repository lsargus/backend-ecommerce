package br.com.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ecommerce.models.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
}
