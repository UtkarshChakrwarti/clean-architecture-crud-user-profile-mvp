package com.nitr.hellonitr.service;

import com.nitr.hellonitr.dtos.UsersDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface UsersService {

    UsersDTO getUserById(String id);

    Page<UsersDTO> getAllUsers(int page, int size);

    UsersDTO createUser(UsersDTO userDto);

    UsersDTO updateUser(String id, UsersDTO userDto);

    void deleteUser(String id);

    Page<UsersDTO> searchByName(String query, int page, int size);

    String storeProfilePicture(String id, MultipartFile file);

    byte[] getProfilePicture(String id);

    void deleteProfilePicture(String id);
}
