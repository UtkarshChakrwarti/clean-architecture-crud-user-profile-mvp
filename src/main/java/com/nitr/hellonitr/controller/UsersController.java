package com.nitr.hellonitr.controller;

import com.nitr.hellonitr.dtos.UsersDTO;
import com.nitr.hellonitr.exception.ResourceNotFoundException;
import com.nitr.hellonitr.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    // Get user by UUID: /api/users/{id}
    @GetMapping("/{id}")
    public ResponseEntity<UsersDTO> getUser(@PathVariable String id) {
        UsersDTO user = usersService.getUserById(id);
        if (user == null) {
            throw new ResourceNotFoundException("User not found with id " + id);
        }
        return ResponseEntity.ok(user);
    }

    // Get all users with pagination: /api/users?page=0&size=50
    @GetMapping
    public ResponseEntity<Page<UsersDTO>> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        return ResponseEntity.ok(usersService.getAllUsers(page, size));
    }

    // Create a new user: /api/users
    @PostMapping
    public ResponseEntity<UsersDTO> createUser(@RequestBody UsersDTO userDto) {
        return ResponseEntity.ok(usersService.createUser(userDto));
    }

    // Update an existing user: /api/users/{id}
    @PutMapping("/{id}")
    public ResponseEntity<UsersDTO> updateUser(
            @PathVariable String id,
            @RequestBody UsersDTO userDto) {
        UsersDTO updatedUser = usersService.updateUser(id, userDto);
        if (updatedUser == null) {
            throw new ResourceNotFoundException("User not found with id " + id);
        }
        return ResponseEntity.ok(updatedUser);
    }

    // Delete a user: /api/users/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        usersService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    // Search users by name: /api/users/search?name=value&page=0&size=50
    @GetMapping("/search")
    public ResponseEntity<Page<UsersDTO>> searchByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        return ResponseEntity.ok(usersService.searchByName(name, page, size));
    }

    // Advanced searches could still use their specific paths for clarity
    @GetMapping("/search/department/{department}")
    public ResponseEntity<Page<UsersDTO>> searchByDepartment(
            @PathVariable String department,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        return ResponseEntity.ok(usersService.searchByDepartment(department, page, size));
    }

    @GetMapping("/search/designation/{designation}")
    public ResponseEntity<Page<UsersDTO>> searchByDesignation(
            @PathVariable String designation,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        return ResponseEntity.ok(usersService.searchByDesignation(designation, page, size));
    }

    //todo: Add more search methods as needed

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //Generic exception handler to catch all other exceptions
    //todo: Use Custom Exception classes for more specific error handling
}
