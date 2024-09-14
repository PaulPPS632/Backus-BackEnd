package com.example.backus.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    //Usuario, PasswordHash, Tipo (1=Empresa, 2=Persona Juridica, 3=Persona Natural), Nombre Completo, Direccion, Telefono, Documento de Identidad (RUC o DNI)
    String name;
    String adress;
    String phone;
    String document;
    String email;
    String password;


    @ManyToOne
    @JoinColumn(name = "rolesId")
    private Roles roles;
}
