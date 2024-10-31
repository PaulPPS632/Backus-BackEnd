package com.example.backus.repository.users;

import com.example.backus.models.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Roles, Long> {
    Optional<Roles> findByName(String name);
}
