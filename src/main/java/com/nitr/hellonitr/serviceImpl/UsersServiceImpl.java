package com.nitr.hellonitr.serviceImpl;

import com.nitr.hellonitr.dtos.UsersDTO;
import com.nitr.hellonitr.entity.Users;
import com.nitr.hellonitr.exception.ResourceNotFoundException;
import com.nitr.hellonitr.repository.UsersRepository;
import com.nitr.hellonitr.service.UsersService;
import com.nitr.hellonitr.utility.EntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    UsersRepository usersRepository;

    @Autowired
    EntityMapper entityMapper;

    @Override
    public UsersDTO getUserById(String id) {
        Users user = usersRepository.findById(UUID.fromString(id)).orElse(null);
        return entityMapper.toDto(user, UsersDTO.class);
    }

    @Override
    public Page<UsersDTO> getAllUsers(int page, int size) {
        // Create a PageRequest with sorting by 'name' in ascending order
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("name").ascending());

        // Fetch the page from the repository and convert entities to DTOs
        return usersRepository.findAll(pageRequest).map(user -> entityMapper.toDto(user, UsersDTO.class));
    }

    @Override
    public UsersDTO createUser(UsersDTO userDto) {
        Users user = entityMapper.toEntity(userDto, Users.class);
        user = usersRepository.save(user);
        return entityMapper.toDto(user, UsersDTO.class);
    }

    @Override
    public UsersDTO updateUser(String id, UsersDTO userDto) {
        Users user = usersRepository.findById(UUID.fromString(id)).orElse(null);
        if (user != null && user.getId().equals(UUID.fromString(id))) {
            user = entityMapper.toEntity(userDto, Users.class);
            user.setId(UUID.fromString(id));
            user = usersRepository.save(user);
            return entityMapper.toDto(user, UsersDTO.class);
        } else {
            throw new ResourceNotFoundException("User not found with id " + id);
        }

    }

    @Override
    public void deleteUser(String id) {
        Users user = usersRepository.findById(UUID.fromString(id)).orElse(null);
        if (user == null) {
            throw new ResourceNotFoundException("User not found with id " + id);
        }
        usersRepository.deleteById(UUID.fromString(id));
    }

    @Override
    public Page<UsersDTO> searchByName(String query, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("name").ascending());
        return usersRepository.findByName(query, pageRequest).map(user -> entityMapper.toDto(user, UsersDTO.class));
    }

    @Override
    public String storeProfilePicture(String id, MultipartFile file) {
        if (file.isEmpty()) {
            throw new ResourceNotFoundException("File is empty");
        }
        Users user = usersRepository.findById(UUID.fromString(id)).orElseThrow(() ->
                new ResourceNotFoundException("User not found with id " + id));

        try {
            // Convert the MultipartFile to a byte array
            byte[] bytes = file.getBytes();
            user.setProfilePicture(bytes);  // Set the byte array to the profilePicture field
            usersRepository.save(user);     // Save the user entity with the updated profile picture
        } catch (IOException e) {
            throw new IllegalStateException("Failed to convert file to byte array", e);
        }

        return "Profile picture uploaded successfully.";
    }

    @Override
    public byte[] getProfilePicture(String id) {
        Users user = usersRepository.findById(UUID.fromString(id)).orElseThrow(() ->
                new ResourceNotFoundException("User not found with id " + id));

        return user.getProfilePicture();
    }


}
