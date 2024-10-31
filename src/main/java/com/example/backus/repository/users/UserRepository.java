package com.example.backus.repository.users;

import com.example.backus.models.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, String> {
    Optional<Users> findByUsernameAndPassword(String Username,String Password );

    Optional<Users> findByUsername(String username);

    Optional<Users> findByDocument(String document);

    List<Users> findByRoles_NameNot(String Roles);

}
