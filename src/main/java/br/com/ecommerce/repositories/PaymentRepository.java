package br.com.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ecommerce.models.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}