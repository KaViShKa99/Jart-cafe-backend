package com.example.jart_cafe.repositories;

import com.example.jart_cafe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findFirstByEmail(String email);
}
