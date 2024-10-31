package com.example.backus.controllers;

import com.example.backus.models.dto.RolesRequest;
import com.example.backus.models.dto.RolesResponse;
import com.example.backus.service.User.RolesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("Roles")
@RequiredArgsConstructor
public class RolesController {
    private final RolesService rolesService;
    @GetMapping
    public ResponseEntity<List<RolesResponse>> GetAll(){
        return rolesService.getAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<RolesResponse> GetById(@PathVariable Long id){
        return rolesService.getById(id);
    }
    @PostMapping
    public ResponseEntity<RolesResponse> Post(@RequestBody RolesRequest rolesRequest){
        return rolesService.create(rolesRequest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RolesResponse> Update(@RequestBody RolesRequest rolesRequest,@PathVariable("id") Long id){
        return rolesService.update(rolesRequest,id);
    }
    @DeleteMapping("/{id}")
    public void Delete(@PathVariable("id") Long id){
        rolesService.delete(id);
    }
}
