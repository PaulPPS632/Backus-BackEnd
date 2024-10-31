package com.example.backus.service.User;

import com.example.backus.models.dto.RolesRequest;
import com.example.backus.models.dto.RolesResponse;
import com.example.backus.models.entity.Roles;
import com.example.backus.repository.users.RolesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RolesService {
    private final RolesRepository rolesRepository;

    public ResponseEntity<List<RolesResponse>> getAll() {
        List<Roles> roles = rolesRepository.findAll();
        List<RolesResponse> rolesResponses = roles.stream()
                .map(this::mapToRolesResponse)
                .toList();
        return ResponseEntity.ok(rolesResponses);
    }

    public ResponseEntity<RolesResponse> getById(Long id) {
        Optional<Roles> role = rolesRepository.findById(id);
        return role.map(value -> ResponseEntity.ok(mapToRolesResponse(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<RolesResponse> create(RolesRequest rolesRequest) {
        Roles newRole = new Roles();
        newRole.setName(rolesRequest.name());
        newRole.setDescription(rolesRequest.description());

        Roles savedRole = rolesRepository.save(newRole);
        return ResponseEntity.ok(mapToRolesResponse(savedRole));
    }

    public ResponseEntity<RolesResponse> update(RolesRequest rolesRequest, Long id) {
        Optional<Roles> existingRole = rolesRepository.findById(id);
        if (existingRole.isPresent()) {
            Roles roleToUpdate = existingRole.get();
            roleToUpdate.setName(rolesRequest.name());
            roleToUpdate.setDescription(rolesRequest.description());

            Roles updatedRole = rolesRepository.save(roleToUpdate);
            return ResponseEntity.ok(mapToRolesResponse(updatedRole));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public void delete(Long id) {
        rolesRepository.deleteById(id);
    }
    private RolesResponse mapToRolesResponse(Roles rol){
        return new RolesResponse(rol.getId(), rol.getName(), rol.getDescription());
    }
}
