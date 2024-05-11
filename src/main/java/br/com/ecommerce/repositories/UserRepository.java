package br.com.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ecommerce.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Método para encontrar um usuário pelo email
    User findByEmail(String email);
}