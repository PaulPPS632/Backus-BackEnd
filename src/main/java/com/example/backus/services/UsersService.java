package com.example.backus.services;

import com.example.backus.models.dto.RolesResponse;
import com.example.backus.models.dto.UsersRequest;
import com.example.backus.models.dto.UsersResponse;
import com.example.backus.models.entity.Roles;
import com.example.backus.models.entity.Users;
import com.example.backus.repositorys.RolesRepository;
import com.example.backus.repositorys.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public void delete(Long id) {
        usersRepository.deleteById(id);
    }

    private UsersResponse mapToUserResponse(Users user){
        return new UsersResponse(user.getId(), user.getName(), user.getAdress(), user.getPhone(), user.getDocument(), user.getEmail(), mapToRolesResponse(user.getRoles()));
    }
    private RolesResponse mapToRolesResponse(Roles rol){
        return new RolesResponse(rol.getId(), rol.getName(), rol.getDescription());
    }

}
