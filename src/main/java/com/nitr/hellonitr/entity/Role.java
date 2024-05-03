package com.nitr.hellonitr.entity;

import com.nitr.hellonitr.enums.RoleNameEnum;
import jakarta.persistence.*;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Entity
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "role_name")
    private RoleNameEnum roleNameEnum;

}
