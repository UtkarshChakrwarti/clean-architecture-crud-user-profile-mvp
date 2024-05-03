package com.nitr.hellonitr.controller;

import com.nitr.hellonitr.dtos.UsersDTO;
import com.nitr.hellonitr.exception.ResourceNotFoundException;
import com.nitr.hellonitr.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @GetMapping("/{id}")
    public ResponseEntity<UsersDTO> getUserById(@PathVariable String id) {
        UsersDTO user = usersService.getUserById(id);
        if (user == null) {
            throw new ResourceNotFoundException("User not found with id " + id);
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<Page<UsersDTO>> getAllUsers(Pageable pageable) {
        return ResponseEntity.ok(usersService.getAllUsers(pageable));
    }

    @PostMapping("/create")
    public ResponseEntity<UsersDTO> createUser(@RequestBody UsersDTO userDto) {
        return ResponseEntity.ok(usersService.createUser(userDto));
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<UsersDTO> updateUser(@PathVariable String id, @RequestBody UsersDTO userDto) {
        UsersDTO updatedUser = usersService.updateUser(id, userDto);
        if (updatedUser == null) {
            throw new ResourceNotFoundException("User not found with id " + id);
        }
        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping("delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        usersService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search/name/{name}")
    public ResponseEntity<List<UsersDTO>> searchByName(@PathVariable String name) {
        return ResponseEntity.ok(usersService.searchByName(name));
    }

    @GetMapping("/search/department/{department}")
    public ResponseEntity<List<UsersDTO>> searchByDepartment(@PathVariable String department) {
        return ResponseEntity.ok(usersService.searchByDepartment(department));
    }

    @GetMapping("/search/designation/{designation}")
    public ResponseEntity<List<UsersDTO>> searchByDesignation(@PathVariable String designation) {
        return ResponseEntity.ok(usersService.searchByDesignation(designation));
    }

}
