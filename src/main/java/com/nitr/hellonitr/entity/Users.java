package com.nitr.hellonitr.entity;

import com.nitr.hellonitr.enums.DepartmentEnum;
import com.nitr.hellonitr.enums.DesignationEnum;
import com.nitr.hellonitr.enums.RoleNameEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @NotNull
    @Column(name = "name")
    private String name;

    private String password;

    @Column(name = "designation")
    @Enumerated(EnumType.STRING)
    private DesignationEnum designation;


    @Column(name = "department")
    @Enumerated(EnumType.STRING)
    private DepartmentEnum department;

    @Column(name = "mobile", unique = true)
    private String mobile;

    @Column(name = "work", unique = true)
    private String work;


    @Column(name = "residence")
    private String residence;

    @Column(name = "email", unique = true)
    private String email;

    @Lob
    @Column(name = "profile_picture")
    private byte[] profilePicture;

    @Column(name = "roles")
    @Enumerated(EnumType.STRING)
    private Set<RoleNameEnum> roles;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;
}
