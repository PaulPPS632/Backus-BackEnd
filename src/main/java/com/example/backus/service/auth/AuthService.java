package com.example.backus.service.auth;

import com.example.backus.exception.ResourceNotFoundException;
import com.example.backus.models.dto.auth.AuthResponse;
import com.example.backus.models.dto.auth.LoginRequest;
import com.example.backus.models.dto.users.UserAuthDto;
import com.example.backus.models.entity.Roles;
import com.example.backus.models.entity.Users;
import com.example.backus.repository.users.RolRepository;
import com.example.backus.repository.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final RolRepository rolRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest loginRequest) {
        String username = loginRequest.username();
        String password = loginRequest.password();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
        Users user = userRepository.findByUsername(username).orElseThrow();
        UserDetails userdetails = user;
        String token = jwtService.getToken(userdetails);
        return AuthResponse.builder().token(token).rol(user.getRoles().getName()).username(user.getUsername()).build();
    }

    public AuthResponse register(UserAuthDto userAuthDto) {
        Optional<Roles> rol = rolRepository.findByName(userAuthDto.rol());
        if(rol.isEmpty()){
            throw new ResourceNotFoundException("Rol no encontrado: " + userAuthDto.rol());
        }
        Users user = Users.builder()
                .username(userAuthDto.username())
                .password(passwordEncoder.encode(userAuthDto.password()))
                .name(userAuthDto.name())
                .adress(userAuthDto.adress())
                .phone(userAuthDto.phone())
                .document(userAuthDto.document())
                .roles(rol.get())
                .build();
        userRepository.save(user);
        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .rol(userAuthDto.rol())
                .username(userAuthDto.username())
                .build();
    }
    public Map<String, Object> validate(String token) {
        Map<String, Object> response = jwtService.validate(token);
        Users usuario = userRepository.findByUsername((String) response.get("username")).orElseThrow();
        response.put("rol",usuario.getRoles().getName());
        response.put("usuarioId",usuario.getId());
        return response;
    }
}
