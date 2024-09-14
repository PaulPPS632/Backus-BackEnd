package com.example.backus.services;

import com.example.backus.models.dto.RolesResponse;
import com.example.backus.models.dto.UsersRequest;
import com.example.backus.models.dto.UsersResponse;
import com.example.backus.models.entity.Roles;
import com.example.backus.models.entity.Users;
import com.example.backus.repositorys.RolesRepository;
import com.example.backus.repositorys.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository usersRepository;
    private final RolesRepository rolesRepository;
    public ResponseEntity<List<UsersResponse>> getAll() {
        List<Users> users = usersRepository.findAll();
        return ResponseEntity.ok(users.stream().map(this::mapToUserResponse).toList());
    }

    public ResponseEntity<UsersResponse> getById(Long id) {
        Optional<Users> user = usersRepository.findById(id);
        return user.map(value -> ResponseEntity.ok(mapToUserResponse(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<UsersResponse> create(UsersRequest usersRequest) {
        Users newUser = new Users();
        newUser.setName(usersRequest.name());
        newUser.setAdress(usersRequest.adress());
        newUser.setPhone(usersRequest.phone());
        newUser.setDocument(usersRequest.document());
        newUser.setEmail(usersRequest.email());
        Optional<Roles> rol = rolesRepository.findById(usersRequest.RolId());
        String hashedPassword = Encrypt.hashPassword(usersRequest.password());
        newUser.setPassword(hashedPassword);
        newUser.setRoles(rol.orElseThrow());

        Users savedUser = usersRepository.save(newUser);
        return ResponseEntity.ok(mapToUserResponse(savedUser));
    }

    public ResponseEntity<UsersResponse> update(UsersRequest usersRequest) {
        Optional<Users> existingUser = usersRepository.findById(usersRequest.id());
        if (existingUser.isPresent()) {
            Users userToUpdate = existingUser.get();
            userToUpdate.setName(usersRequest.name());
            userToUpdate.setAdress(usersRequest.adress());
            userToUpdate.setPhone(usersRequest.phone());
            userToUpdate.setDocument(usersRequest.document());
            userToUpdate.setEmail(usersRequest.email());
            Optional<Roles> rol = rolesRepository.findById(usersRequest.RolId());
            userToUpdate.setRoles(rol.orElseThrow());

            Users updatedUser = usersRepository.save(userToUpdate);
            return ResponseEntity.ok(mapToUserResponse(updatedUser));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    public ResponseEntity<Object> login(String email, String password) {
        Optional<Users> optionalUser = usersRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Usuario no encontrado");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }

        Users user = optionalUser.get();

        // Hashear la contraseña ingresada
        String hashedInputPassword = Encrypt.hashPassword(password);

        // Comparar el hash de la contraseña ingresada con el hash almacenado
        if (!hashedInputPassword.equals(user.getPassword())) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Contrasena incorrecta");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }

        // Login exitoso
        return ResponseEntity.ok(mapToUserResponse(user));
    }
    public ResponseEntity<Object> Validate(String token, String email) {
        Optional<Users> optionalUsers = usersRepository.findByPasswordAndEmail(token, email);
        if (optionalUsers.isEmpty()) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("status", "UNAUTHORIZED");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }else {
            Map<String, String> response = new HashMap<>();
            if(optionalUsers.get().getRoles().getId() != 1){
                response.put("status", "AUTHORIZED");
            }else{
                response.put("status", "UNAUTHORIZED");
            }
            return ResponseEntity.ok(response);
        }
    }
    public void delete(Long id) {
        usersRepository.deleteById(id);
    }

    private UsersResponse mapToUserResponse(Users user){
        return new UsersResponse(user.getId(), user.getName(), user.getAdress(), user.getPhone(), user.getDocument(), user.getEmail(), user.getPassword(),mapToRolesResponse(user.getRoles()));
    }
    private RolesResponse mapToRolesResponse(Roles rol){
        return new RolesResponse(rol.getId(), rol.getName(), rol.getDescription());
    }

}
