package com.example.backus.service.User;

import com.example.backus.models.dto.users.*;
import com.example.backus.models.entity.Roles;
import com.example.backus.models.entity.Users;
import com.example.backus.repository.users.RolRepository;
import com.example.backus.repository.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JdbcTemplate jdbcTemplate;
    private final RolRepository rolRepository;
    //@Transactional
    public UserResponse Regist(UserAuthDto usuario) {
        try {
            Optional<Users> optionalUser = userRepository.findByUsername(usuario.username());
            if(optionalUser.isPresent()) {
                optionalUser.get();
                return maptoUserResponse(optionalUser.get());
            }else{
                return maptoUserResponse(crear(usuario));
            }
        }catch (Exception e) {
            // Manejar la excepción adecuadamente
            throw new RuntimeException("Error al registrar usuario", e);
        }

    }
    private Users crear(UserAuthDto usuario){
        Users user = new Users();
        Roles rol = rolRepository.findById(1L).orElseThrow(() -> new RuntimeException("Rol not found"));

        user.setUsername(usuario.username());
        user.setPassword(usuario.password());
        user.setRoles(rol);

        Users guardado = userRepository.save(user);
        //logicaNegocioUserRepository.save(LogicaNegocioUser.builder().usuario(guardado.getId()).metaventas(8000D).build());
        return guardado;
    }
    private UserAuthDto maptoUserAuthDTO(Users user){
        return UserAuthDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .name(user.getName())
                .adress(user.getAdress())
                .phone(user.getPhone())
                .document(user.getDocument())
                .build();
    }
    public UserResponse Login(String email, String password) {
        return maptoUserResponse(userRepository.findByUsernameAndPassword(email,password).orElseThrow());
    }

    public List<UserResponse> getAll() {
        List<Users> users = userRepository.findAll();
        return users.stream().map(this::maptoUserResponse).toList();
    }
    private UserResponse maptoUserResponse(Users user){
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .adress(user.getAdress())
                .phone(user.getPhone())
                .document(user.getDocument())
                .rol(maptoRolResponse(user.getRoles()))
                .build();
    }
    private RolResponse maptoRolResponse(Roles rol){
        if(rol != null){
            return RolResponse.builder()
                    .id(rol.getId())
                    .nombre(rol.getName())
                    .descripcion(rol.getDescription())
                    .build();
        }
        return RolResponse.builder().build();
    }
    public List<RolResponse> getAllRoles() {
        return rolRepository.findAll().stream().map(this::maptoRolResponse).toList();
    }
    public void AsignarRol(UserRequest usuario) {
        Optional<Users> user = userRepository.findById(usuario.id());
        user.orElseThrow().setRoles(rolRepository.findByName(usuario.rol()).orElseThrow());
        userRepository.save(user.get());
    }
    private Users maptoUser(UserResponse usuarioResponse){
        Optional<Roles> rol = rolRepository.findById(usuarioResponse.rol().id());
        return Users.builder()
                .id(usuarioResponse.id())
                .username(usuarioResponse.username())
                .name(usuarioResponse.name())
                .adress(usuarioResponse.adress())
                .phone(usuarioResponse.phone())
                .document(usuarioResponse.document())
                .roles(rol.orElseThrow())
                .build();
    }

    public void delete(String id) {
        userRepository.deleteById(id);
    }


    public ResponseEntity<UserResponse> getUserByUsername(String username) {
        if(!username.isEmpty()){
            return ResponseEntity.ok(maptoUserResponse(userRepository.findByUsername(username).orElseThrow()));
        }else {
            return ResponseEntity.status(500).body(UserResponse.builder().build());
        }
    }
    public Users getUserByUserID(String id) {
        if(!id.isEmpty()){
            return userRepository.findById(id).orElseThrow();
        }else {
            return Users.builder().build();
        }
    }
    public ResponseEntity<List<UserResponse>> getUserDashboard() {
        return ResponseEntity.ok(userRepository.findByRoles_NameNot("cliente").stream().map(this::maptoUserResponse).toList());
    }
    public void UserRegisterJson(String documento, String nombre, String direccion, String telefono,String username, String tipoEntidad){
        Optional<Users> userOptional = userRepository.findByDocument(documento);
        if(userOptional.isEmpty()){
            Users nuevo = new Users();
            nuevo.setDocument(documento);
            nuevo.setName(nombre);
            nuevo.setAdress(direccion);
            nuevo.setPhone(telefono);
            nuevo.setUsername(username);
            userRepository.save(nuevo);
        }
    }
}
