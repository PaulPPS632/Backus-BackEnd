package com.example.backus.controllers;

import com.example.backus.models.dto.UsersRequest;
import com.example.backus.models.dto.UsersResponse;
import com.example.backus.services.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("Users")
@RequiredArgsConstructor
public class UsersController {
    private final UsersService usersService;

    @GetMapping
    public ResponseEntity<List<UsersResponse>> GetAll(){
        return usersService.getAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<UsersResponse> GetById(@PathVariable("id") Long id){
        return usersService.getById(id);
    }
    @PostMapping("/login")
    public ResponseEntity<Object> Login(@RequestBody Map<String, String> credentials){
        String email = credentials.get("email");
        String password = credentials.get("password");
        return usersService.login(email, password);
    }
    @PostMapping("/validate")
    public ResponseEntity<Object> Validate(@RequestBody Map<String, String> credentials){
        String token = credentials.get("token");
        String email = credentials.get("email");
        return usersService.Validate(token, email);
    }
    @PostMapping
    public ResponseEntity<UsersResponse> Post(@RequestBody UsersRequest usersRequest){
        return usersService.create(usersRequest);
    }

    @PutMapping()
    public ResponseEntity<UsersResponse> Update(@RequestBody UsersRequest usersRequest){
        return usersService.update(usersRequest);
    }
    @DeleteMapping("/{id}")
    public void Delete(@PathVariable("id") Long id){
        usersService.delete(id);
    }
}
