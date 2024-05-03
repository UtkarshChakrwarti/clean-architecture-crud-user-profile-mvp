package com.nitr.hellonitr.entity;

import com.nitr.hellonitr.enums.DepartmentEnum;
import com.nitr.hellonitr.enums.DesignationEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;
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
    private DesignationEnum designation;


    @Column(name = "department")
    private DepartmentEnum department;


    @Column(name = "mobile")
    private String mobile;


    @Column(name = "work")
    private String work;


    @Column(name = "residence")
    private String residence;


    @Email
    @Column(name = "email")
    private String email;

    @Lob
    @Column(name = "profile_picture")
    private byte[] profilePicture;

    @ManyToMany
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;
}
