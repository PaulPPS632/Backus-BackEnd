package com.example.backus.repositorys;

import com.example.backus.models.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);
    Optional<Users> findByPasswordAndEmail(String password, String email);
}
