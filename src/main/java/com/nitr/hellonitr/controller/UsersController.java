package com.nitr.hellonitr.controller;

import com.nitr.hellonitr.dtos.UsersDTO;
import com.nitr.hellonitr.exception.ResourceNotFoundException;
import com.nitr.hellonitr.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        return ResponseEntity.ok(usersService.searchByName(query, page, size));
    }

    //todo: Add more search methods as needed

    @PostMapping("/{id}/profile-picture")
    public ResponseEntity<String> uploadProfilePicture(
            @PathVariable String id,
            @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }
        try {
            String fileName = usersService.storeProfilePicture(id, file);
            return ResponseEntity.ok("Profile picture uploaded successfully: " + fileName);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Could not upload profile picture: " + e.getMessage());
        }
    }

    //Get profile picture by user id
    @GetMapping("/{id}/profile-picture")
    public ResponseEntity<byte[]> getProfilePicture(@PathVariable String id) {
        try {
            byte[] imageBytes = usersService.getProfilePicture(id);
            if (imageBytes == null || imageBytes.length == 0) {
                return ResponseEntity.notFound().build();
            }
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);//or MediaType.IMAGE_PNG
            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    //Delete profile picture by user id
    @DeleteMapping("/{id}/profile-picture")
    public ResponseEntity<Void> deleteProfilePicture(
            @PathVariable String id) {
        usersService.deleteProfilePicture(id);
        return ResponseEntity.ok().build();
    }


    // Exception handling

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
