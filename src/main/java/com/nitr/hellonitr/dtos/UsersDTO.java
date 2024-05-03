package com.nitr.hellonitr.dtos;

import com.nitr.hellonitr.entity.Role;
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
    private String work;
    private String residence;
    private String email;
    private Set<Role> roles;
    private String profilePicture;

}
