package com.nitr.hellonitr.dtos;

import com.nitr.hellonitr.enums.RoleNameEnum;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class UsersDTO {
    private UUID id;
    private String name;
    private String designation;
    private String department;
    private String mobile;
    private String password;
    private String work;
    private String residence;
    private String email;
    private Set<RoleNameEnum> roles;
    private String profilePicture;

}
